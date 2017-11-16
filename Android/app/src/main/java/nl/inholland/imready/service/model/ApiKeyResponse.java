package nl.inholland.imready.service.model;

import java.util.Date;

public class ApiKeyResponse {
    private String authtoken;
    private Date datetime;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
