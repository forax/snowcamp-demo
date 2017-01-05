package io.snowcamp.papaya.doc;

import java.util.Collections;
import java.util.List;

public interface Sequence extends List<Object> {
  // a sequence of values that can be viewed as JSON value
  
  @SuppressWarnings("unchecked") // the cast is obviously wrong but works because of the erasure
  default <T> List<T> asCheckedList(Class<T> elementType) {  
    return Collections.checkedList((List<T>)this, elementType);
  }
}
