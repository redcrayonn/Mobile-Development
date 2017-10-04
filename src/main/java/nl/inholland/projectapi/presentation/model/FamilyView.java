package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.Role;

public class FamilyView extends BaseView {

    public String id;
    public String name;
    public String password;
    public Role role;
    public String apiKey;
    public List<Message> messages;
}
