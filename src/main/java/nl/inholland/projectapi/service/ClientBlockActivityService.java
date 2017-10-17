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

public class ClientBlockActivityService extends BaseService {
    
    private final ClientDAO clientDAO;
    private final UserDAO userDAO;

    @Inject
    public ClientBlockActivityService(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
    }    
    
    public List<Activity> getActivities(Client client, String blockId) {
        for (BuildingBlock b : client.getBuildingBlocks()) {
            if (b.getId().equals(blockId)) {
                return b.getActivities();
            }
        }
        throw new NotFoundException("Not found");        
    }
    
    public Activity getActivity(Client client, String blockId, String activityId) {
        for(Activity a : getActivities(client, blockId)) {
            if(a.getId().equals(activityId)) {
                return a;
            }
        }
        throw new NotFoundException("Not found");
    }
}
