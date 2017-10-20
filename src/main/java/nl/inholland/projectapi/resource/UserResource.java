package nl.inholland.projectapi.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.service.UserService;
import nl.inholland.projectapi.model.APIKey;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;

@Path("/api/v1/")
public class UserResource extends BaseResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public APIKey login(Credentials credentials) {
        return userService.login(credentials);
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.admin, Role.family, Role.client, Role.caregiver})
    public void logout(@Context SecurityContext context) {
        userService.logout(context.getUserPrincipal().getName());
    }
}
