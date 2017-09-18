package dk.developer.validation.single;

import dk.developer.database.DatabaseObject;
import dk.developer.validation.GenericValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StoredConstraintValidator implements ConstraintValidator<Stored, DatabaseObject> {
    @Override
    public void initialize(Stored constraintAnnotation) {
    }

    @Override
    public boolean isValid(DatabaseObject value, ConstraintValidatorContext context) {
        return GenericValidator.get().isStored(value).result();
    }
}
