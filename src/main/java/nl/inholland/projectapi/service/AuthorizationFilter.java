package nl.inholland.projectapi.service;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.model.User;
import nl.inholland.projectapi.persistence.UserDAO;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    private final UserDAO userDAO;
    
    @Inject
    public AuthorizationFilter(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @Context
    private ResourceInfo resourceInfo;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {       
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);
        
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);
       
        Principal userPrincipal = requestContext.getSecurityContext().getUserPrincipal();
        User user = userDAO.getByUsername(userPrincipal.getName());
        try {
            if(methodRoles.isEmpty()) {
                if(!classRoles.contains(user.getRole())) {
                   throw new ForbiddenException();
                }
            } else {
                if(!methodRoles.contains(user.getRole())) {
                    throw new ForbiddenException();
                }
            }
        } catch(Exception e) {
            throw new ForbiddenException("User privileges not sufficient");
        }
    }
    
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if(annotatedElement == null) {
            return new ArrayList<Role>();
        }
        
        Secured secured = annotatedElement.getAnnotation(Secured.class);
        
        if(secured == null) {
            return new ArrayList<Role>();
        }
        
        Role[] allowedRoles = secured.value();
        return Arrays.asList(allowedRoles);
    }
}
