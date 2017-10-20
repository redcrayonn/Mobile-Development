package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.persistence.BlockDAO;
import org.bson.types.ObjectId;

public class BlockActivityService extends BaseService {

    private final BlockDAO blockDAO;

    @Inject
    public BlockActivityService(BlockDAO blockDAO) {
        this.blockDAO = blockDAO;
    }

    public List<Activity> getAll(String blockId, int count) {
        return reduceList(blockDAO.get(blockId).getActivities(), count);
    }

    public Activity getById(String blockId, String activityId) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");
        for (Activity a : block.getActivities()) {
            if (a.getId().equals(activityId)) {
                return a;
            }
        }
        throw new NotFoundException("Activity not found");
    }

    public URI create(String blockId, Activity activity, UriInfo uriInfo) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");
        try {
            List<Activity> activitiesList = block.getActivities();
            activity.setId(new ObjectId());
            activitiesList.add(activity);
            blockDAO.update(block);
            return buildUri(uriInfo, activity.getId());
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    public void update(String blockId, String activityId, Activity updatedActivity) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");

        requiredValue(updatedActivity.getLikes());
        requiredValue(updatedActivity.getName());
        requiredValue(updatedActivity.getDescription());

        for (Activity a : block.getActivities()) {
            if (a.getId().equals(activityId)) {
                a = updatedActivity;
                blockDAO.update(block);
            }
        }
    }

    public void delete(String blockId, String activityId) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");
        block.getActivities().removeIf(a -> a.getId().equals(activityId));
    }
}
