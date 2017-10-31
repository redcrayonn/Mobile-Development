package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.Date;
import java.util.List;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;

@Singleton
public abstract class BaseService {

    protected URI buildUri(UriInfo uriInfo, String id) {
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        return builder.path(id).build();
    }

    public void requireResult(Object o, String message) throws NotFoundException {
        if (o == null) {
            throw new NotFoundException(message);
        }
    }

    protected void requiredValue(Object o) throws BadRequestException {
        if (o == null) {
            throw new BadRequestException();
        }
    }

    public <T> List<T> reduceList(List<T> list, int count) {
        if (list.size() < count || count <= 0) {
            return list;
        }
        return list.subList(0, count);
    }

    protected void checkPermissions(User user, User accesingUser) throws ForbiddenException {
        if (user instanceof Client) {
            checkClientPermissions((Client) user, accesingUser);
        } else if (accesingUser.getRole().equals(Role.admin)) {
        } else if (!user.getId().equals(accesingUser.getId())) {
            throw new ForbiddenException("User privileges not sufficient");
        }
    }

    private void checkClientPermissions(Client client, User accesingUser) throws ForbiddenException {
        switch (accesingUser.getRole()) {
            case admin:
                return;
            case client:
                if (client.getId().equals(accesingUser.getId())) {
                    return;
                } else {
                    break;
                }
            case caregiver:
                for (Caregiver c : client.getCaregivers()) {
                    if (c.getId().equals(accesingUser.getId())) {
                        return;
                    }
                }
            case family:
                for (Family f : client.getFamily()) {
                    if (f.getId().equals(accesingUser.getId())) {
                        return;
                    }
                }
                break;
            default:
                break;
        }
        throw new ForbiddenException("User privileges not sufficient");
    }

    protected URI sendMessage(Message message, User sender, User receiver, UriInfo uriInfo, UserDAO userDAO) {
        try {
            message.setDateTime(new Date());
            message.setSenderId(new ObjectId(sender.getId()));
            message.setId(new ObjectId());
            message.setRead(false);

            receiver.getMessages().add(message);
            userDAO.update(receiver);

            message.setId(new ObjectId());
            message.setRead(true);

            sender.getMessages().add(message);
            userDAO.update(sender);

            return buildUri(uriInfo, message.getId());
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
}
