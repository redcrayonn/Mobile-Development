package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;

public class ClientMessageService extends BaseService {

    private final ClientDAO clientDAO;
    private final UserDAO userDAO;

    @Inject
    public ClientMessageService(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
    }

    public List<Message> getAll(Client client, int count) {
        return reduceList(client.getMessages(), count);
    }

    public Message getById(Client client, String messageId) {
        for (Message m : client.getMessages()) {
            if (m.getId().equals(messageId)) {
                return m;
            }
        }
        throw new NotFoundException();
    }

    public URI create(Message message, Client sender, UriInfo uriInfo) {
        User receiver = userDAO.get(message.getReceiverId());
        requireResult(receiver, "ReceivedId not found");

        return sendMessage(message, sender, receiver, uriInfo, userDAO);
    }

    public void delete(Client client, String messageId) {
        client.getMessages().removeIf(m -> m.getId().equals(messageId));
        clientDAO.update(client);
    }

}
