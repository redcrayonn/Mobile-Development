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
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.AppointmentPresenter;
import nl.inholland.projectapi.presentation.model.AppointmentView;
import nl.inholland.projectapi.service.CaregiverAppointmentService;
import nl.inholland.projectapi.service.CaregiverService;

@Path("/api/v1/caregivers/{caregiverId}/appointments")
public class CaregiverAppointmentResource extends BaseResource {
    
    private final CaregiverService caregiverService;
    private final CaregiverAppointmentService caregiverAppointmentService;
    private final AppointmentPresenter appointmentPresenter;

    @Inject
    public CaregiverAppointmentResource(CaregiverService caregiverService, CaregiverAppointmentService caregiverAppointmentService, AppointmentPresenter appointmentPresenter) {
        this.caregiverService = caregiverService;
        this.caregiverAppointmentService = caregiverAppointmentService;
        this.appointmentPresenter = appointmentPresenter;
    }
    
    @GET
    @Produces("application/json")
    @Secured({Role.admin, Role.caregiver})
    public List<AppointmentView> getAll(@PathParam("caregiverId") String caregiverId, @Context SecurityContext context) {
        return appointmentPresenter.present(caregiverService.getById(caregiverId, context.getUserPrincipal()).getAppointments());
    }
    
    @POST
    @Consumes("application/json")
    @Secured({Role.admin, Role.caregiver})
    public Response createAppointment(@PathParam("caregiverId") String caregiverId, Appointment appointment, @Context UriInfo uriInfo, @Context SecurityContext context) {
        URI uri = caregiverAppointmentService.create(appointment, caregiverService.getById(caregiverId, context.getUserPrincipal()), uriInfo);
        return Response.created(uri).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("/{appointmentId}")
    @Secured({Role.admin, Role.caregiver})
    public AppointmentView getById(@PathParam("caregiverId") String caregiverId, @PathParam("appointmentId") String appointmentId, @Context SecurityContext context) {
        return appointmentPresenter.present(caregiverAppointmentService.getById(caregiverService.getById(caregiverId, context.getUserPrincipal()), appointmentId));
    }

    @PUT
    @Consumes("application/json")
    @Path("/{appointmentId}")
    @Secured({Role.admin, Role.caregiver})
    public Response updateAppointment(@PathParam("caregiverId") String caregiverId, @PathParam("appointmentId") String appointmentId, Appointment updatedAppointment, @Context UriInfo uriInfo, @Context SecurityContext context) {
        Caregiver caregiverFound = caregiverService.getById(caregiverId, context.getUserPrincipal());
        caregiverAppointmentService.update(caregiverAppointmentService.getById(caregiverFound, appointmentId), updatedAppointment, caregiverFound);
        return Response.ok().build();
    }

    @DELETE
    @Consumes("application/json")
    @Path("/{appointmentId}")
    @Secured({Role.admin, Role.caregiver})
    public void deleteAppointment(@PathParam("caregiverId") String caregiverId, @PathParam("appointmentId") String appointmentId, @Context UriInfo uriInfo, @Context SecurityContext context) {
        Caregiver caregiverFound = caregiverService.getById(caregiverId, context.getUserPrincipal());
        caregiverAppointmentService.delete(caregiverAppointmentService.getById(caregiverFound, appointmentId), caregiverFound);
    }   
}
