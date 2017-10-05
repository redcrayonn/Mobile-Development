package nl.inholland.projectapi.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import nl.inholland.projectapi.model.APIKey;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.UserDAO;
import org.apache.commons.lang3.time.DateUtils;
import org.mindrot.jbcrypt.BCrypt;

public class UserService extends BaseService{
    
    private final UserDAO dao;

    @Inject
    public UserService(UserDAO dao) {
        this.dao = dao;
    }
    public APIKey login(Credentials credentials) {
        User user = getByUsername(credentials.getUsername());                  
        if(user == null) {
            throw new NotAuthorizedException("Wrong username or password");
        }
        if(!BCrypt.checkpw(credentials.getPassword(), user.getPassword())) {
            throw new NotAuthorizedException("Wrong username or password");
        }
        return assignKey(user);
    }
    private APIKey assignKey(User user) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        APIKey key = new APIKey(token, DateUtils.addHours(new Date(), 168));
        user.setApiKey(key);
        dao.update(user);
        return user.getApiKey();
    }
    private User getByUsername(String username) {
        return dao.getByUsername(username);
    }
}
