package dk.developer.validation.single;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {NotNaNConstraintValidator.NotNaNDoubleConstraint.class, NotNaNConstraintValidator.NotNaNFloatConstraint.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface NotNaN {
    String message() default "May not be NaN";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
