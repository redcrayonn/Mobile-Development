package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Status;

public class PersonalComponentView extends BaseView {
    
    public String name;
    public Status status;
    public List<PersonalActivityView> activities;
    
}
