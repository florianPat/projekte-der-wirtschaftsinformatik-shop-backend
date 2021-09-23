package fhdw.pdw.jacksonserialisation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Um bei Arrays nicht nur das erste Element zu serialisieren, sondnern alle Objekte in einem
 * neuen Eintrag isoliert voneinander zu behandeln, wurde der CollectionSerializer überschrieben
 */
public class CustomCollectionSerializer extends CollectionSerializer {
  private static final long serialVersionUID = 1L;

  public CustomCollectionSerializer(
      JavaType elemType,
      boolean staticTyping,
      TypeSerializer vts,
      JsonSerializer<Object> valueSerializer) {
    super(elemType, staticTyping, vts, valueSerializer);
  }

  @Deprecated // since 2.6
  public CustomCollectionSerializer(
      JavaType elemType,
      boolean staticTyping,
      TypeSerializer vts,
      BeanProperty property,
      JsonSerializer<Object> valueSerializer) {
    super(elemType, staticTyping, vts, property, valueSerializer);
  }

  public CustomCollectionSerializer(
      CollectionSerializer src,
      BeanProperty property,
      TypeSerializer vts,
      JsonSerializer<?> valueSerializer,
      Boolean unwrapSingle) {
    super(src, property, vts, valueSerializer, unwrapSingle);
  }

  @Override
  public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
    return new CustomCollectionSerializer(this, _property, vts, _elementSerializer, _unwrapSingle);
  }

  @Override
  public CustomCollectionSerializer withResolved(
      BeanProperty property,
      TypeSerializer vts,
      JsonSerializer<?> elementSerializer,
      Boolean unwrapSingle) {
    return new CustomCollectionSerializer(this, property, vts, elementSerializer, unwrapSingle);
  }

  @Override
  public void serializeContents(Collection<?> value, JsonGenerator g, SerializerProvider provider)
      throws IOException {
    g.setCurrentValue(value);
    if (_elementSerializer != null) {
      serializeContentsUsing(value, g, provider, _elementSerializer);
      return;
    }
    Iterator<?> it = value.iterator();
    if (!it.hasNext()) {
      return;
    }
    PropertySerializerMap serializers = _dynamicSerializers;
    final TypeSerializer typeSer = _valueTypeSerializer;

    int i = 0;
    try {
      do {
        Object elem = it.next();

        if (!(g instanceof CustomUTF8JsonGenerator)) {
          throw new RuntimeException("The JsonGenerator needs to be a CustomUTF8JsonGenerator!");
        }
        JsonWriteContext writeContext = ((CustomUTF8JsonGenerator) g).getWriteContext();
        if (writeContext.getParent().inRoot()) {
          if (!(provider instanceof CustomSerializerProvider)) {
            throw new RuntimeException("The DefaultSerialisationProvider needs to be overridden!");
          }
          ((CustomSerializerProvider) provider).resetMemoryCircularReference();
        }

        if (elem == null) {
          provider.defaultSerializeNull(g);
        } else {
          Class<?> cc = elem.getClass();
          JsonSerializer<Object> serializer = serializers.serializerFor(cc);
          if (serializer == null) {
            if (_elementType.hasGenericTypes()) {
              serializer =
                  _findAndAddDynamic(
                      serializers, provider.constructSpecializedType(_elementType, cc), provider);
            } else {
              serializer = _findAndAddDynamic(serializers, cc, provider);
            }
            serializers = _dynamicSerializers;
          }
          if (typeSer == null) {
            serializer.serialize(elem, g, provider);
          } else {
            serializer.serializeWithType(elem, g, provider, typeSer);
          }
        }
        ++i;
      } while (it.hasNext());
    } catch (Exception e) {
      wrapAndThrow(provider, e, value, i);
    }
  }

  public void serializeContentsUsing(
      Collection<?> value, JsonGenerator g, SerializerProvider provider, JsonSerializer<Object> ser)
      throws IOException {
    Iterator<?> it = value.iterator();
    if (it.hasNext()) {
      TypeSerializer typeSer = _valueTypeSerializer;
      int i = 0;
      do {
        Object elem = it.next();

        if (!(provider instanceof CustomSerializerProvider)) {
          throw new RuntimeException("The DefaultSerialisationProvider needs to be overridden!");
        }
        ((CustomSerializerProvider) provider).resetMemoryCircularReference();

        try {
          if (elem == null) {
            provider.defaultSerializeNull(g);
          } else {
            if (typeSer == null) {
              ser.serialize(elem, g, provider);
            } else {
              ser.serializeWithType(elem, g, provider, typeSer);
            }
          }
          ++i;
        } catch (Exception e) {
          wrapAndThrow(provider, e, value, i);
        }
      } while (it.hasNext());
    }
  }
}
