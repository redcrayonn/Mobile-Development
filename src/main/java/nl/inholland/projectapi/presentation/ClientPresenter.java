package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.presentation.model.ClientView;

public class ClientPresenter extends BasePresenter {

    private final MessagePresenter messagePresenter;
    private final EntityPresenter presenter;
    private final AppointmentPresenter appointmentPresenter;
    private final PersonalBlockPresenter blockPresenter;

    @Inject
    public ClientPresenter(
            MessagePresenter messagePresenter,
            EntityPresenter presenter,
            AppointmentPresenter appointmentPresenter,
            PersonalBlockPresenter blockPresenter) {
        this.messagePresenter = messagePresenter;
        this.presenter = presenter;
        this.appointmentPresenter = appointmentPresenter;
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
        view.family = presenter.present(client.getFamily());
        view.appointments = appointmentPresenter.present(client.getAppointments());
        view.caregivers = presenter.present(client.getCaregivers());
        view.blocks = blockPresenter.present(client.getBuildingBlocks());

        return view;
    }
}
