package dk.developer.validation.single;

import dk.developer.database.DatabaseObject;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = IdConstraintValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Id {
    Class<? extends DatabaseObject> of();

    String message() default "Not the id of a database object";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
