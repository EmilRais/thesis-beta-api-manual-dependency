package dk.developer.database;

import dk.developer.testing.DatabaseInitialiser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static dk.developer.database.DatabaseTestHelper.*;

public class PersistedDatabaseTest extends DatabaseInitialiser {
    private DatabaseLayer databaseLayer;

    @BeforeMethod
    @Override
    public void before() throws Exception {
        super.before();
        databaseLayer = database.unwrap();
    }

    @Test
    public void saveNewObject() throws Exception {
        saveAndLoadObject(databaseLayer);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void saveExistingObject() throws Exception {
        saveSameObjectTwice(databaseLayer);
    }

    @Test
    public void loadNonExistingObject() throws Exception {
        loadNoObject(databaseLayer);
    }

    @Test
    public void loadExistingObject() throws Exception {
        saveAndLoadObject(databaseLayer);
    }

    @Test
    public void loadManyObjects() throws Exception {
        loadSeveralObjects(databaseLayer);
    }

    @Test
    public void deleteNonExistingObject() throws Exception {
        deleteNoObject(databaseLayer);
    }

    @Test
    public void deleteExistingObject() throws Exception {
        saveAndDeleteObject(databaseLayer);
    }

    @Test
    public void updateNonExistingObject() throws Exception {
        updateNoObject(databaseLayer);
    }

    @Test
    public void updateExistingObject() throws Exception {
        saveUpdateAndLoadObject(databaseLayer);
    }
}