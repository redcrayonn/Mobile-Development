/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.model;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Stefan
 */
public class Caregiver extends User{
    private List<Appointment> appointments;

    public Caregiver() {
    }

    public Caregiver(ObjectId id, String username, List<Message> messages, List<Appointment> appointments) {
        super(id, username, messages);
        this.appointments = appointments;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    
}
