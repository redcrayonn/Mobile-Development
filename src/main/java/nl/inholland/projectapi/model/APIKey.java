package nl.inholland.projectapi.model;

import java.util.Date;

public class APIKey {
    private String authtoken;
    private Date datetime;
    
    public APIKey() {
        
    }

    public APIKey(String authtoken, Date datetime) {
        this.authtoken = authtoken;
        this.datetime = datetime;
    }
    
    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    
    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }    
}
