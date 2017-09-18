package dk.developer.validation.single;

import dk.developer.database.DatabaseObject;
import dk.developer.validation.GenericValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdConstraintValidator implements ConstraintValidator<Id, String> {
    private Class<? extends DatabaseObject> type;

    @Override
    public void initialize(Id constraintAnnotation) {
        type = constraintAnnotation.of();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return GenericValidator.get().isId(value, type).result();
    }
}
