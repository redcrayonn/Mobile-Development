package nl.inholland.projectapi.service;

import javax.inject.Singleton;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Singleton
public abstract class BaseService {
    protected UriBuilder buildUri(UriInfo uriInfo, String id) {
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();//Get base path (/api/1.0/blocks/)
        return builder.path(id);//Add new block ID to base path (/api/1.0/blocks/{id}) 
    }
}
