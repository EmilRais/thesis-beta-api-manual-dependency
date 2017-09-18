package dk.developer.database;

import dk.developer.clause.From;
import dk.developer.clause.In;
import dk.developer.clause.Matching;
import dk.developer.clause.With;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryDatabase implements DatabaseLayer {
    private final Map<String, Map<Object, DatabaseObject>> database;

    public MemoryDatabase(Map<String, Map<Object, DatabaseObject>> database) {
        this.database = database;
    }

    @Override
    public In.Void<String> save(DatabaseObject databaseObject) {
        return collectionName -> {
            database.putIfAbsent(collectionName, new HashMap<>());
            Map<Object, DatabaseObject> collection = database.get(collectionName);

            Object id = databaseObject.getId();
            if ( collection.containsKey(id) )
                throw new RuntimeException("Duplicate id for " + databaseObject);

            collection.put(id, databaseObject);
        };
    }

    @Override
    public With<Object, From<String, Map<String, Object>>> load(String key) {
        return value -> collectionName -> {
            Map<Object, DatabaseObject> collection = database.getOrDefault(collectionName, new HashMap<>());
            for (DatabaseObject databaseObject : collection.values()) {
                Map<String, Object> objectMap = asMap(databaseObject);
                Object object = objectMap.get(key);
                if ( value.equals(object) )
                    return objectMap;
            }

            return null;
        };
    }

    @Override
    public List<Map<String, Object>> loadAll(String collectionName) {
        Map<Object, DatabaseObject> collection = database.getOrDefault(collectionName, new HashMap<>());
        return collection.values().stream()
                .map(this::asMap)
                .collect(Collectors.toList());
    }

    @Override
    public Matching<Object, From.Bool<String>> delete(String key) {
        return value -> collectionName -> {
            Map<Object, DatabaseObject> collection = database.getOrDefault(collectionName, new HashMap<>());
            for (DatabaseObject databaseObject : collection.values()) {
                Map<String, Object> objectMap = asMap(databaseObject);
                Object object = objectMap.get(key);
                if ( value.equals(object) ) {
                    collection.remove(databaseObject.getId());
                    return true;
                }
            }

            return false;
        };
    }

    @Override
    public In.Bool<String> update(DatabaseObject databaseObject) {
        return collectionName -> {
            Map<Object, DatabaseObject> collection = database.getOrDefault(collectionName, new HashMap<>());

            Object id = databaseObject.getId();
            DatabaseObject oldValue = collection.put(id, databaseObject);
            return oldValue != null;
        };
    }
}
