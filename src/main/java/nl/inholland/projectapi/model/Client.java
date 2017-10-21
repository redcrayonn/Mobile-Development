package nl.inholland.projectapi.model;

import nl.inholland.projectapi.model.inputs.Credentials;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "users", noClassnameStored = false)
public class Client extends User {

    @NotEmpty
    private int points;
    @Reference(idOnly = true)
    private List<Family> family;
    @Reference(idOnly = true)
    private List<Caregiver> caregivers;
    @Embedded
    private List<Appointment> appointments;
    @Embedded
    private List<BuildingBlock> blocks;

    public Client(Credentials credentials) {
        super(credentials, Role.client);
        this.points = 0;
        this.family = new ArrayList<Family>();
        this.caregivers = new ArrayList<Caregiver>();
        this.appointments = new ArrayList<Appointment>();
        this.blocks = new ArrayList<BuildingBlock>();
    }

    public Client() {

    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Family> getFamily() {
        return family;
    }

    public void setFamily(List<Family> family) {
        this.family = family;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Caregiver> getCaregivers() {
        return caregivers;
    }

    public void setCaregivers(List<Caregiver> caregivers) {
        this.caregivers = caregivers;
    }

    public List<BuildingBlock> getBuildingBlocks() {
        return blocks;
    }

    public void setBuildingBlocks(List<BuildingBlock> buildingBlocks) {
        this.blocks = buildingBlocks;
    }
}
