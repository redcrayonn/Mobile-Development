package nl.inholland.projectapi.model;

import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "users", noClassnameStored = false)
public class Caregiver extends User {

    public Caregiver(Credentials credentials) {
        super(credentials, Role.caregiver);
        this.appointments = new ArrayList<Appointment>();
    }
    public Caregiver() {
        
    }
    @Embedded
    private List<Appointment> appointments;

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}
