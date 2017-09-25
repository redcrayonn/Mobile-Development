package nl.inholland.projectapi.persistence;

import java.util.List;
import nl.inholland.projectapi.model.Appointment;

public class AppointmentDAO extends BaseDAO {

    public AppointmentDAO() {
    }

    public Appointment getAppointmentById(int id) {
        return MockDB.getAppointmentById(id);
    }

    public List<Appointment> getAllAppointments() {
        return MockDB.getAllAppointments();
    }
}
