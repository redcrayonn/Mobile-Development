package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Message;

public class ClientView extends BaseView {

    public String id;
    public String name;
    public int points;
    public List<Message> messages;
    public List<Family> family;
    public List<Appointment> appointments;
    public List<Caregiver> caregivers;
    public List<BuildingBlock> blocks;
}
