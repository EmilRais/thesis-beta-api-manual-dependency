package dk.developer.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import dk.developer.clause.From;
import dk.developer.clause.In;
import dk.developer.clause.Matching;
import dk.developer.clause.With;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersistedDatabase implements DatabaseLayer {
    private final MongoDatabase database;

    public PersistedDatabase(MongoDatabase database) {
        this.database = database;
    }

    @Override
    public In.Void<String> save(DatabaseObject databaseObject) {
        return collectionName -> {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Map<String, Object> data = asMap(databaseObject);
            collection.insertOne(new Document(data));
        };
    }

    @Override
    public With<Object, From<String, Map<String, Object>>> load(String key) {
        return value -> collectionName -> {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            BasicDBObject findByIdQuery = new BasicDBObject(key, value);

            MongoCursor<Document> iterator = collection.find(findByIdQuery).iterator();
            if ( !iterator.hasNext() )
                return null;

            return iterator.next();
        };
    }

    @Override
    public List<Map<String, Object>> loadAll(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);

        List<Map<String, Object>> documents = new ArrayList<>();
        for (Document document : collection.find())
            documents.add(document);

        return documents;
    }

    @Override
    public Matching<Object, From.Bool<String>> delete(String key) {
        return value -> collectionName -> {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            DeleteResult result = collection.deleteOne(new BasicDBObject(key, value));
            return result.getDeletedCount() != 0;
        };
    }

    @Override
    public In.Bool<String> update(DatabaseObject databaseObject) {
        return collectionName -> {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            BasicDBObject filter = new BasicDBObject("_id", databaseObject.getId());

            Map<String, Object> data = asMap(databaseObject);
            Document updatedDocument = new Document("$set", new BasicDBObject(data));
            UpdateResult result = collection.updateOne(filter, updatedDocument);
            return result.getModifiedCount() != 0;
        };
    }
}
