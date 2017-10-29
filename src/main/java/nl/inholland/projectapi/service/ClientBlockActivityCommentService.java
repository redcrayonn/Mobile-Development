package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.Date;
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
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.inputs.InputComment;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;

public class ClientBlockActivityCommentService extends BaseService {

    private final ClientDAO clientDAO;
    private final UserDAO userDAO;

    @Inject
    public ClientBlockActivityCommentService(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
    }

    public List<Comment> getAll(Client client, String blockId, String activityId) {
        for (BuildingBlock b : client.getBuildingBlocks()) {
            if (b.getId().equals(blockId)) {
                for (Activity a : b.getActivities()) {
                    if (a.getId().equals(activityId)) {
                        return a.getComments();
                    }
                }
            }
        }
        throw new NotFoundException("Comments not found");
    }

    public URI create(Client client, String blockId, String activityId, Comment comment, Principal principal, UriInfo uriInfo) {
        User sender = userDAO.getByUsername(principal.getName());
        try {
            comment.setId(new ObjectId());
            comment.setDateTime(new Date());
            comment.setSenderId(new ObjectId(sender.getId()));
            comment.getMessage();
        } catch (Exception e) {
            throw new BadRequestException("Invalid Comment object");
        }
        getAll(client, blockId, activityId).add(comment);
        clientDAO.update(client);
        return buildUri(uriInfo, comment.getId());
    }

    public Comment get(Client client, String blockId, String activityId, String commentId) {
        for (Comment c : getAll(client, blockId, activityId)) {
            if (c.getId().equals(commentId)) {
                return c;
            }
        }
        throw new NotFoundException("Comment not found");
    }

    public void update(Client client, String blockId, String activityId, String commentId, InputComment input, Principal principal) {
        User editor = userDAO.getByUsername(principal.getName());
        Comment comment = get(client, blockId, activityId, commentId);
        if(!checkRoles(editor, comment)) {
            throw new ForbiddenException("You can only edit your own comments");
        }
        comment.setMessage(input.getMessage());
        clientDAO.update(client);
    }

    public void delete(Client client, String blockId, String activityId, String commentId, Principal principal) {
        User deleter = userDAO.getByUsername(principal.getName());
        Comment comment = get(client, blockId, activityId, commentId);
        if(!checkRoles(deleter, comment)) {
            throw new ForbiddenException("You can only delete your own comments");
        }
        getAll(client, blockId, activityId).removeIf(i -> i.getId().equals(commentId));
        clientDAO.update(client);        
    }
    
    private boolean checkRoles(User user, Comment comment) {
        if(comment.getSenderId().equals(user.getId()) || user.getRole().equals(Role.admin)) {
            return true;
        }
        return false;
    }
}
