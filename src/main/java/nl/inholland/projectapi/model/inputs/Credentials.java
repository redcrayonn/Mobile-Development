package nl.inholland.projectapi.model.inputs;

public class Credentials {

    private String username;

    public Credentials() {

    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
