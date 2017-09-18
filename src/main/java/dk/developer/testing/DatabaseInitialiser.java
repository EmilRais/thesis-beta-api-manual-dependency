package dk.developer.testing;

import dk.developer.database.DatabaseFront;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class DatabaseInitialiser {
    protected DatabaseFront database;

    @BeforeMethod
    public void before() throws Exception {
        database = TestDatabaseProvider.persistedTestDatabase();
    }

    @AfterSuite
    public void tearDown() throws Exception {
        TestDatabaseProvider.stopPersistedTestDatabase();
    }
}
