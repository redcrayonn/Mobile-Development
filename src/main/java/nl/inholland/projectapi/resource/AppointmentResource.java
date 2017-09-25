package nl.inholland.projectapi.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.ErrorResponse;
import nl.inholland.projectapi.presentation.AppointmentPresenter;
import nl.inholland.projectapi.presentation.ErrorPresenter;
import nl.inholland.projectapi.service.AppointmentService;

@Path("/appointments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppointmentResource extends BaseResource {

    private final AppointmentService appointmentService;
    private final AppointmentPresenter appointmentPresenter;
    private final ErrorPresenter errorPresenter;

    @Inject
    public AppointmentResource(AppointmentService appointmentService, AppointmentPresenter appointmentPresenter, ErrorPresenter errorPresenter) {

        this.appointmentService = appointmentService;
        this.appointmentPresenter = appointmentPresenter;
        this.errorPresenter = errorPresenter;
    }

    @GET
    @Produces("application/json")
    public Response getAll() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return (appointments.isEmpty()) ? Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(errorPresenter.present(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase(), "Sorry team"))).build() : Response.ok(appointmentPresenter.present(appointments), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{appointmentId}")
    @Produces("application/json")
    public Response getById(@PathParam("appointmentId") String appointmentId) {

        if (!appointmentId.matches("\\d+")) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorPresenter.present(new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase(), "Id moet int zijn"))).build();
        }

        int appointmentIdInt = Integer.parseInt(appointmentId);
        Appointment appointment = appointmentService.getAppointmentById(appointmentIdInt);

        if (appointment == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(errorPresenter.present(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase(), "Sorry team"))).build();
        }
        String appointmentView = appointmentPresenter.present(appointment);
        return Response.ok(appointmentView, MediaType.APPLICATION_JSON).build();
    }
}