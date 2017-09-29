package nl.inholland.projectapi.presentation.model;

import java.util.List;

public class ClientView extends BaseView {

    public String id;
    public String name;
    public int points;
    public List<MessageView> messages;
    public List<FamilyView> family;
    public List<AppointmentView> appointments;
    public List<CaregiverView> caregivers;
    public List<BlockView> blocks;
}
