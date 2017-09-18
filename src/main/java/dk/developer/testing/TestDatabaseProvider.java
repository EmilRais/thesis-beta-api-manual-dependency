package dk.developer.testing;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
import dk.developer.database.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.flapdoodle.embed.mongo.distribution.Version.Main.PRODUCTION;

public class TestDatabaseProvider {
    private static MongodForTestsFactory testFactory;
    private static MongoClient client;

    public static DatabaseFront memoryTestDatabase() {
        Map<String, Map<Object, DatabaseObject>> dataMap = new HashMap<>();
        DatabaseLayer database = new MemoryDatabase(dataMap);
        DatabaseProvider.setDatabase(database);
        return DatabaseProvider.databaseLayer();
    }

    public static DatabaseFront persistedTestDatabase() {
        if ( testFactory == null && client == null )
            initialisePersistedTestDatabase();

        UUID uuid = UUID.randomUUID();
        MongoDatabase mongoDatabase = client.getDatabase(uuid.toString());
        DatabaseLayer database = new PersistedDatabase(mongoDatabase);
        DatabaseProvider.setDatabase(database);
        return DatabaseProvider.databaseLayer();
    }

    private static void initialisePersistedTestDatabase() {
        try {
            testFactory = MongodForTestsFactory.with(PRODUCTION);
            client = testFactory.newMongo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopPersistedTestDatabase() {
        System.out.println("Done with testing, shutting down");
        testFactory.shutdown();
        System.out.println("Succesfully shut down the persisted test database");
    }

    private TestDatabaseProvider() {
    }
}
