package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.presentation.model.CaregiverView;

public class CaregiverPresenter extends BasePresenter
{
    private MessagePresenter messagePresenter;

    @Inject
    public CaregiverPresenter(MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
    }
    
    public CaregiverView present(Caregiver caregiver) {
        CaregiverView view = new CaregiverView();
        view.id = caregiver.getId().toHexString();
        view.name = caregiver.getName();
        view.messages = messagePresenter.present(caregiver.getMessages());
        view.appointments = caregiver.getAppointments();
        return view;
    }
    
    public List<CaregiverView> present(List<Caregiver> caregivers) {
        List<CaregiverView> views = new ArrayList<>();
        
        for(Caregiver caregiver : caregivers) {
            CaregiverView view = new CaregiverView();
            view.id = caregiver.getId().toHexString();
            view.name = caregiver.getName();
            view.messages = messagePresenter.present(caregiver.getMessages());
            view.appointments = caregiver.getAppointments(); 
            views.add(view);            
        }      
        return views;
    }    
}
