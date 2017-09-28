package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.BuildingBlock;

public class ClientView
{
    public String id;
    public String name;
    public int points;
    public List<MessageView> messages;
    public List<FamilyView> family;
    public List<Appointment> appointments;
    public List<CaregiverView> caregivers;
    public List<BuildingBlock> blocks;
}