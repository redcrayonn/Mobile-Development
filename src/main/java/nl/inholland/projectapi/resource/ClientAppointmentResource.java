package nl.inholland.projectapi.resource;

import io.swagger.annotations.Api;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.AppointmentPresenter;
import nl.inholland.projectapi.presentation.model.AppointmentView;
import nl.inholland.projectapi.service.ClientAppointmentService;
import nl.inholland.projectapi.service.ClientService;

@Api("Client appointments")
@Path("/api/v1/clients/{clientId}/appointments")
@Secured({Role.admin, Role.client, Role.caregiver})
public class ClientAppointmentResource extends BaseResource {

    private final ClientService clientService;
    private final ClientAppointmentService appointmentService;
    private final AppointmentPresenter appointmentPresenter;

    @Inject
    public ClientAppointmentResource(
            ClientService clientService,
            ClientAppointmentService appointmentService,
            AppointmentPresenter appointmentPresenter) {
        this.clientService = clientService;
        this.appointmentService = appointmentService;
        this.appointmentPresenter = appointmentPresenter;
    }

    @GET
    @Produces("application/json")
    public List<AppointmentView> getAll(
            @PathParam("clientId") String clientId,
            @QueryParam("count") int count,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        return appointmentPresenter.present(appointmentService.getAll(client, count));
    }

    @POST
    @Consumes("application/json")
    public Response createAppointment(
            @PathParam("clientId") String clientId,
            Appointment appointment,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        clientService.requireResult(appointment, "Json object in body required");
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        URI uri = appointmentService.create(appointment, client, uriInfo);
        return Response.created(uri).header("Id", getId(uri)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{appointmentId}")
    public AppointmentView getById(
            @PathParam("clientId") String clientId,
            @PathParam("appointmentId") String appointmentId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        Appointment appointment = appointmentService.getById(client, appointmentId);
        return appointmentPresenter.present(appointment);
    }

    @PUT
    @Consumes("application/json")
    @Path("/{appointmentId}")
    public Response updateAppointment(
            @PathParam("clientId") String clientId,
            @PathParam("appointmentId") String appointmentId,
            Appointment updatedAppointment,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        clientService.requireResult(updatedAppointment, "Json object in body required");
        Client clientFound = clientService.getById(clientId, context.getUserPrincipal());
        appointmentService.update(appointmentId, updatedAppointment, clientFound);
        return Response.ok().build();
    }

    @DELETE
    @Consumes("application/json")
    @Path("/{appointmentId}")
    public void deleteAppointment(
            @PathParam("clientId") String clientId,
            @PathParam("appointmentId") String appointmentId,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        Client clientFound = clientService.getById(clientId, context.getUserPrincipal());
        appointmentService.delete(appointmentId, clientFound);
    }
}
