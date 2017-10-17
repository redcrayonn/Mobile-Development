package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Like;
import nl.inholland.projectapi.persistence.ClientDAO;
import org.bson.types.ObjectId;

public class ClientBlockActivityLikeService extends BaseService {

    private final ClientDAO clientDAO;

    @Inject
    public ClientBlockActivityLikeService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public List<Like> getAll(Client client, String blockId, String activityId) {
        for (BuildingBlock b : client.getBuildingBlocks()) {
            if (b.getId().equals(blockId)) {
                for (Activity a : b.getActivities()) {
                    if (a.getId().equals(activityId)) {
                        return a.getLikes();
                    }
                }
            }
        }
        throw new NotFoundException("Likes not found");
    }

    public URI create(Client client, String blockId, String activityId, Like like, UriInfo uriInfo) {
        like.setId(new ObjectId());
        like.setDateTime(new Date());
        try {
            like.getSenderId();
        } catch (Exception e) {
            throw new BadRequestException("SenderId is required");
        }

        getAll(client, blockId, activityId).add(like);
        clientDAO.update(client);
        return buildUri(uriInfo, like.getId());
    }

    public Like get(Client client, String blockId, String activityId, String likeId) {
        for (Like l : getAll(client, blockId, activityId)) {
            if (l.getId().equals(likeId)) {
                return l;
            }
        }
        throw new NotFoundException("Like not found");
    }

    public void delete(Client client, String blockId, String activityId, String likeId) {
        getAll(client, blockId, activityId).removeIf(i -> i.getId().equals(likeId));
        clientDAO.update(client);
    }
}
