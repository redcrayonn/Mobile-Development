package nl.inholland.projectapi.service;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.UserDAO;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private final UserDAO userDAO;

    @Inject
    public AuthenticationFilter(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        User user = userDAO.getByAPIKey(token);
        if (user == null || user.getApiKey().getDatetime().before(new Date())) {
            throw new NotAuthorizedException("API key invalid");
        }

        final SecurityContext currentSecurityContext = requestContext.getSecurityContext();

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return user.getUserName();
                    }
                };
            }

            @Override
            public boolean isUserInRole(String role) {
                return Role.valueOf(role).equals(user.getRole());
            }

            @Override
            public boolean isSecure() {
                return currentSecurityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "";
            }
        });
    }
}
