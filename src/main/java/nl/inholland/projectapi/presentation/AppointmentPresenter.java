package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.presentation.model.AppointmentView;

public class AppointmentPresenter extends BasePresenter {

    public AppointmentPresenter() {
    }

    public String present(Appointment appointment) {
        AppointmentView view = new AppointmentView();
        view.dateTime = appointment.getDateTime();
        view.description = appointment.getDescription();
        return super.toJson(view);
    }

    public List<AppointmentView> present(List<Appointment> appointments) {
        List<AppointmentView> views = new ArrayList<>();
        AppointmentView view;
        
        for (Appointment appointment : appointments) {
            view = new AppointmentView();
            view.dateTime = appointment.getDateTime();
            view.description = appointment.getDescription();
            views.add(view);
        }
        return views;
    }
}
