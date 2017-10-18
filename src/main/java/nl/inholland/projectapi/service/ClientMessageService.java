/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;

/**
 *
 * @author kelvi
 */
public class ClientMessageService extends BaseService {
    
    private final ClientDAO clientDAO;
    private final UserDAO userDAO;
    
    @Inject
    public ClientMessageService(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
    }
    
    public List<Message> getAll(String clientId, int count) {
        Client client = clientDAO.getById(clientId);
        List<Message> messages = client.getMessages();
        
        return reduceList(messages, count);
    }
    
    public Message getById(String clientId, String messageId) {
        for(Message m : getAll(clientId, -1)) {
            if(m.getId().equals(messageId)) {
                return m;
            }
        }
        
        throw new NotFoundException();
    }
    
    public URI create(Message message, String receiverId, Principal principal, UriInfo uriInfo) {
        Client receiver = clientDAO.get(receiverId);
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
            message.setRead(true);
            
            senderMessageList.add(message);
            userDAO.update(sender);
           
            message.setId(new ObjectId());
            message.setRead(false);
            
            receiverMessageList.add(message);
            userDAO.update(receiver);
            
            return buildUri(uriInfo, message.getId());
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
    
    public void delete(Client client, String messageId) {
        client.getMessages().removeIf(m -> m.getId().equals(messageId));
        clientDAO.update(client);
    }
    
    
}
