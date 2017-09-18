package dk.developer.validation.single;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {NaNConstraintValidator.NaNDoubleConstraint.class, NaNConstraintValidator.NaNFloatConstraint.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface NaN {
    String message() default "Has to be NaN";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}