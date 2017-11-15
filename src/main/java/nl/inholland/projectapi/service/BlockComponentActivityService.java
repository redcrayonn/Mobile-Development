package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.persistence.BlockDAO;

public class BlockComponentActivityService extends BaseService {

    private final BlockDAO blockDAO;

    @Inject
    public BlockComponentActivityService(BlockDAO blockDAO) {
        this.blockDAO = blockDAO;
    }

    public List<Activity> getAll(String blockId, String componentId, int count) {
        List<Component> components = blockDAO.get(blockId).getComponents();
        requireResult(components, "Components not found");
        for(Component c : components) {
            if(c.getId().equals(componentId)) {
                return reduceList(c.getActivities(), count);
            }
        }
        throw new NotFoundException("Activities not found");
    }

    public Activity getById(String blockId, String componentId, String activityId) {
        for (Activity a : getAll(blockId, componentId, -1)) {
            if (a.getId().equals(activityId)) {
                return a;
            }
        }
        throw new NotFoundException("Activity not found");
    }

    public URI create(String blockId, String componentId, Activity activity, UriInfo uriInfo) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");
        try {
            for(Component c : block.getComponents()) {
                if(c.getId().equals(componentId)) {
                    activity.createNewId();
                    c.getActivities().add(activity);
                    blockDAO.update(block);
                    return buildUri(uriInfo, activity.getId());
                }
            }  
        } catch (Exception e) {
            throw new BadRequestException();
        }
        throw new NotFoundException("Activity not found");
    }

    public void update(String blockId, String componentId, String activityId, Activity updatedActivity) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");

        requiredValue(updatedActivity.getName());
        requiredValue(updatedActivity.getDescription());

        for (Component c : block.getComponents()) {
            if(c.getId().equals(componentId)) {
                for(Activity a : c.getActivities()) {
                    if (a.getId().equals(activityId)) {
                        a.setName(updatedActivity.getName());
                        a.setDescription(updatedActivity.getDescription());
                        a.setAssignment(updatedActivity.getAssignment());
                        a.setPoints(updatedActivity.getPoints());
                        blockDAO.update(block);
                    }
                }
            }
        }
    }

    public void delete(String blockId, String componentId, String activityId) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");
        for (Component c : block.getComponents()) {
            if(c.getId().equals(componentId)) {
                c.getActivities().removeIf(a -> a.getId().equals(activityId));
                blockDAO.update(block);
            }
        }
    }
}
