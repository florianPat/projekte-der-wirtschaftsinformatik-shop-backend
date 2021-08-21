package fhdw.pdw.jacksonserialisation;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

public class CustomBeanSerializerFactory extends BeanSerializerFactory {
  private static final long serialVersionUID = 1;

  public static final CustomBeanSerializerFactory instance = new CustomBeanSerializerFactory(null);

  protected CustomBeanSerializerFactory(SerializerFactoryConfig config) {
    super(config);
  }

  @Override
  public SerializerFactory withConfig(SerializerFactoryConfig config) {
    if (_factoryConfig == config) {
      return this;
    }
    /* 22-Nov-2010, tatu: Handling of subtypes is tricky if we do immutable-with-copy-ctor;
     *    and we pretty much have to here either choose between losing subtype instance
     *    when registering additional serializers, or losing serializers.
     *    Instead, let's actually just throw an error if this method is called when subtype
     *    has not properly overridden this method; this to indicate problem as soon as possible.
     */
    if (getClass() != CustomBeanSerializerFactory.class) {
      throw new IllegalStateException(
          "Subtype of CustomBeanSerializerFactory ("
              + getClass().getName()
              + ") has not properly overridden method 'withConfig': cannot instantiate subtype with "
              + "additional serializer definitions");
    }
    return new CustomBeanSerializerFactory(config);
  }

  @Override
  public ContainerSerializer<?> buildCollectionSerializer(
      JavaType elemType,
      boolean staticTyping,
      TypeSerializer vts,
      JsonSerializer<Object> valueSerializer) {
    System.out.println("BUILDING COLLECTION SERIALIZER");
    return new CustomCollectionSerializer(elemType, staticTyping, vts, valueSerializer);
  }
}
