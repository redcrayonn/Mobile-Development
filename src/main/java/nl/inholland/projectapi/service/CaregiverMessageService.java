package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.CaregiverDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;

public class CaregiverMessageService extends BaseService {

    private final CaregiverDAO dao;
    private final UserDAO userDAO;

    @Inject
    public CaregiverMessageService(CaregiverDAO dao, UserDAO userDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
    }
    
    public List<Message> getAll(String caregiverId, int count) {
        Caregiver caregiver = dao.getById(caregiverId);
        List<Message> messages = caregiver.getMessages();
        
        return reduceList(messages, count);
    }
    
    public Message getById(String caregiverId, String messageId) {
        for(Message m : getAll(caregiverId, -1)) {
            if(m.getId().equals(messageId)) {
                return m;
            }
        }
        
        throw new NotFoundException();
    }
    
    public URI create(Message message, String receiverId, Principal principal, UriInfo uriInfo) {
        Caregiver receiver = dao.get(receiverId);
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
    
    public void delete(Caregiver caregiver, String messageId) {
        caregiver.getMessages().removeIf(m -> m.getId().equals(messageId));
        dao.update(caregiver);
    }
}

