package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Role;

public class ClientView extends BaseView {

    public String name;
    public int points;
    public Role role;
    public List<MessageView> messages;
    public List<FamilyView> family;
    public List<AppointmentView> appointments;
    public List<CaregiverView> caregivers;
    public List<PersonalBlockView> blocks;
}
