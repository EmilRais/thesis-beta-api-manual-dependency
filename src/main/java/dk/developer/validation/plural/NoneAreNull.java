package dk.developer.validation.plural;

import cz.jirutka.validator.collection.CommonEachValidator;
import cz.jirutka.validator.collection.constraints.EachConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@EachConstraint(validateAs = NotNull.class)
@Constraint(validatedBy = CommonEachValidator.class)
public @interface NoneAreNull {
    String message() default "None of the values may be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
