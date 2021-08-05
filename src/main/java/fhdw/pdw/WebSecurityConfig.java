package fhdw.pdw;

import fhdw.pdw.security.CustomUserDetailsService;
import fhdw.pdw.security.JwtAuthenticationEntryPoint;
import fhdw.pdw.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled =
        true, // Enables @Secured("ROLE_ADMIN", "IS_AUTHENTICATED_ANONYMOUSLY") annotation
    jsr250Enabled = true, // Enables @RolesAllowed("ROLE_ADMIN") annotation
    prePostEnabled = true // Enables @PreAuthorize("isAnonymous()") / "hasRole('USER')" annotation
    )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired protected CustomUserDetailsService customUserDetailService;

  /** Used to return 401 unauthorized error to users who try to access a protected ressource */
  @Autowired protected JwtAuthenticationEntryPoint unauthorizedHandler;

  /** Set user details from jwt token from request in the springs SecurityContext */
  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Main spring security interface to authenticate a user (in-memory, LDAP, JDBC, or custom, which
   * we use: customUserDetailService and set the passwordEncoder
   */
  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(customUserDetailService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requiresChannel()
        .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
        .requiresSecure();

    http.csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and()
        .authorizeRequests()
        .antMatchers("api/auth/**")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/users/**")
        .permitAll()
        .anyRequest()
        .permitAll();

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
