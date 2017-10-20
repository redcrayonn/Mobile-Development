package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Like;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;

public class ClientBlockActivityLikeService extends BaseService {

    private final ClientDAO clientDAO;
    private final UserDAO userDAO;

    @Inject
    public ClientBlockActivityLikeService(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
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

    public URI create(Client client, String blockId, String activityId, Principal principal, UriInfo uriInfo) {
        User sender = userDAO.getByUsername(principal.getName());
        Like like = new Like();
        like.setId(new ObjectId());
        like.setSenderId(new ObjectId(sender.getId()));
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
