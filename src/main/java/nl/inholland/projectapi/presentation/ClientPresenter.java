package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.presentation.model.ClientView;

public class ClientPresenter extends BasePresenter {

    @Inject
    public ClientPresenter() {

    }

    public List<ClientView> present(List<Client> clients) {
        List<ClientView> views = new ArrayList<>();

        for (Client client : clients) {
            ClientView view = new ClientView();

            view.id = client.getId();
            view.name = client.getName();
            view.points = client.getPoints();
            view.messages = client.getMessages();
            view.family = client.getFamily();
            view.appointments = client.getAppointments();
            view.caregivers = client.getCaregivers();
            view.blocks = client.getBuildingBlocks();
            views.add(view);
        }
        return views;
    }

    public ClientView present(Client client) {
        ClientView view = new ClientView();

        view.id = client.getId();
        view.name = client.getName();
        view.points = client.getPoints();
        view.messages = client.getMessages();
        view.family = client.getFamily();
        view.appointments = client.getAppointments();
        view.caregivers = client.getCaregivers();
        view.blocks = client.getBuildingBlocks();

        return view;
    }
}
