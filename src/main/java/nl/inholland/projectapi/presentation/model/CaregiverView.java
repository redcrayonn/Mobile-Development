package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.Message;

public class CaregiverView extends BaseView {

    public String id;
    public String name;
    public List<Message> messages;
    public List<Appointment> appointments;
}
