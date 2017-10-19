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

    /**
     * Search and return Activity from BuildingBlock
     *
     * @param blockFound
     * @param activityId
     * @return Activity
     * @see Activity
     */
    public Activity getById(BuildingBlock blockFound, String activityId) {

        List<Activity> activityList = blockFound.getActivities();
        requireResult(activityList, "No Activities found");

        if (!activityList.isEmpty()) {
            for (Iterator<Activity> iterator = activityList.iterator(); iterator.hasNext();) {
                Activity nextActivity = iterator.next();
                if (nextActivity.getId().equals(activityId)) {
                    return nextActivity;
                }
            }
        }
        throw new NotFoundException();
    }

    /**
     * Create an Activity for a BuildingBlock
     *
     * @param block
     * @param activity
     * @param uriInfo
     * @return URI
     */
    public URI create(BuildingBlock block, Activity activity, UriInfo uriInfo) {
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

    /**
     * Update an Activity for a BuildingBlock
     *
     * @param activityFound
     * @param updatedActivity
     * @param blockFound
     */
    public void update(Activity activityFound, Activity updatedActivity, BuildingBlock blockFound) {
        List<Activity> activitiesList = blockFound.getActivities();
        try {

            requiredValue(updatedActivity.getLikes());
            requiredValue(updatedActivity.getName());
            requiredValue(updatedActivity.getDescription());

            for (int i = 0; i < activitiesList.size(); i++) {
                Activity nextActivity = activitiesList.get(i);
                if (nextActivity.getId().equals(activityFound.getId())) {
                    updatedActivity.setId(new ObjectId(nextActivity.getId()));
                    activitiesList.set(i, updatedActivity);
                    blockDAO.update(blockFound);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    /**
     * Delete an Activity for a BuildingBlock
     *
     * @param activity
     * @param blockFound
     */
    public void delete(Activity activity, BuildingBlock blockFound) {

        List<Activity> activitiesList = blockFound.getActivities();
        try {
            for (int i = 0; i < activitiesList.size(); i++) {
                Activity nextActivity = activitiesList.get(i);
                if (nextActivity.getId().equals(activity.getId())) {
                    activitiesList.remove(nextActivity);
                    blockDAO.update(blockFound);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }

    }
}
