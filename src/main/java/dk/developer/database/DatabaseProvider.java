package dk.developer.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DatabaseProvider {
    private static DatabaseLayer database;
    private MongoClient client;

    public static DatabaseFront databaseLayer() {
        return DatabaseFront.create(database);
    }

    public static void useProductionDatabase() {
        MongoClient client = new MongoClient("127.0.0.1");
        MongoDatabase mongoDatabase = client.getDatabase("database");
        database = new PersistedDatabase(mongoDatabase);
    }

    private DatabaseProvider() {
    }

    public static void setDatabase(DatabaseLayer database) {
        DatabaseProvider.database = database;
    }
}
