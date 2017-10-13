package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.presentation.model.CaregiverView;

public class CaregiverPresenter extends BasePresenter {

    private final MessagePresenter messagePresenter;
    private final AppointmentPresenter appointmentPresenter;
    
    @Inject
    public CaregiverPresenter(MessagePresenter messagePresenter, AppointmentPresenter appointmentPresenter) {
        this.messagePresenter = messagePresenter;
        this.appointmentPresenter = appointmentPresenter;
    }

    public CaregiverView present(Caregiver caregiver) {
        CaregiverView view = new CaregiverView();
        view.id = caregiver.getId();
        view.name = caregiver.getName();
        view.role = caregiver.getRole();
        view.messages = messagePresenter.present(caregiver.getMessages());
        view.appointments = appointmentPresenter.present(caregiver.getAppointments());
        return view;
    }

    public List<CaregiverView> present(List<Caregiver> caregivers) {
        List<CaregiverView> views = new ArrayList<>();

        for (Caregiver caregiver : caregivers) {
            views.add(present(caregiver));
        }
        return views;
    }
}
