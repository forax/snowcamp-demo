package io.snowcamp.papaya.api;

import io.snowcamp.papaya.doc.Document;
import io.snowcamp.papaya.doc.Sequence;

public interface DBFactory {
  DB create(String name);
  
  <T> T createAnEmptyObject(Class<T> type);
  Document createAnEmptyDocument();
  Sequence createAnEmptySequence();
  Document createADocument(String document) ;
}
