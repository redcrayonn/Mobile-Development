package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.persistence.CaregiverDAO;
import org.bson.types.ObjectId;

public class CaregiverAppointmentService extends BaseService {

    private final CaregiverDAO dao;

    @Inject
    public CaregiverAppointmentService(CaregiverDAO dao) {
        this.dao = dao;
    }

    public List<Appointment> getAll(Caregiver caregiver, int count) {
        return reduceList(caregiver.getAppointments(), count);
    }

    public Appointment getById(Caregiver caregiver, String appointmentId) {
        for (Appointment a : caregiver.getAppointments()) {
            if (a.getId().equals(appointmentId)) {
                return a;
            }
        }
        throw new NotFoundException("Appointment not found");
    }

    public URI create(Appointment appointment, Caregiver caregiver, UriInfo uriInfo) {
        try {
            appointment.setId(new ObjectId());
            caregiver.getAppointments().add(appointment);
            dao.update(caregiver);
            return buildUri(uriInfo, appointment.getId());
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    public void update(String appointmentId, Appointment updatedAppointment, Caregiver caregiver) {
        try {
            for (Appointment a : caregiver.getAppointments()) {
                if (a.getId().equals(appointmentId)) {
                    a.setDatetime(updatedAppointment.getDatetime());
                    a.setDescription(updatedAppointment.getDescription());
                    dao.update(caregiver);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    public void delete(String appointmentId, Caregiver caregiver) {
        caregiver.getAppointments().removeIf(a -> a.getId().equals(appointmentId));
        dao.update(caregiver);
    }
}
