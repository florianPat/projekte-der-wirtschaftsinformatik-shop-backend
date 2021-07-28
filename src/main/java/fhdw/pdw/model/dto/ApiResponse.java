package fhdw.pdw.model.dto;

public class ApiResponse {
  protected Boolean success;
  protected String message;

  public ApiResponse(Boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
