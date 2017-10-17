package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.Iterator;
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

    private ClientDAO DAO;

    @Inject
    public ClientAppointmentService(ClientDAO DAO) {
        this.DAO = DAO;
    }

    /**
     * Search and return appointment
     *
     * @param client
     * @param appointmentId
     * @return
     * @see Appointment
     */
    public Appointment getById(Client client, String appointmentId) {
        List<Appointment> appointmentList = client.getAppointments();

        if (!appointmentList.isEmpty()) {
            for (Iterator<Appointment> iterator = appointmentList.iterator(); iterator.hasNext();) {
                Appointment nextAppointment = iterator.next();
                if (nextAppointment.getId().equals(appointmentId)) {
                    return nextAppointment;
                }
            }
        }
        throw new NotFoundException();
    }

    /**
     * Create an appointment and add it to the clients appointment list
     *
     * @param appointment
     * @param client
     * @param uriInfo
     * @return UriBuilder
     */
    public URI create(Appointment appointment, Client client, UriInfo uriInfo) {

        try {
            List<Appointment> appointmentList = client.getAppointments();
            appointment.setId(new ObjectId());
            appointmentList.add(appointment);
            DAO.update(client);
            return buildUri(uriInfo, appointment.getId());
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * Update clients appointment
     *
     * @param appointmentFound
     * @param updatedAppointment
     * @param client
     * @param uriInfo
     * @return
     */
    public void update(Appointment appointmentFound, Appointment updatedAppointment, Client client) {
        try {
            List<Appointment> appointmentList = client.getAppointments();
            for (Iterator<Appointment> iterator = appointmentList.iterator(); iterator.hasNext();) {
                Appointment nextAppointment = iterator.next();
                if (nextAppointment.getId().equals(appointmentFound.getId())) {
                    nextAppointment.setDatetime(updatedAppointment.getDatetime());
                    nextAppointment.setDescription(updatedAppointment.getDescription());
                    DAO.update(client);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * Delete clients appointment
     *
     * @param appointment
     * @param client
     * @param uriInfo
     * @return UriBuilder
     */
    public void delete(Appointment appointment, Client client) {
        try {
            List<Appointment> appointmentList = client.getAppointments();

            if (!appointmentList.isEmpty()) {
                for (int i = 0; i < appointmentList.size(); i++) {
                    if (appointmentList.get(i).getId().equals(appointment.getId())) {
                        appointmentList.remove(appointmentList.get(i));
                        DAO.update(client);
                    }
                }
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

}
