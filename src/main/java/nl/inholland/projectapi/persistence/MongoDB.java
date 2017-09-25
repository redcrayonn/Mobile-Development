package nl.inholland.projectapi.persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import javax.inject.Singleton;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Family;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

@Singleton
public class MongoDB
{
    private final MongoCollection<BuildingBlock> buildingBlocks;
    private final MongoCollection<Caregiver> caregivers;
    private final MongoCollection<Client> clients;
    private final MongoCollection<Family> family;
    private InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
    
    private static MongoDB instance = null;
    private MongoDB()
    {
        String user = null;
        String db = null;
        char[] password = null;
        
        Properties prop = new Properties();
        try
        {
            if(input != null)
            {
                prop.load(input);
            } 
            user = prop.getProperty("USER");
            db = prop.getProperty("DB");
            password = prop.getProperty("PASSWORD").toCharArray();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }        
        
        CodecRegistry codecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
      
        MongoCredential credential = MongoCredential.createScramSha1Credential(user, db, password);
        MongoClientOptions options = MongoClientOptions.builder().codecRegistry(codecRegistry).build();
        
        MongoClient mongoClient = new MongoClient(new ServerAddress("imready.ml", 27017), Arrays.asList(credential), options);
        MongoDatabase database = mongoClient.getDatabase("imready");     
        
        buildingBlocks = database.getCollection("buildingBlocks", BuildingBlock.class);
        caregivers = database.getCollection("caregivers", Caregiver.class);
        clients = database.getCollection("clients", Client.class);
        family = database.getCollection("family", Family.class);
    }
    public static MongoDB getInstance()
    {
        if(instance == null)
        {
            instance = new MongoDB();
        }
        return instance;
    }
    public MongoCollection<BuildingBlock> getBuildingBlocks()
    {
        return buildingBlocks;
    }
    public MongoCollection<Caregiver> getCaregivers() {
        return caregivers;
    }

    public MongoCollection<Client> getClients() {
        return clients;
    }

    public MongoCollection<Family> getFamily() {
        return family;
    }
}
