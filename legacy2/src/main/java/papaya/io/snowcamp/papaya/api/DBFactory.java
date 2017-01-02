package io.snowcamp.papaya.api;

public interface DBFactory {
  DB create(String name);
  
  <T> T createAnEmptyObject(Class<T> type);
  Document createAnEmptyDocument();
  Sequence createAnEmptySequence();
  Document createADocument(String document) ;
}
