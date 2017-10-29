package nl.inholland.projectapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ProjectAPIConfiguration extends Configuration {

    @JsonProperty
    public SwaggerBundleConfiguration swagger;
}
