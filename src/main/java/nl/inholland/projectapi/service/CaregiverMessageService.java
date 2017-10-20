package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.CaregiverDAO;
import nl.inholland.projectapi.persistence.UserDAO;

public class CaregiverMessageService extends BaseService {

    private final CaregiverDAO dao;
    private final UserDAO userDAO;

    @Inject
    public CaregiverMessageService(CaregiverDAO dao, UserDAO userDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
    }

    public List<Message> getAll(Caregiver caregiver, int count) {
        List<Message> messages = caregiver.getMessages();
        return reduceList(messages, count);
    }

    public Message getById(Caregiver caregiver, String messageId) {
        for (Message m : caregiver.getMessages()) {
            if (m.getId().equals(messageId)) {
                return m;
            }
        }
        throw new NotFoundException();
    }

    public URI create(Message message, Caregiver sender, UriInfo uriInfo) {
        User receiver = userDAO.get(message.getReceiverId());
        requireResult(receiver, "ReceivedId not found");

        return sendMessage(message, sender, receiver, uriInfo, userDAO);
    }

    public void delete(Caregiver caregiver, String messageId) {
        caregiver.getMessages().removeIf(m -> m.getId().equals(messageId));
        dao.update(caregiver);
    }
}
