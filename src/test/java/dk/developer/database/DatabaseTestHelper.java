package dk.developer.database;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.common.truth.Truth.ASSERT;

class DatabaseTestHelper {
    static void saveAndLoadObject(DatabaseLayer database) {
        String name = "Peter";
        DatabaseObject databaseObject = new FakeDatabaseObject(name);
        String id = databaseObject.getId();

        String collection = "collection";
        database.save(databaseObject).in(collection);

        Map<String, Object> data = database.load("_id").with(id).from(collection);
        ASSERT.that(data.get("name")).isEqualTo(name);
    }

    static void saveSameObjectTwice(DatabaseLayer database) {
        DatabaseObject databaseObject = new FakeDatabaseObject(null);
        String collection = "collection";
        database.save(databaseObject).in(collection);
        database.save(databaseObject).in(collection);
    }

    static void loadNoObject(DatabaseLayer database) {
        Map<String, Object> data = database.load("_id").with(ObjectId.get()).from("collection");
        ASSERT.that(data).isNull();
    }

    static void loadZeroObjects(DatabaseLayer database) {
        List<Map<String, Object>> data = database.loadAll("collection");
        ASSERT.that(data).isEmpty();
    }

    static void loadSeveralObjects(DatabaseLayer database) {
        FakeDatabaseObject oneObject = new FakeDatabaseObject(null);
        database.save(oneObject).in("collection");

        FakeDatabaseObject someObject = new FakeDatabaseObject(null);
        database.save(someObject).in("collection");

        List<Map<String, Object>> data = database.loadAll("collection");
        ASSERT.that(data).hasSize(2);

        Object firstId = data.get(0).get("_id");
        ASSERT.that(firstId.equals(oneObject.getId()) || firstId.equals(someObject.getId())).isTrue();

        Object secondId = data.get(1).get("_id");
        ASSERT.that(secondId.equals(oneObject.getId()) || secondId.equals(someObject.getId())).isTrue();

        ASSERT.that(firstId).isNotEqualTo(secondId);
    }

    static void deleteNoObject(DatabaseLayer database) {
        boolean didDelete = database.delete("_id").matching(ObjectId.get()).from("collection");
        ASSERT.that(didDelete).isFalse();
    }

    static void saveAndDeleteObject(DatabaseLayer database) {
        DatabaseObject databaseObject = new FakeDatabaseObject(null);
        String id = databaseObject.getId();

        String collection = "collection";
        database.save(databaseObject).in(collection);
        boolean didDelete = database.delete("_id").matching(id).from(collection);
        ASSERT.that(didDelete).isTrue();

        Map<String, Object> map = database.load("_id").with(id).from(collection);
        ASSERT.that(map).isNull();
    }

    static void updateNoObject(DatabaseLayer database) {
        DatabaseObject databaseObject = new FakeDatabaseObject(null);
        boolean didUpdate = database.update(databaseObject).in("collection");
        ASSERT.that(didUpdate).isFalse();
    }

    static void saveUpdateAndLoadObject(DatabaseLayer database) {
        String id = ObjectId.get().toString();
        String collection = "collection";
        database.save(new FakeDatabaseObject(id, "Ole")).in(collection);

        boolean didUpdate = database.update(new FakeDatabaseObject(id, "Hans")).in(collection);
        ASSERT.that(didUpdate).isTrue();

        Map<String, Object> data = database.load("_id").with(id).from(collection);
        ASSERT.that(data.get("name")).isEqualTo("Hans");
    }

    @Collection(FakeDatabaseObject.COLLECTION)
    static class FakeDatabaseObject extends DatabaseObject {
        static final String COLLECTION = "People";

        @JsonProperty("_id")
        private final String id;

        private final String name;

        @JsonCreator
        public FakeDatabaseObject(@JsonProperty("name") String name) {
            this.name = name;
            this.id = ObjectId.get().toString();
        }

        public FakeDatabaseObject(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;
            FakeDatabaseObject that = (FakeDatabaseObject) o;
            return Objects.equals(id, that.id) &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "FakeDatabaseObject{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
