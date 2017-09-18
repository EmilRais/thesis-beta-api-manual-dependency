package dk.developer.database;

import dk.developer.clause.Matching;
import dk.developer.clause.With;
import dk.developer.utility.Converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dk.developer.utility.Converter.converter;

public class DatabaseFront {
    private final DatabaseLayer database;
    private final Converter converter;

    public static DatabaseFront create(DatabaseLayer database) {
        return new DatabaseFront(database, converter());
    }

    private DatabaseFront(DatabaseLayer database, Converter converter) {
        this.database = database;
        this.converter = converter;
    }

    public <Type extends DatabaseObject> Matching<String, With<Object, Type>> load(Class<Type> type) {
        return key -> value -> {
            String collectionName = collection(type);

            Map<String, Object> objectMap = database.load(key).with(value).from(collectionName);
            return converter.convert(objectMap, type);
        };
    }

    private String collection(Class<? extends DatabaseObject> type) {
        Collection annotation = type.getAnnotation(Collection.class);
        return annotation.value();
    }

    public <Type extends DatabaseObject> List<Type> loadAll(Class<Type> type) {
        String collectionName = collection(type);

        List<Map<String, Object>> listOfObjectMap = database.loadAll(collectionName);
        return listOfObjectMap.stream()
                .map(map -> converter.convert(map, type))
                .collect(Collectors.toList());
    }

    public void save(DatabaseObject databaseObject) {
        String collectionName = collection(databaseObject.getClass());
        database.save(databaseObject).in(collectionName);
    }

    public boolean update(DatabaseObject databaseObject) {
        String collectionName = collection(databaseObject.getClass());
        return database.update(databaseObject).in(collectionName);
    }

    public <Type extends DatabaseObject> Matching<String, With.Bool<Object>> delete(Class<Type> type) {
        String collectionName = collection(type);
        return key -> value -> database.delete(key).matching(value).from(collectionName);
    }

    public DatabaseLayer unwrap() {
        return database;
    }
}
