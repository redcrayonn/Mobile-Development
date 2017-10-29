package nl.inholland.projectapi.resource;

import java.net.URI;
import javax.inject.Singleton;

@Singleton
public class BaseResource {

    protected String getId(URI uri) {
        return uri.getPath().replaceFirst(".*/([^/?]+).*", "$1");
    }
}
