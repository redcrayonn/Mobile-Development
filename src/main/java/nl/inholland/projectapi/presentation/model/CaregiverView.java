package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Appointment;


public class CaregiverView 
{
    public String id;
    public String name;
    public List<MessageView> messages;
    public List<Appointment> appointments;
}
