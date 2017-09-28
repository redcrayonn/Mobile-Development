package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.presentation.model.ClientView;

public class ClientPresenter extends BasePresenter {
    private CaregiverPresenter caregiverPresenter;
    private FamilyPresenter familyPresenter;
    private MessagePresenter messagePresenter;

    @Inject
    public ClientPresenter(CaregiverPresenter caregiverPresenter, FamilyPresenter familyPresenter, MessagePresenter messagePresenter) {
        this.caregiverPresenter = caregiverPresenter;
        this.familyPresenter = familyPresenter;
        this.messagePresenter = messagePresenter;
    }
    
    public List<ClientView> present(List<Client> clients) {
        List<ClientView> views = new ArrayList<>();
        
        for(Client client : clients) {
            ClientView view = new ClientView();
        
            view.id = client.getId().toHexString();
            view.name = client.getName();
            view.points = client.getPoints();
            view.messages = messagePresenter.present(client.getMessages());
            view.family = familyPresenter.present(client.getFamily());
            view.appointments = client.getAppointments();
            view.caregivers = caregiverPresenter.present(client.getCaregivers());
            view.blocks = client.getBuildingBlocks();
            views.add(view);
        }    
        return views;
    }
    
    public ClientView present(Client client) {
        ClientView view = new ClientView();

        view.id = client.getId().toHexString();
        view.name = client.getName();
        view.points = client.getPoints();
        view.messages = messagePresenter.present(client.getMessages());
        view.family = familyPresenter.present(client.getFamily());
        view.appointments = client.getAppointments();
        view.caregivers = caregiverPresenter.present(client.getCaregivers());
        view.blocks = client.getBuildingBlocks();
      
        return view;
    }
}
