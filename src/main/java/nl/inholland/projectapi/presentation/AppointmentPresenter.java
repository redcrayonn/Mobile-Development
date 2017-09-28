package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.presentation.model.AppointmentView;

public class AppointmentPresenter extends BasePresenter
{
    public AppointmentView present(Appointment appointment) {
        AppointmentView view = new AppointmentView();
        view.id = appointment.getId().toHexString();
        System.out.println(appointment.getId().toHexString());
        view.description = appointment.getDescription();
        view.datetime = appointment.getDatetime();
        return view;
    }
    
    public List<AppointmentView> present(List<Appointment> appointments) {
        List<AppointmentView> views = new ArrayList<>();
        
        for(Appointment appointment : appointments) {
            AppointmentView view = new AppointmentView();
            System.out.println(appointment.getId().toHexString());
            view.id = appointment.getId().toHexString();
            view.description = appointment.getDescription();
            view.datetime = appointment.getDatetime();
            views.add(view);            
        }      
        return views;
    }   
}
