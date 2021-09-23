package fhdw.pdw;

import com.fasterxml.jackson.databind.ObjectMapper;
import fhdw.pdw.jacksonserialisation.CustomJackson2ObjectMapperBuilder;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer, ApplicationContextAware {
  protected ApplicationContext applicationContext;

  /**
   * Diese Methode verändert den Objektmapper von Jackson, um bei Arrays nicht nur die ID, sondern
   * das ganze Objekt zu serialisieren
   */
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    WebMvcConfigurer.super.extendMessageConverters(converters);

    for (HttpMessageConverter<?> converter : converters) {
      if (converter instanceof AbstractJackson2HttpMessageConverter) {
        AbstractJackson2HttpMessageConverter jackson2HttpMessageConverter =
            (AbstractJackson2HttpMessageConverter) converter;
        Jackson2ObjectMapperBuilder builder = CustomJackson2ObjectMapperBuilder.json();
        if (this.applicationContext != null) {
          builder.applicationContext(this.applicationContext);
        }
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        objectMapper = builder.build();
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
      }
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
