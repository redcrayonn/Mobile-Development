package nl.inholland.projectapi.service;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.UserDAO;

public class UserService extends BaseService{
    
    private final UserDAO dao;

    @Inject
    public UserService(UserDAO dao) {
        this.dao = dao;
    }
    public User getByCredentials(Credentials credentials) {
        return dao.getByCredentials(credentials);
    }
    
    public User assignKey(User user)
    {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        user.setApiKey(token);
        dao.update(user);
        return user;
    }
    
}
