package fhdw.pdw.model.dto;

import javax.validation.ConstraintViolation;

public class ErrorResponse extends ApiResponse {
  protected String entity;
  protected String property;
  protected Object invalidValue;

  public ErrorResponse() {}

  public ErrorResponse(String entity, String property, Object invalidValue, String message) {
    super(message);
    this.entity = entity;
    this.property = property;
    this.invalidValue = invalidValue;
    this.message = message;
  }

  public ErrorResponse(ConstraintViolation<?> violation) {
    super(violation.getMessage());
    this.entity = violation.getRootBeanClass().getName();
    this.property = violation.getPropertyPath().toString();
    this.invalidValue = violation.getInvalidValue();
  }

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public Object getInvalidValue() {
    return invalidValue;
  }

  public void setInvalidValue(Object invalidValue) {
    this.invalidValue = invalidValue;
  }
}
