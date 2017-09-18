package dk.developer.validation.plural;

import cz.jirutka.validator.collection.CommonEachValidator;
import cz.jirutka.validator.collection.constraints.EachConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@EachConstraint(validateAs = Size.class)
@Constraint(validatedBy = CommonEachValidator.class)
public @interface AllAreSize {
    int min() default 0;
    int max() default Integer.MAX_VALUE;

    String message() default "All of the values must adhere to the size";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
