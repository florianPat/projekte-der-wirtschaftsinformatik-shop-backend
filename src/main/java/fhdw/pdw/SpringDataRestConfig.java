package fhdw.pdw;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class SpringDataRestConfig implements RepositoryRestConfigurer {
  @Override
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config, CorsRegistry cors) {
    cors.addMapping("/**")
        .allowedOriginPatterns(
            "https://fhdw-pdw-shop-frontend*.vercel.app/", "http://localhost:3000")
        .allowedMethods("*");
  }
}
