package nl.inholland.projectapi.service;

import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;

public class ClientBlockService extends BaseService {

    private final ClientDAO clientDAO;
    private final UserDAO userDAO;

    @Inject
    public ClientBlockService(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
    }

    public List<BuildingBlock> getAll(String clientId, Principal principal, int count) {
        Client client = clientDAO.get(clientId);
        checkPermissions(client, userDAO.getByUsername(principal.getName()));
        List<BuildingBlock> blocks = client.getBuildingBlocks();
        requireResult(blocks, "Not Found");
        return reduceList(blocks, count);
    }

    public BuildingBlock getById(String clientId, String blockId, Principal principal) {
        Client client = clientDAO.get(clientId);
        checkPermissions(client, userDAO.getByUsername(principal.getName()));
        for (BuildingBlock b : client.getBuildingBlocks()) { //Nog niet lekker
            if (b.getId().equals(blockId)) {
                return b;
            }
        }
        throw new NotFoundException("Not found");
    }
    
    //new file later
    public List<Activity> getActivities(String clientId, String blockId, Principal principal) {
        Client client = clientDAO.get(clientId);
        checkPermissions(client, userDAO.getByUsername(principal.getName()));
        for (BuildingBlock b : client.getBuildingBlocks()) { //Nog niet lekker
            if (b.getId().equals(blockId)) {
                return b.getActivities();
            }
        }
        throw new NotFoundException("Not found");        
    }
    
}
