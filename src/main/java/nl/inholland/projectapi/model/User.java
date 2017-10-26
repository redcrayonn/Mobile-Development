package nl.inholland.projectapi.model;

import nl.inholland.projectapi.model.inputs.Credentials;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "users", noClassnameStored = false)
public abstract class User extends EntityModel implements Principal {

    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private APIKey apiKey;
    @NotEmpty
    private Role role;
    @Embedded
    private List<Message> messages;
    
    private final int SALT_ROUNDS = 12;
    
    public User(Credentials credentials, Role role) {
        this.name = credentials.getUsername();
        this.password = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt(SALT_ROUNDS));
        this.apiKey = new APIKey();
        this.messages = new ArrayList<Message>();
        this.role = role;
    }

    public User() {

    }

    public String getUserName() {
        return name;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(SALT_ROUNDS));
    }

    public APIKey getApiKey() {
        return apiKey;
    }

    public void setApiKey(APIKey apiKey) {
        this.apiKey = apiKey;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
