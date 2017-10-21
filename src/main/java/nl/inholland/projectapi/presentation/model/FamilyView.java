package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Role;

public class FamilyView extends BaseView {

    public String name;
    public Role role;
    public List<MessageView> messages;
}
