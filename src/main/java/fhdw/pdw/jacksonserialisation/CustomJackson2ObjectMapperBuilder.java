package fhdw.pdw.jacksonserialisation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Der Jackson Object Mapper Builder wird überschrieben, um den eigens erstellten
 * Object Mapper zu erzeugen, welcher widerum den eigens erstellten CustomBeanSerialisierer
 * in das Jackson System injizert
 */
public class CustomJackson2ObjectMapperBuilder extends Jackson2ObjectMapperBuilder {
  @Override
  public <T extends ObjectMapper> T build() {
    this.factory(new CustomMappingJsonFactory());
    T result = super.build();
    result.setSerializerProvider(new CustomSerializerProvider());
    result.setSerializerFactory(CustomBeanSerializerFactory.instance);
    return result;
  }

  public static Jackson2ObjectMapperBuilder json() {
    return new CustomJackson2ObjectMapperBuilder();
  }
}
