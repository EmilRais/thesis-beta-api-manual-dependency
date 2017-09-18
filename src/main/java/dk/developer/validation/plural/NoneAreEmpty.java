package dk.developer.validation.plural;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@NoneAreNull
@AllAreSize(min = 1)
@ReportAsSingleViolation
public @interface NoneAreEmpty {
    String message() default "None of the values may be null or empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
