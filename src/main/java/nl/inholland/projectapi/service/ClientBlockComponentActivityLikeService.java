package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.model.Like;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;

public class ClientBlockComponentActivityLikeService extends ClientBlockActivitySocialService {

    private final ClientDAO clientDAO;
    private final UserDAO userDAO;

    @Inject
    public ClientBlockComponentActivityLikeService(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
    }

    public List<Like> getAll(Client client, String blockId, String componentId, String activityId) {
        for (BuildingBlock b : client.getBuildingBlocks()) {
            if (b.getId().equals(blockId)) {
                for (Component c: b.getComponents()) {
                    if (c.getId().equals(componentId)) {
                        for (Activity a : c.getActivities()) {
                            if (a.getId().equals(activityId)) {
                                return a.getLikes();
                            }
                        }
                    }
                }
            }
        }
        throw new NotFoundException("Likes not found");
    }

    public URI create(Client client, String blockId, String componentId, String activityId, Principal principal, UriInfo uriInfo) {
        User sender = userDAO.getByUsername(principal.getName());
        Like like = new Like(sender.getId());
        getAll(client, blockId, componentId, activityId).add(like);
        clientDAO.update(client);
        return buildUri(uriInfo, like.getId());
    }

    public Like get(Client client, String blockId, String componentId, String activityId, String likeId) {
        for (Like l : getAll(client, blockId, componentId, activityId)) {
            if (l.getId().equals(likeId)) {
                return l;
            }
        }
        throw new NotFoundException("Like not found");
    }

    public void delete(Client client, String blockId, String componentId, String activityId, String likeId, Principal principal) {
        User deleter = userDAO.getByUsername(principal.getName());
        Like like = get(client, blockId, componentId, activityId, likeId);
        if(!checkRoles(deleter, like)) {
            throw new ForbiddenException("You can only delete your own likes");
        }
        getAll(client, blockId, componentId, activityId).removeIf(i -> i.getId().equals(likeId));
        clientDAO.update(client);        
    }
}
