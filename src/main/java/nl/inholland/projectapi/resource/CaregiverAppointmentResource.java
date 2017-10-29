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
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.AppointmentPresenter;
import nl.inholland.projectapi.presentation.model.AppointmentView;
import nl.inholland.projectapi.service.CaregiverAppointmentService;
import nl.inholland.projectapi.service.CaregiverService;

@Api("Caregiver appointments")
@Path("/api/v1/caregivers/{caregiverId}/appointments")
@Secured({Role.admin, Role.caregiver})
public class CaregiverAppointmentResource extends BaseResource {

    private final CaregiverService caregiverService;
    private final CaregiverAppointmentService caregiverAppointmentService;
    private final AppointmentPresenter appointmentPresenter;

    @Inject
    public CaregiverAppointmentResource(
            CaregiverService caregiverService,
            CaregiverAppointmentService caregiverAppointmentService,
            AppointmentPresenter appointmentPresenter) {
        this.caregiverService = caregiverService;
        this.caregiverAppointmentService = caregiverAppointmentService;
        this.appointmentPresenter = appointmentPresenter;
    }

    @GET
    @Produces("application/json")
    public List<AppointmentView> getAll(
            @PathParam("caregiverId") String caregiverId,
            @QueryParam("count") int count,
            @Context SecurityContext context) {
        Caregiver caregiver = caregiverService.getById(caregiverId, context.getUserPrincipal());
        List<Appointment> appointments = caregiverAppointmentService.getAll(caregiver, count);
        return appointmentPresenter.present(appointments);
    }

    @POST
    @Consumes("application/json")
    public Response createAppointment(
            @PathParam("caregiverId") String caregiverId,
            Appointment appointment,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        caregiverService.requireResult(appointment, "Json object in body required");
        Caregiver caregiver = caregiverService.getById(caregiverId, context.getUserPrincipal());
        URI uri = caregiverAppointmentService.create(appointment, caregiver, uriInfo);
        return Response.created(uri).header("Id", getId(uri)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{appointmentId}")
    public AppointmentView getById(
            @PathParam("caregiverId") String caregiverId,
            @PathParam("appointmentId") String appointmentId,
            @Context SecurityContext context) {
        Caregiver caregiver = caregiverService.getById(caregiverId, context.getUserPrincipal());
        Appointment appointment = caregiverAppointmentService.getById(caregiver, appointmentId);
        return appointmentPresenter.present(appointment);
    }

    @PUT
    @Consumes("application/json")
    @Path("/{appointmentId}")
    public Response updateAppointment(
            @PathParam("caregiverId") String caregiverId,
            @PathParam("appointmentId") String appointmentId,
            Appointment updatedAppointment,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        caregiverService.requireResult(updatedAppointment, "Json object in body required");
        Caregiver caregiverFound = caregiverService.getById(caregiverId, context.getUserPrincipal());
        caregiverAppointmentService.update(appointmentId, updatedAppointment, caregiverFound);
        return Response.ok().build();
    }

    @DELETE
    @Consumes("application/json")
    @Path("/{appointmentId}")
    public void deleteAppointment(
            @PathParam("caregiverId") String caregiverId,
            @PathParam("appointmentId") String appointmentId,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        Caregiver caregiverFound = caregiverService.getById(caregiverId, context.getUserPrincipal());
        caregiverAppointmentService.delete(appointmentId, caregiverFound);
    }
}
