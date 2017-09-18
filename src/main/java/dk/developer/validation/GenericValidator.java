package dk.developer.validation;

import dk.developer.database.DatabaseFront;
import dk.developer.database.DatabaseObject;
import dk.developer.database.DatabaseProvider;

import static dk.developer.validation.GenericValidator.Status.*;

public class GenericValidator {
    private static GenericValidator INSTANCE = new GenericValidator();

    public static GenericValidator get() {
        return INSTANCE;
    }

    protected GenericValidator() {
    }

    public Status isStored(DatabaseObject object) {
        if ( object == null )
            return DEFAULT;

        String id = object.getId();
        DatabaseFront database = DatabaseProvider.databaseLayer();
        boolean foundAnything = database.load(object.getClass()).matching("_id").with(id) != null;
        return foundAnything ? VALID : INVALID;
    }

    public Status isId(String string, Class<? extends DatabaseObject> type) {
        if ( string == null || string.isEmpty() )
            return DEFAULT;

        DatabaseFront database = DatabaseProvider.databaseLayer();
        boolean foundAnything = database.load(type).matching("_id").with(string) != null;
        return foundAnything ? VALID : INVALID;
    }

    public Status isNaN(Double number) {
        if ( number == null )
            return DEFAULT;

        return number.isNaN() ? VALID : INVALID;
    }

    public Status isNaN(Float number) {
        if ( number == null )
            return DEFAULT;

        return number.isNaN() ? VALID : INVALID;
    }

    public enum Status {
        DEFAULT, VALID, INVALID;

        public boolean result() {
            switch ( this ) {
                case VALID: return true;
                case INVALID: return false;
                default: return true;
            }
        }

        public boolean invertedResult() {
            switch ( this ) {
                case VALID: return false;
                case INVALID: return true;
                default: return true;
            }
        }
    }
}
