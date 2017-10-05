package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.APIKey;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.Role;

public class ClientView extends BaseView {

    public String id;
    public String name;
    public String password;
    public Role role;
    public APIKey apiKey;
    public List<Message> messages;
    public int points;
    public List<Family> family;
    public List<Appointment> appointments;
    public List<Caregiver> caregivers;
    public List<BuildingBlock> blocks;
}
