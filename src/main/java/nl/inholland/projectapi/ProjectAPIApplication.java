package nl.inholland.projectapi;

import com.hubspot.dropwizard.guice.GuiceBundle;
import health.DatabaseHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ProjectAPIApplication extends Application<ProjectAPIConfiguration> {

    private GuiceBundle<ProjectAPIConfiguration> guiceBundle;
    private SwaggerBundle<ProjectAPIConfiguration> swaggerBundle;

    public static void main(String[] args) throws Exception {
        new ProjectAPIApplication().run(new String[]{"server", "configuration.yml"});
    }

    @Override
    public void initialize(final Bootstrap<ProjectAPIConfiguration> bootstrap) {
        configureGuice();
        bootstrap.addBundle(guiceBundle);
        configureSwagger();
        bootstrap.addBundle(swaggerBundle);
    }

    private void configureGuice() {
        guiceBundle = GuiceBundle.<ProjectAPIConfiguration>newBuilder()
                .addModule(new ProjectAPIModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(ProjectAPIConfiguration.class)
                .build();
    }
    
    private void configureSwagger() {
        swaggerBundle = new SwaggerBundle<ProjectAPIConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ProjectAPIConfiguration configuration) {
                return configuration.swagger;
            }      
        };
    }
    
    @Override
    public void run(final ProjectAPIConfiguration configuration,
            final Environment environment) throws Exception {
        environment.healthChecks().register("database", guiceBundle.getInjector().getInstance(DatabaseHealthCheck.class));

    }
}
