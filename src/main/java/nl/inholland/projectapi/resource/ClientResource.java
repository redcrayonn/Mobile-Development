package nl.inholland.projectapi.resource;

import com.fasterxml.jackson.databind.JsonNode;
import io.dropwizard.jersey.PATCH;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.AppointmentPresenter;
import nl.inholland.projectapi.presentation.ClientPresenter;
import nl.inholland.projectapi.presentation.model.AppointmentView;
import nl.inholland.projectapi.presentation.model.ClientView;
import nl.inholland.projectapi.service.AppointmentService;
import nl.inholland.projectapi.service.ClientService;
import org.bson.types.ObjectId;

@Path("/api/v1/clients")
public class ClientResource extends BaseResource {

    private final ClientService clientService;
    private final ClientPresenter clientPresenter;
    private final AppointmentService appointmentService;
    private final AppointmentPresenter appointmentPresenter;

    @Inject
    public ClientResource(ClientService clientService, ClientPresenter clientPresenter, AppointmentService appointmentService, AppointmentPresenter appointmentPresenter) {
        this.clientService = clientService;
        this.clientPresenter = clientPresenter;
        this.appointmentService = appointmentService;
        this.appointmentPresenter = appointmentPresenter;
    }

    @GET
    @Produces("application/json")
    @Secured({Role.admin})
    public List<ClientView> getAll(@QueryParam("count") int count) {
        List<Client> clients = clientService.getAll();
        if (count != 0) {
            List<Client> reducedList = clientService.reduceList(clients, count);
            return clientPresenter.present(reducedList);
        }
        return clientPresenter.present(clients);
    }

    @POST
    @Consumes("application/json")
    @Secured({Role.admin})
    public Response create(Credentials credentials, @Context UriInfo uriInfo) {
        UriBuilder builder = clientService.create(credentials, uriInfo);
        return Response.created(builder.build()).build();
    }

    @GET
    @Path("/{userId}")
    @Produces("application/json")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public ClientView getById(@PathParam("userId") String userId, @Context SecurityContext context) {
        Client client = clientService.getById(userId, context.getUserPrincipal());
        return clientPresenter.present(client);
    }

    @PUT
    @Path("/{userId}")
    @Consumes("application/json")
    @Secured({Role.admin, Role.client})
    public Response update(@PathParam("userId") String userId, Credentials credentials, @Context SecurityContext context) {
        clientService.update(userId, credentials, context.getUserPrincipal());
        return Response.ok().build();
    }

    @PATCH
    @Path("/{userId}")
    @Consumes("application/json")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public Response patch(@PathParam("userId") String userId, JsonNode patchRequest, @Context SecurityContext context) {
        clientService.patch(userId, patchRequest, context.getUserPrincipal());
        return Response.ok().build();//Return 200
    }

    @DELETE
    @Path("/{userId}")
    @Secured({Role.admin})
    public void delete(@PathParam("userId") ObjectId id) {
        clientService.deleteById(id);
    }

// _____Client Appointment Section_______
    @GET
    @Produces("application/json")
    @Path("/{clientId}/appointments/{appointmentId}")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public AppointmentView getById(@PathParam("clientId") String clientId, @PathParam("appointmentId") String appointmentId, @Context SecurityContext context) {
        return appointmentPresenter.present(appointmentService.getById(clientService.getById(clientId, context.getUserPrincipal()), appointmentId));
    }

    @GET
    @Produces("application/json")
    @Path("/{clientId}/appointments/")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public List<AppointmentView> getAll(@PathParam("clientId") String clientId, @Context SecurityContext context) {
        return appointmentPresenter.present(clientService.getById(clientId, context.getUserPrincipal()).getAppointments());
    }

    @POST
    @Consumes("application/json")
    @Path("/{clientId}/appointments/")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public Response createAppointment(@PathParam("clientId") String clientId, Appointment appointment, @Context UriInfo uriInfo, @Context SecurityContext context) {
        UriBuilder uriBuilder = appointmentService.create(appointment, clientService.getById(clientId, context.getUserPrincipal()), uriInfo);
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Consumes("application/json")
    @Path("/{clientId}/appointments/{appointmentId}")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public Response updateAppointment(@PathParam("clientId") String clientId, @PathParam("appointmentId") String appointmentId, Appointment updatedAppointment, @Context UriInfo uriInfo, @Context SecurityContext context) {
        Client clientFound = clientService.getById(clientId, context.getUserPrincipal());
        appointmentService.update(appointmentService.getById(clientFound, appointmentId), updatedAppointment, clientFound);
        return Response.ok().build();//Return 200

    }

    @DELETE
    @Consumes("application/json")
    @Path("/{clientId}/appointments/{appointmentId}")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public void deleteAppointment(@PathParam("clientId") String clientId, @PathParam("appointmentId") String appointmentId, @Context UriInfo uriInfo, @Context SecurityContext context) {
        Client clientFound = clientService.getById(clientId, context.getUserPrincipal());
        appointmentService.delete(appointmentService.getById(clientFound, appointmentId), clientFound);
    }
}