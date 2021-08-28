package fhdw.pdw.constraints.validators;

import fhdw.pdw.constraints.PasswordAndAPasswordRepeatMatch;
import fhdw.pdw.model.dto.UserDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordAndAPasswordRepeatMatchValidator
    implements ConstraintValidator<PasswordAndAPasswordRepeatMatch, UserDto> {
  @Override
  public void initialize(PasswordAndAPasswordRepeatMatch constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(UserDto value, ConstraintValidatorContext context) {
    return value.getPassword().equals(value.getPasswordRepeat());
  }
}
