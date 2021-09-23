package fhdw.pdw.jacksonserialisation;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Die MappingsJsonFactory wird überschrieben, um einen eigens erstellten UTF8JsonGenerator
 * zu erzeugen und um dadruch im CollectionSerializer die wiederholte Serialisierung bei einem
 * neuen Array Eintrag zu erzwingen
 */
public class CustomMappingJsonFactory extends MappingJsonFactory {
  private static final long serialVersionUID = -1; // since 2.7

  public CustomMappingJsonFactory() {
    super();
  }

  public CustomMappingJsonFactory(ObjectMapper mapper) {
    super(mapper);
  }

  public CustomMappingJsonFactory(JsonFactory src, ObjectMapper mapper) {
    super(src, mapper);
  }

  // @since 2.1
  @Override
  public JsonFactory copy() {
    _checkInvalidCopy(CustomMappingJsonFactory.class);
    // note: as with base class, must NOT copy mapper reference
    return new CustomMappingJsonFactory(this, null);
  }

  @Override
  public MatchStrength hasFormat(InputAccessor acc) throws IOException {
    if (getClass() == CustomMappingJsonFactory.class) {
      return hasJSONFormat(acc);
    }
    return null;
  }

  @Override
  protected JsonGenerator _createUTF8Generator(OutputStream out, IOContext ctxt)
      throws IOException {
    UTF8JsonGenerator gen =
        new CustomUTF8JsonGenerator(ctxt, _generatorFeatures, _objectCodec, out, _quoteChar);
    if (_maximumNonEscapedChar > 0) {
      gen.setHighestNonEscapedChar(_maximumNonEscapedChar);
    }
    if (_characterEscapes != null) {
      gen.setCharacterEscapes(_characterEscapes);
    }
    SerializableString rootSep = _rootValueSeparator;
    if (rootSep != DEFAULT_ROOT_VALUE_SEPARATOR) {
      gen.setRootValueSeparator(rootSep);
    }
    return gen;
  }
}
