package nl.inholland.projectapi.model;

public class Appointment extends EntityModel {

    private String dateTime;
    private String description;

    public Appointment(int Id, String dateTime, String description) {
        super.setId(Id);
        this.dateTime = dateTime;
        this.description = description;
    }

    public Appointment() {
    }

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
