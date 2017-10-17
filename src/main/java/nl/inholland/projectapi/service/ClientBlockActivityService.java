package nl.inholland.projectapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.persistence.ClientDAO;

public class ClientBlockActivityService extends BaseService {
    
    private final ClientDAO clientDAO;

    @Inject
    public ClientBlockActivityService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
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
    
    public void patch(Client client, String blockId, Activity activity, JsonNode patchRequest) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonActivity = mapper.valueToTree(activity);
            JsonPatch patch = JsonPatch.fromJson(patchRequest);
            JsonNode patched = patch.apply(jsonActivity);
            Activity updatedActivity = mapper.treeToValue(patched, Activity.class);
            for(BuildingBlock b : client.getBuildingBlocks()) {
                if(b.getId().equals(blockId)) {
                    b.getActivities().remove(activity);
                    b.getActivities().add(updatedActivity);
                }
            }
            clientDAO.update(client);

        } catch (JsonPatchException | IOException | IllegalArgumentException | NullPointerException ex) {
            throw new BadRequestException("Bad patch request");
        }
    }
}
