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
public class Client extends User{
    private int point;
    private List<Family> family;
    private List<Appointment> appointment;
    private List<Caregiver> caregivers;
    private List<BuildingBlock> buildingBlocks; 

    public Client() {
    }  
    
    public Client(int point, List<Family> family, List<Appointment> appointment, List<Caregiver> caregivers, List<BuildingBlock> buildingBlocks, ObjectId id, String username, List<Message> messages) {
        super(id, username, messages);
        this.point = point;
        this.family = family;
        this.appointment = appointment;
        this.caregivers = caregivers;
        this.buildingBlocks = buildingBlocks;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<Family> getFamily() {
        return family;
    }

    public void setFamily(List<Family> family) {
        this.family = family;
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(List<Appointment> appointment) {
        this.appointment = appointment;
    }

    public List<Caregiver> getCaregivers() {
        return caregivers;
    }

    public void setCaregivers(List<Caregiver> caregivers) {
        this.caregivers = caregivers;
    }

    public List<BuildingBlock> getBuildingBlocks() {
        return buildingBlocks;
    }

    public void setBuildingBlocks(List<BuildingBlock> buildingBlocks) {
        this.buildingBlocks = buildingBlocks;
    }
    
}
