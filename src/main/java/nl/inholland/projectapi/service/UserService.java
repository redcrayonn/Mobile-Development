package nl.inholland.projectapi.service;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import nl.inholland.projectapi.model.APIKeyResponse;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

public class UserService extends BaseService{
    
    private final UserDAO dao;

    @Inject
    public UserService(UserDAO dao) {
        this.dao = dao;
    }
    public APIKeyResponse login(Credentials credentials) {
        User user = getByCredentials(credentials);       
        if(user == null) {
            throw new NotAuthorizedException("Wrong username or password");
        }

        //
        
//        String hashed = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());
//        System.out.println(hashed);
//        
//        if(BCrypt.checkpw("client1", hashed))
//            System.out.println("gj");
//        
        //
        
        User newUser = assignKey(user);
        APIKeyResponse response = new APIKeyResponse();
        response.setAuthtoken(newUser.getApiKey());   
        return response;
    }
    
    private User getByCredentials(Credentials credentials) {
        return dao.getByCredentials(credentials);
    }
    
    private User assignKey(User user) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        user.setApiKey(token);
        dao.update(user);
        return user;
    }
    
}
