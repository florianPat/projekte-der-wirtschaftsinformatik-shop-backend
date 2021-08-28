package fhdw.pdw.constraints;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import fhdw.pdw.constraints.validators.UniqueUserEmailValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {UniqueUserEmailValidator.class})
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface UniqueUserEmail {
  String message() default "{fhdw.pdw.constraints.UniqueUserEmail.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  // String[] value();
}
