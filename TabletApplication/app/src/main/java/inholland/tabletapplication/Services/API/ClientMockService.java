package inholland.tabletapplication.Services.API;

import java.util.ArrayList;

import inholland.tabletapplication.Models.LoginUser;
import inholland.tabletapplication.Models.Users.Client;
import inholland.tabletapplication.Services.Interfaces.IUserService;

/**
 * Created by Peter on 05/12/2017.
 */

public class ClientMockService implements IUserService {
    //Database mock
    private ArrayList<Client> clients;

    public ClientMockService(){
        //Initialize list and fill with mock data
        clients = new ArrayList<>();

        //clients.add(new Client("Ben"));
        //clients.add(new Client("Dave"));
        //clients.add(new Client("Frank"));
        //clients.add(new Client("Karel"));
        //clients.add(new Client("Mark"));
    }

    @Override
    public ArrayList<Client> getClients() {
        return clients;
    }

    @Override
    public Client getClientById(String id) {
        return null;
    }

    @Override
    public Boolean createNewClient(LoginUser credentials) {
        return null;
    }

    @Override
    public Boolean updateClient(Client client) {
        return null;
    }
}
