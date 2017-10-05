package nl.inholland.projectapi.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.inholland.projectapi.model.APIKeyResponse;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.service.UserService;

@Path("/api/v1/")
public class UserResource extends BaseResource{
    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public APIKeyResponse login(Credentials credentials) {    
        return userService.login(credentials);
    }    
}
