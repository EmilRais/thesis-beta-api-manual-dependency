package dk.developer.database;

import com.fasterxml.jackson.core.type.TypeReference;
import dk.developer.clause.From;
import dk.developer.clause.In;
import dk.developer.clause.Matching;
import dk.developer.clause.With;

import java.util.List;
import java.util.Map;

import static dk.developer.utility.Converter.converter;

public interface DatabaseLayer {
    In.Void<String> save(DatabaseObject databaseObject) throws RuntimeException;
    With<Object, From<String, Map<String, Object>>> load(String key);
    List<Map<String,Object>> loadAll(String collectionName);
    Matching<Object, From.Bool<String>> delete(String key);
    In.Bool<String> update(DatabaseObject databaseObject);

    default Map<String, Object> asMap(DatabaseObject databaseObject) {
        return converter().convert(databaseObject, new TypeReference<Map<String, Object>>() {
        });
    }
}
