package nl.inholland.projectapi.service;

import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Social;
import nl.inholland.projectapi.model.User;

public class ClientBlockActivitySocialService extends BaseService {
    
    protected boolean checkRoles(User user, Social social) {
        if(social.getSenderId().equals(user.getId()) || user.getRole().equals(Role.admin)) {
            return true;
        }
        return false;
    }
}
