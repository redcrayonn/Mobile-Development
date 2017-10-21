package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.presentation.model.AppointmentView;

public class AppointmentPresenter extends BasePresenter {

    public AppointmentPresenter() {
    }

    public List<AppointmentView> present(List<Appointment> appointments) {
        List<AppointmentView> views = new ArrayList<>();
        appointments.forEach((appointment) -> {
            views.add(present(appointment));
        });
        return views;
    }

    public AppointmentView present(Appointment appointment) {
        AppointmentView view = new AppointmentView();
        view.id = appointment.getId();
        view.datetime = appointment.getDatetime();
        view.description = appointment.getDescription();
        return view;
    }
}
