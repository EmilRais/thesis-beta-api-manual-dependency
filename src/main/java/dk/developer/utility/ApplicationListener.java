package dk.developer.utility;

import dk.developer.database.DatabaseProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initialising application");

        System.out.println("Connecting to production database");
        DatabaseProvider.useProductionDatabase();
        System.out.println("Now using production database");

        System.out.println("Done initialising application");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO: Maybe we should also close the database
        DatabaseProvider.setDatabase(null);
    }
}
