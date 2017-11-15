package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Comment;
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.model.inputs.InputComment;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;

public class ClientBlockComponentActivityCommentService extends ClientBlockActivitySocialService {

    private final ClientDAO clientDAO;
    private final UserDAO userDAO;

    @Inject
    public ClientBlockComponentActivityCommentService(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
    }

    public List<Comment> getAll(Client client, String blockId, String componentId, String activityId) {
        for (BuildingBlock b : client.getBuildingBlocks()) {
            if (b.getId().equals(blockId)) {
                for (Component c : b.getComponents()) {
                    if (c.getId().equals(componentId)) {
                        for (Activity a : c.getActivities()) {
                            if (a.getId().equals(activityId)) {
                                return a.getComments();
                            }
                        }
                    }
                }
            }
        }
        throw new NotFoundException("Comments not found");
    }

    public URI create(Client client, String blockId, String componentId, String activityId, InputComment input, Principal principal, UriInfo uriInfo) {
        User sender = userDAO.getByUsername(principal.getName());
        Comment comment;
        try {
            comment = new Comment(input, sender.getId());
        } catch (Exception e) {
            throw new BadRequestException("Invalid Comment object");
        }
        getAll(client, blockId, componentId, activityId).add(comment);
        clientDAO.update(client);
        return buildUri(uriInfo, comment.getId());
    }

    public Comment get(Client client, String blockId, String componentId, String activityId, String commentId) {
        for (Comment c : getAll(client, blockId, componentId, activityId)) {
            if (c.getId().equals(commentId)) {
                return c;
            }
        }
        throw new NotFoundException("Comment not found");
    }

    public void update(Client client, String blockId, String componentId, String activityId, String commentId, InputComment input, Principal principal) {
        User editor = userDAO.getByUsername(principal.getName());
        Comment comment = get(client, blockId, componentId, activityId, commentId);
        if(!checkRoles(editor, comment)) {
            throw new ForbiddenException("You can only edit your own comments");
        }
        comment.setMessage(input.getMessage());
        clientDAO.update(client);
    }

    public void delete(Client client, String blockId, String componentId, String activityId, String commentId, Principal principal) {
        User deleter = userDAO.getByUsername(principal.getName());
        Comment comment = get(client, blockId, componentId, activityId, commentId);
        if(!checkRoles(deleter, comment)) {
            throw new ForbiddenException("You can only delete your own comments");
        }
        getAll(client, blockId, componentId, activityId).removeIf(i -> i.getId().equals(commentId));
        clientDAO.update(client);        
    }
}
