package dk.developer.validation.single;

import dk.developer.validation.GenericValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NaNConstraintValidator {
    public static class NaNDoubleConstraint implements ConstraintValidator<NaN, Double> {
        @Override
        public void initialize(NaN constraintAnnotation) {
        }

        @Override
        public boolean isValid(Double value, ConstraintValidatorContext context) {
            switch ( GenericValidator.get().isNaN(value) ) {
                case VALID:
                    return true;
                case INVALID:
                    return false;
                default:
                    return true;
            }
        }
    }

    public static class NaNFloatConstraint implements ConstraintValidator<NaN, Float> {
        @Override
        public void initialize(NaN constraintAnnotation) {
        }

        @Override
        public boolean isValid(Float value, ConstraintValidatorContext context) {
            return GenericValidator.get().isNaN(value).result();
        }
    }
}
