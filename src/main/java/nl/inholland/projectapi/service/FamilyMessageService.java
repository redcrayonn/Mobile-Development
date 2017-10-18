package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.FamilyDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;

public class FamilyMessageService extends BaseService {

    private final FamilyDAO dao;
    private final UserDAO userDAO;

    @Inject
    public FamilyMessageService(FamilyDAO dao, UserDAO userDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
    }

    public List<Message> getAll(String familyId, int count) {
        Family family = dao.getById(familyId);
        requireResult(family, "FamilyId not found");
        List<Message> messages = family.getMessages();
        return reduceList(messages, count);
    }

    public Message getById(String familyId, String messageId) {
        try {
            for (Message m : getAll(familyId, -1)) {
                if (m.getId().equals(messageId)) {
                    return m;
                }
            }
        } catch (Exception e) {
            throw new NotFoundException();
        }
        throw new NotFoundException();
    }

    public URI create(Message message, String receiverId, Principal principal, UriInfo uriInfo) {
        Family receiver = dao.get(receiverId);
        User sender = userDAO.getByUsername(principal.getName());

        requireResult(sender, "SenderId not found");
        requireResult(receiver, "ReceivedId not found");

        try {
            List<Message> senderMessageList = sender.getMessages();
            List<Message> receiverMessageList = receiver.getMessages();

            message.setDateTime(new Date());
            message.setSenderId(new ObjectId(sender.getId()));
            message.setReceiverId(new ObjectId(receiver.getId()));
            message.setId(new ObjectId());

            receiverMessageList.add(message);
            userDAO.update(receiver);

            message.setId(new ObjectId());
            message.setRead(true);
            senderMessageList.add(message);
            userDAO.update(sender);
            return buildUri(uriInfo, message.getId());
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    public void delete(String familyId, String messageId) {
        Family family = dao.getById(familyId);
        requireResult(family, "FamilyID not found");
        List<Message> mList = family.getMessages();

        if (!mList.isEmpty()) {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getId().equals(messageId)) {
                    mList.remove(mList.get(i));
                }
            }
            dao.update(family);
        } else {
            throw new NotFoundException();
        }
    }
}
