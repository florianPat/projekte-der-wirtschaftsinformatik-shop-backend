package fhdw.pdw.jacksonserialisation;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import java.io.OutputStream;

public class CustomUTF8JsonGenerator extends UTF8JsonGenerator {
  public CustomUTF8JsonGenerator(
      IOContext ctxt, int features, ObjectCodec codec, OutputStream out, char quoteChar) {
    super(ctxt, features, codec, out, quoteChar);
  }

  public CustomUTF8JsonGenerator(
      IOContext ctxt,
      int features,
      ObjectCodec codec,
      OutputStream out,
      char quoteChar,
      byte[] outputBuffer,
      int outputOffset,
      boolean bufferRecyclable) {
    super(ctxt, features, codec, out, quoteChar, outputBuffer, outputOffset, bufferRecyclable);
  }

  @Deprecated // since 2.10
  public CustomUTF8JsonGenerator(
      IOContext ctxt, int features, ObjectCodec codec, OutputStream out) {
    super(ctxt, features, codec, out);
  }

  @Deprecated // since 2.10
  public CustomUTF8JsonGenerator(
      IOContext ctxt,
      int features,
      ObjectCodec codec,
      OutputStream out,
      byte[] outputBuffer,
      int outputOffset,
      boolean bufferRecyclable) {
    super(ctxt, features, codec, out, outputBuffer, outputOffset, bufferRecyclable);
  }

  public JsonWriteContext getWriteContext() {
    return _writeContext;
  }
}
