package nl.inholland.projectapi.service;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.persistence.AppointmentDAO;

public class AppointmentService extends BaseService {
    
    private final AppointmentDAO appointmentDAO;
   
    @Inject
    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }
    
    public Appointment getAppointmentById(int id) {
        return appointmentDAO.getAppointmentById(id);
    }
}
