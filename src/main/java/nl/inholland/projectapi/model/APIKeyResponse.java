package nl.inholland.projectapi.model;

/**
 *
 * @author student
 */
public class APIKeyResponse {
    private String authtoken;
    
    public APIKeyResponse() {
        
    }
    
    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }    
}
