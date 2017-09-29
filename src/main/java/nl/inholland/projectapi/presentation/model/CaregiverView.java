package nl.inholland.projectapi.presentation.model;

import java.util.List;

public class CaregiverView extends BaseView {

    public String id;
    public String name;
    public List<MessageView> messages;
    public List<AppointmentView> appointments;
}
