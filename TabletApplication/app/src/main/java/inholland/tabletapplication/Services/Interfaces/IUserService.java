package inholland.tabletapplication.Services.Interfaces;

import java.util.ArrayList;

import inholland.tabletapplication.Models.LoginUser;
import inholland.tabletapplication.Models.Users.Client;

/**
 * Created by Peter on 05/12/2017.
 */

public interface IUserService {
    public ArrayList<Client> getClients();
    public Client getClientById(String id);
    public Boolean createNewClient(LoginUser credentials);
    public Boolean updateClient(Client client);
}
