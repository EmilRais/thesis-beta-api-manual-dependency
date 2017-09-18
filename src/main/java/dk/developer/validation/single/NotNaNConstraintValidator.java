package dk.developer.validation.single;

import dk.developer.validation.GenericValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNaNConstraintValidator {
    public static class NotNaNDoubleConstraint implements ConstraintValidator<NotNaN, Double> {
        @Override
        public void initialize(NotNaN constraintAnnotation) {
        }

        @Override
        public boolean isValid(Double value, ConstraintValidatorContext context) {
            switch ( GenericValidator.get().isNaN(value) ) {
                case VALID:
                    return false;
                case INVALID:
                    return true;
                default:
                    return true;
            }
        }
    }

    public static class NotNaNFloatConstraint implements ConstraintValidator<NotNaN, Float> {
        @Override
        public void initialize(NotNaN constraintAnnotation) {
        }

        @Override
        public boolean isValid(Float value, ConstraintValidatorContext context) {
            return GenericValidator.get().isNaN(value).invertedResult();
        }
    }
}
