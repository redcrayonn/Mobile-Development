package nl.inholland.projectapi.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.CommandResult;
import com.mongodb.MongoException;
import javax.inject.Inject;
import org.mongodb.morphia.Datastore;

public class DatabaseHealthCheck extends HealthCheck {

    private final Datastore datastore;

    @Inject
    public DatabaseHealthCheck(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    protected Result check() throws Exception {
        try {
            CommandResult result = datastore.getDB().getStats();
            if (result.ok()) {
                return Result.healthy();
            } else {
                return Result.unhealthy(result.getErrorMessage());
            }
        } catch (MongoException e) {
        }
        return Result.unhealthy("Can't get MongoDB status");
    }

}
