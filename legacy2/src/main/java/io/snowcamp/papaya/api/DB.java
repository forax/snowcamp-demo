package io.snowcamp.papaya.api;

import java.util.stream.Stream;

import io.snowcamp.papaya.doc.Document;

public interface DB extends AutoCloseable {
  String name();
  @Override
  void close();
  
  Document createAnEmptyDocument();
  Document createADocument(String document);
  Stream<Document> findAllDocuments();
  void appendDocument(Document document);
  
  <T> T createAnEmptyObject(Class<T> type);
  <T> Stream<T> findAllObjects(Class<T> type);
  void appendObject(Object element);
}
