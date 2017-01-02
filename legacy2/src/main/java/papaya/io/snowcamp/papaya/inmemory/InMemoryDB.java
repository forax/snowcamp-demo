package io.snowcamp.papaya.inmemory;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import io.snowcamp.papaya.api.DB;
import io.snowcamp.papaya.doc.Document;
import io.snowcamp.papaya.reflect.ReflectionSupport;

public class InMemoryDB implements DB {
  private final String name;
  private boolean closed;
  private final InMemoryDBFactory factory;
  private final ArrayList<Document> documents = new ArrayList<>();

  public InMemoryDB(String name, InMemoryDBFactory factory) {
    this.name = Objects.requireNonNull(name);
    this.factory = factory;
  }

  @Override
  public String name() {
    return name;
  }
  
  @Override
  public void close() {
    closed = true;
  }
  
  private void checkClosed() {
    if (closed) {
      throw new IllegalStateException("DB closed !");
    }
  }

  @Override
  public <T> T createAnEmptyObject(Class<T> type) {
    checkClosed();
    return factory.createAnEmptyObject(type);
  }
  @Override
  public <T> Stream<T> findAllObjects(Class<T> type) {
    checkClosed();
    return findAllDocuments().map(element -> ReflectionSupport.asTypedObject(factory::createAnEmptyDocument, factory::createAnEmptySequence, element, type));
  }
  @Override
  public void appendObject(Object element) {
    checkClosed();
    Document doc = ReflectionSupport.asDocument(factory::createAnEmptyDocument, factory::createAnEmptySequence, element);
    documents.add(doc);
  }

  @Override
  public Document createAnEmptyDocument() {
    checkClosed();
    return factory.createAnEmptyDocument();
  }
  @Override
  public Document createADocument(String document) {
    checkClosed();
    return factory.createADocument(document);
  }
  @Override
  public Stream<Document> findAllDocuments() {
    checkClosed();
    return documents.stream();
  }
  @Override
  public void appendDocument(Document document) {
    checkClosed();
    documents.add(document);
  }
}
