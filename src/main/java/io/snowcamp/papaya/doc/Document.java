package io.snowcamp.papaya.doc;

import java.io.IOException;
import java.io.UncheckedIOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public interface Document extends Map<String, Object> {
  // a map of key/value pairs, keys are String, values are any JSON values
  
  default <T> Optional<T> get(String key, Class<T> type) {
    return Optional.ofNullable(get(key)).filter(type::isInstance).map(type::cast);
  }
  
  default OptionalInt getInt(String key) {
    return get(key, Integer.class).map(OptionalInt::of).orElseGet(OptionalInt::empty);
  }
  default OptionalLong getLong(String key) {
    return get(key, Long.class).map(OptionalLong::of).orElseGet(OptionalLong::empty);
  }
  default OptionalDouble getDouble(String key) {
    return get(key, Double.class).map(OptionalDouble::of).orElseGet(OptionalDouble::empty);
  }
  
  default void putBlob(String key, byte[] blob) {
    put(key, new sun.misc.BASE64Encoder().encode(blob));
  }
  default Optional<byte[]> getBlob(String key) {
    return get(key, String.class).map(base64 -> {
      try {
        return new sun.misc.BASE64Decoder().decodeBuffer(base64);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    });
  }
}
