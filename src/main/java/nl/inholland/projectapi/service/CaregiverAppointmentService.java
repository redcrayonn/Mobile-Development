package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.Iterator;
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

    private final CaregiverDAO DAO;

    @Inject
    public CaregiverAppointmentService(CaregiverDAO DAO) {
        this.DAO = DAO;
    }

    /**
     * Search and return appointment
     *
     * @param Caregiver
     * @param appointmentId
     * @return
     * @see Appointment
     */
    public Appointment getById(Caregiver caregiver, String appointmentId) {
        List<Appointment> appointmentList = caregiver.getAppointments();

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
     * Create an appointment and add it to the caregiver's appointment list
     *
     * @param appointment
     * @param caregiver
     * @param uriInfo
     * @return UriBuilder
     */
    public URI create(Appointment appointment, Caregiver caregiver, UriInfo uriInfo) {

        try {
            List<Appointment> appointmentList = caregiver.getAppointments();
            appointment.setId(new ObjectId());
            appointmentList.add(appointment);
            DAO.update(caregiver);
            return buildUri(uriInfo, appointment.getId());
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * Update casregiver's appointment
     *
     * @param appointmentFound
     * @param updatedAppointment
     * @param caregiver
     */
    public void update(Appointment appointmentFound, Appointment updatedAppointment, Caregiver caregiver) {
        try {
            List<Appointment> appointmentList = caregiver.getAppointments();
            for (Iterator<Appointment> iterator = appointmentList.iterator(); iterator.hasNext();) {
                Appointment nextAppointment = iterator.next();
                if (nextAppointment.getId().equals(appointmentFound.getId())) {
                    nextAppointment.setDatetime(updatedAppointment.getDatetime());
                    nextAppointment.setDescription(updatedAppointment.getDescription());
                    DAO.update(caregiver);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * Delete caregiver's appointment
     *
     * @param appointment
     * @param caregiver
     */
    public void delete(Appointment appointment, Caregiver caregiver) {
        try {
            List<Appointment> appointmentList = caregiver.getAppointments();

            if (!appointmentList.isEmpty()) {
                for (int i = 0; i < appointmentList.size(); i++) {
                    if (appointmentList.get(i).getId().equals(appointment.getId())) {
                        appointmentList.remove(appointmentList.get(i));
                        DAO.update(caregiver);
                    }
                }
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

}
