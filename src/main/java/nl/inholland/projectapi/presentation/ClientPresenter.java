package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.presentation.model.ClientView;

public class ClientPresenter extends BasePresenter {

    private final MessagePresenter messagePresenter;
    private final FamilyPresenter familyPresenter;
    private final AppointmentPresenter appointmentPresenter;
    private final CaregiverPresenter caregiverPresenter;
    private final PersonalBlockPresenter blockPresenter;

    @Inject
    public ClientPresenter(
            MessagePresenter messagePresenter,
            FamilyPresenter familyPresenter,
            AppointmentPresenter appointmentPresenter,
            CaregiverPresenter caregiverPresenter,
            PersonalBlockPresenter blockPresenter) {
        this.messagePresenter = messagePresenter;
        this.familyPresenter = familyPresenter;
        this.appointmentPresenter = appointmentPresenter;
        this.caregiverPresenter = caregiverPresenter;
        this.blockPresenter = blockPresenter;
    }

    public List<ClientView> present(List<Client> clients) {
        List<ClientView> views = new ArrayList<>();

        for (Client client : clients) {
            views.add(present(client));
        }
        return views;
    }

    public ClientView present(Client client) {
        ClientView view = new ClientView();

        view.id = client.getId();
        view.name = client.getName();
        view.points = client.getPoints();
        view.role = client.getRole();
        view.messages = messagePresenter.present(client.getMessages());
        view.family = familyPresenter.present(client.getFamily());
        view.appointments = appointmentPresenter.present(client.getAppointments());
        view.caregivers = caregiverPresenter.present(client.getCaregivers());
        view.blocks = blockPresenter.present(client.getBuildingBlocks());

        return view;
    }
}
