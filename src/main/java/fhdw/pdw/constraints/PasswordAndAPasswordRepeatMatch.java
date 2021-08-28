package fhdw.pdw.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import fhdw.pdw.constraints.validators.PasswordAndAPasswordRepeatMatchValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {PasswordAndAPasswordRepeatMatchValidator.class})
@Documented
public @interface PasswordAndAPasswordRepeatMatch {
  String message() default "{fhdw.pdw.constraints.PasswordAndAPasswordRepeatMatch.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
