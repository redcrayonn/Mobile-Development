package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.presentation.model.BaseView;

public class EntityPresenter extends BasePresenter {
    
    public List<BaseView> present(List<Client> clients) {
        List<BaseView> views = new ArrayList<>();

        for (Client client : clients) {
            views.add(present(client));
        }
        return views;        
    }

    public BaseView present(Client client) {
        BaseView view = new BaseView();
        view.id = client.getId();
        return view;
    }    
    
}
