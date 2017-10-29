package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Role;

public class ClientView extends BaseView {

    public String name;
    public int points;
    public Role role;
    public List<BaseView> family;
    public List<BaseView> caregivers;
    public List<PersonalBlockView> blocks;
}
