package nl.inholland.projectapi.resource;

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

@Path("/api/v1/clients/{clientId}/appointments")
public class ClientAppointmentResource extends BaseResource {
    
    private final ClientService clientService;
    private final ClientAppointmentService appointmentService;
    private final AppointmentPresenter appointmentPresenter;

    @Inject
    public ClientAppointmentResource(ClientService clientService, ClientAppointmentService appointmentService, AppointmentPresenter appointmentPresenter) {
        this.clientService = clientService;
        this.appointmentService = appointmentService;
        this.appointmentPresenter = appointmentPresenter;
    }
    
    @GET
    @Produces("application/json")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public List<AppointmentView> getAll(@PathParam("clientId") String clientId, @Context SecurityContext context) {
        return appointmentPresenter.present(clientService.getById(clientId, context.getUserPrincipal()).getAppointments());
    }
    
    @POST
    @Consumes("application/json")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public Response createAppointment(@PathParam("clientId") String clientId, Appointment appointment, @Context UriInfo uriInfo, @Context SecurityContext context) {
        URI uri = appointmentService.create(appointment, clientService.getById(clientId, context.getUserPrincipal()), uriInfo);
        return Response.created(uri).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("/{appointmentId}")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public AppointmentView getById(@PathParam("clientId") String clientId, @PathParam("appointmentId") String appointmentId, @Context SecurityContext context) {
        return appointmentPresenter.present(appointmentService.getById(clientService.getById(clientId, context.getUserPrincipal()), appointmentId));
    }

    @PUT
    @Consumes("application/json")
    @Path("/{appointmentId}")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public Response updateAppointment(@PathParam("clientId") String clientId, @PathParam("appointmentId") String appointmentId, Appointment updatedAppointment, @Context UriInfo uriInfo, @Context SecurityContext context) {
        Client clientFound = clientService.getById(clientId, context.getUserPrincipal());
        appointmentService.update(appointmentService.getById(clientFound, appointmentId), updatedAppointment, clientFound);
        return Response.ok().build();
    }

    @DELETE
    @Consumes("application/json")
    @Path("/{appointmentId}")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public void deleteAppointment(@PathParam("clientId") String clientId, @PathParam("appointmentId") String appointmentId, @Context UriInfo uriInfo, @Context SecurityContext context) {
        Client clientFound = clientService.getById(clientId, context.getUserPrincipal());
        appointmentService.delete(appointmentService.getById(clientFound, appointmentId), clientFound);
    }   
}
