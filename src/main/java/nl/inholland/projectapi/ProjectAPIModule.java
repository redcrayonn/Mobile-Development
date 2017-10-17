package nl.inholland.projectapi;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import javax.inject.Singleton;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class ProjectAPIModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    Datastore providesDatastore() {
        MongoClient mongoClient = new MongoClient(new ServerAddress("188.226.178.52", 27017), Arrays.asList(getCredentials()));
        Morphia morphia = new Morphia();
        morphia.getMapper().getOptions().setStoreEmpties(true);
        return morphia.createDatastore(mongoClient, "imready");
    }

    private MongoCredential getCredentials() {
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        String user = null;
        String db = null;
        char[] password = null;
        Properties prop = new Properties();
        try {
            if (input != null) {
                prop.load(input);
            }
            user = prop.getProperty("USER");
            db = prop.getProperty("DB");
            password = prop.getProperty("PASSWORD").toCharArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return MongoCredential.createScramSha1Credential(user, db, password);
    }
}
