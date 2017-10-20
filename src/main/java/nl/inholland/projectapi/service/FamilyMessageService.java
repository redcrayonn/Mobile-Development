package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.FamilyDAO;
import nl.inholland.projectapi.persistence.UserDAO;

public class FamilyMessageService extends BaseService {

    private final FamilyDAO dao;
    private final UserDAO userDAO;

    @Inject
    public FamilyMessageService(FamilyDAO dao, UserDAO userDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
    }

    public List<Message> getAll(Family family, int count) {
        requireResult(family, "FamilyId not found");
        List<Message> messages = family.getMessages();
        return reduceList(messages, count);
    }

    public Message getById(Family family, String messageId) {
        for (Message m : family.getMessages()) {
            if (m.getId().equals(messageId)) {
                return m;
            }
        }
        throw new NotFoundException("Message not found");
    }

    public URI create(Message message, Family sender, UriInfo uriInfo) {
        User receiver = userDAO.get(message.getReceiverId());
        requireResult(receiver, "ReceivedId not found");

        return sendMessage(message, sender, receiver, uriInfo, userDAO);
    }

    public void delete(Family family, String messageId) {
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
