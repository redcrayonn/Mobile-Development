package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.persistence.ClientDAO;
import org.bson.types.ObjectId;

public class ClientAppointmentService extends BaseService {

    private final ClientDAO dao;

    @Inject
    public ClientAppointmentService(ClientDAO dao) {
        this.dao = dao;
    }

    public List<Appointment> getAll(Client client, int count) {
        return reduceList(client.getAppointments(), count);
    }

    public Appointment getById(Client client, String appointmentId) {
        for (Appointment a : client.getAppointments()) {
            if (a.getId().equals(appointmentId)) {
                return a;
            }
        }
        throw new NotFoundException("Appointment not found");
    }

    public URI create(Appointment appointment, Client client, UriInfo uriInfo) {
        try {
            List<Appointment> appointmentList = client.getAppointments();
            appointment.setId(new ObjectId());
            appointmentList.add(appointment);
            dao.update(client);
            return buildUri(uriInfo, appointment.getId());
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    public void update(String appointmentId, Appointment updatedAppointment, Client client) {
        try {
            for (Appointment nextAppointment : client.getAppointments()) {
                if (nextAppointment.getId().equals(appointmentId)) {
                    nextAppointment.setDatetime(updatedAppointment.getDatetime());
                    nextAppointment.setDescription(updatedAppointment.getDescription());
                    dao.update(client);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    public void delete(String appointmentId, Client client) {
        client.getAppointments().removeIf(a -> a.getId().equals(appointmentId));
        dao.update(client);
    }
}
