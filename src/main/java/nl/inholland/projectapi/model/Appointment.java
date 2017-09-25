/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.model;

import org.joda.time.DateTime;

/**
 *
 * @author Stefan
 */
public class Appointment {
    private DateTime datetime;
    private String description;

    public Appointment() {
    }
    
    public Appointment(DateTime datetime, String description) {
        this.datetime = datetime;
        this.description = description;
    }

    public DateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(DateTime datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
