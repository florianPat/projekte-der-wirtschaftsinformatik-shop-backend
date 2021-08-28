package fhdw.pdw;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class SpringDataRestConfig implements RepositoryRestConfigurer {
  @Autowired protected EntityManager entityManager;
  @Autowired protected Validator validator;

  @Override
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config, CorsRegistry cors) {
    cors.addMapping("/**")
        .allowedOriginPatterns(
            "https://fhdw-pdw-shop-frontend*.vercel.app/", "http://localhost:3000")
        .allowedMethods("*");

    config.exposeIdsFor(
        entityManager.getMetamodel().getEntities().stream()
            .map(Type::getJavaType)
            .toArray(Class[]::new));
  }

  @Override
  public void configureValidatingRepositoryEventListener(
      ValidatingRepositoryEventListener validatingListener) {
    validatingListener.addValidator("afterCreate", validator);
    validatingListener.addValidator("beforeCreate", validator);
    validatingListener.addValidator("afterSave", validator);
    validatingListener.addValidator("beforeSave", validator);
    validatingListener.addValidator("beforeLinkSave", validator);
    validatingListener.addValidator("afterLinkSave", validator);
  }
}
