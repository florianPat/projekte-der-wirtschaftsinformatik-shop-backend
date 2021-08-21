package fhdw.pdw.jacksonserialisation;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

public class CustomSerializerProvider extends DefaultSerializerProvider {
  public CustomSerializerProvider() {
    super();
  }

  public CustomSerializerProvider(DefaultSerializerProvider src) {
    super(src);
  }

  protected CustomSerializerProvider(
      SerializerProvider src, SerializationConfig config, SerializerFactory f) {
    super(src, config, f);
  }

  private static final long serialVersionUID = 1L;

  @Override
  public DefaultSerializerProvider copy() {
    if (getClass() != CustomSerializerProvider.class) {
      return super.copy();
    }
    return new CustomSerializerProvider(this);
  }

  @Override
  public CustomSerializerProvider createInstance(
      SerializationConfig config, SerializerFactory jsf) {
    return new CustomSerializerProvider(this, config, jsf);
  }

  public void resetMemoryCircularReference() {
    if (_seenObjectIds != null) {
      _seenObjectIds.clear();
    }
  }
}
