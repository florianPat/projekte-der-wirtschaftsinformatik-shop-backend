package fhdw.pdw.model.dto;

public class ApiResponse {
  protected String message;

  public ApiResponse() {}

  public ApiResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
