package fhdw.pdw.model.dto;

public class JwtAuthenticationResponse {
  protected String token;
  protected String tokenType = "Bearer";

  public JwtAuthenticationResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }
}
