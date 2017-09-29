package nl.inholland.projectapi.model;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;

public class Appointment extends EntityModel {

    @NotEmpty
    private Date datetime;
    @NotEmpty
    private String description;

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
