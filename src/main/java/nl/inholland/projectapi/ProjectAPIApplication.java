package nl.inholland.projectapi;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.inholland.projectapi.persistence.MongoDB;

public class ProjectAPIApplication extends Application<ProjectAPIConfiguration>
{
    private GuiceBundle<ProjectAPIConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception
    {
        new ProjectAPIApplication().run(new String[] { "server" } );
    }

    @Override
    public void initialize( final Bootstrap<ProjectAPIConfiguration> bootstrap)
    {
        configureGuice();
        bootstrap.addBundle(guiceBundle);
    }

    private void configureGuice()
    {
        guiceBundle = GuiceBundle.<ProjectAPIConfiguration>newBuilder()
            .addModule( new ProjectAPIModule() )
            .enableAutoConfig(getClass().getPackage().getName())
            .setConfigClass(ProjectAPIConfiguration.class)
            .build();
    }

    @Override
    public void run( final ProjectAPIConfiguration configuration, 
                     final Environment environment) throws Exception
    {
        //MockDB.init();
        
//        environment.jersey().register(new AuthDynamicFeature(
//                    new BasicCredentialAuthFilter.Builder<OUD_user>()
//                    .setAuthenticator(guiceBundle.getInjector().getInstance(
//                        AuthenticationService.class )
//                    )
//                    .setRealm("SUPER SECRET STUFF")
//                    .buildAuthFilter()
//            )
//                
//        );
        
//        environment.jersey().register(new AuthValueFactoryProvider.Binder<>( OUD_user.class )
//        );
    }
}
