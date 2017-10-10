package nl.inholland.projectapi.service;

import java.util.List;
import javax.inject.Singleton;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.User;

@Singleton
public abstract class BaseService {
    
    protected UriBuilder buildUri(UriInfo uriInfo, String id) {
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();//Get base path (/api/1.0/blocks/)
        return builder.path(id);//Add new block ID to base path (/api/1.0/blocks/{id}) 
    }
    protected <T> void requireResult(List<T> list, String message) throws NotFoundException {
        if(list.isEmpty()) {
            throw new NotFoundException(message);
        }
    }
    protected void requireResult(Object o, String message) throws NotFoundException {
        if (o == null) {
            throw new NotFoundException(message);
        }
    }

    public <T> List<T> reduceList(List<T> list, int count) {
        if (list.size() < count) {
            return list;
        }
        return list.subList(0, count);
    }
    protected void checkPermissions(Client client, User accesingUser) throws ForbiddenException{
        switch(accesingUser.getRole()) {
            case admin:
                return;
            case client:
                if(client.getId().equals(accesingUser.getId())) {
                    return;
                }else {
                    break;
                }
            case caregiver:
                for(Caregiver c : client.getCaregivers()) {
                    if(c.getId().equals(accesingUser.getId())) {
                        return;
                    }
                }
            case family:
                for(Family f : client.getFamily()) {
                    if(f.getId().equals(accesingUser.getId())) {
                        return;
                    }
                }
                break;
            default:
                break;      
        }
        throw new ForbiddenException("User privileges not sufficient");
    }
}
