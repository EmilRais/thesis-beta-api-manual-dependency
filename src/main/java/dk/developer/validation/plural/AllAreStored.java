package dk.developer.validation.plural;

import cz.jirutka.validator.collection.CommonEachValidator;
import cz.jirutka.validator.collection.constraints.EachConstraint;
import dk.developer.validation.single.Stored;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = CommonEachValidator.class)
@EachConstraint(validateAs = Stored.class)
public @interface AllAreStored {
    String message() default "The database objects were not stored in the database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
