package dk.developer.validation.single;

import dk.developer.database.DatabaseObject;
import dk.developer.validation.GenericValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotStoredConstraintValidator implements ConstraintValidator<NotStored, DatabaseObject> {
    @Override
    public void initialize(NotStored constraintAnnotation) {
    }

    @Override
    public boolean isValid(DatabaseObject value, ConstraintValidatorContext context) {
        return GenericValidator.get().isStored(value).invertedResult();
    }
}
