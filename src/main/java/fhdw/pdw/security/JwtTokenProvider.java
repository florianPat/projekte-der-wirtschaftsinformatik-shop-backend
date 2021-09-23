package fhdw.pdw.security;

import io.jsonwebtoken.*;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
  protected static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  @Value("${app.jwtSecret}")
  protected String jwtSecret;

  @Value("${app.jwtExpirationInMs}")
  protected int jwtExpirationInMs;

  /**
   * Generiert den Token bei einem Login mit Hilfe der JWT Bibiliothek
   */
  public String generateToken(Authentication authentication) {
    UserDetail userDetail = (UserDetail) authentication.getPrincipal();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
    return Jwts.builder()
        .setSubject(Integer.toString(userDetail.getId()))
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  /**
   * Extrahiert die Benutzer ID Spalte für eine Datenbankabfrage aus dem JWT Token
   */
  public Integer getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    return Integer.parseInt(claims.getSubject());
  }

  /**
   * Validierung des JWT Tokens auf Gültigkeit hinsichtlich Zeitraum und Benutzer Identifikation
   */
  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature");
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token");
    } catch (ExpiredJwtException e) {
      logger.error("Expired JWT token");
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported JWT token");
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty");
    }
    return false;
  }
}
