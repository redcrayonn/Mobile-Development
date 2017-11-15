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
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.model.Status;
import nl.inholland.projectapi.persistence.ClientDAO;

public class ClientBlockComponentActivityService extends BaseService {

    private final ClientDAO clientDAO;

    @Inject
    public ClientBlockComponentActivityService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public List<Activity> getActivities(Client client, String blockId, String componentId) {
        for (BuildingBlock b : client.getBuildingBlocks()) {
            if (b.getId().equals(blockId)) {
                for(Component c : b.getComponents()) {
                    if(c.getId().equals(componentId)) {
                        return c.getActivities();
                    }
                }
            }
        }
        throw new NotFoundException("Activities not found");
    }

    public Activity getActivity(Client client, String blockId, String componentId, String activityId) {
        for (Activity a : getActivities(client, blockId, componentId)) {
            if (a.getId().equals(activityId)) {
                return a;
            }
        }
        throw new NotFoundException("Activity not found");
    }

    public void patch(Client client, String blockId, String componentId, String activityId, JsonNode patchRequest) {
        ObjectMapper mapper = new ObjectMapper();
        Activity activity = getActivity(client, blockId, componentId, activityId);
        try {  
            JsonNode jsonActivity = mapper.valueToTree(activity);
            JsonPatch patch = JsonPatch.fromJson(patchRequest);
            JsonNode patched = patch.apply(jsonActivity);
            Activity updatedActivity = mapper.treeToValue(patched, Activity.class);

            List<Activity> activities = getActivities(client, blockId, componentId);
            activities.remove(activity);
            activities.add(updatedActivity);
            checkIfCompleted(client, blockId, componentId);
            clientDAO.update(client);

        } catch (JsonPatchException | IOException | IllegalArgumentException | NullPointerException ex) {
            throw new BadRequestException("Bad patch request");
        }
    }
    
    public void checkIfCompleted(Client client, String blockId, String componentId) {
        for (Activity a: getActivities(client, blockId, componentId)) {
            if(!a.getStatus().equals(Status.complete)) {
                return;
            }
        }
        for(BuildingBlock b : client.getBuildingBlocks()) {
            if(b.getId().equals(blockId)) {
                for(Component c : b.getComponents()) {
                    if(c.getId().equals(componentId)) {
                        c.setStatus(Status.complete);
                    }
                }
            }
        }
    }
}
