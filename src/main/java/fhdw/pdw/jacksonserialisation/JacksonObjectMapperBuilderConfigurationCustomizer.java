package fhdw.pdw.jacksonserialisation;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ObjectMapper.class)
class JacksonConfiguration {
  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
  static class JacksonObjectMapperBuilderConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder(
        ApplicationContext applicationContext,
        List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
      Jackson2ObjectMapperBuilder builder = new CustomJackson2ObjectMapperBuilder();
      builder.applicationContext(applicationContext);
      customize(builder, customizers);
      return builder;
    }

    private void customize(
        Jackson2ObjectMapperBuilder builder,
        List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
      for (Jackson2ObjectMapperBuilderCustomizer customizer : customizers) {
        customizer.customize(builder);
      }
    }
  }
}
