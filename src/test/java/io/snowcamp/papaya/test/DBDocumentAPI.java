package io.snowcamp.papaya.test;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import io.snowcamp.papaya.api.DB;
import io.snowcamp.papaya.api.DBFactory;
import io.snowcamp.papaya.doc.Document;
import io.snowcamp.papaya.spi.StandardKind;

@SuppressWarnings("static-method")
public class DBDocumentAPI {
  @Test
  public void testInMemoryDBName() {
    DBFactory factory = StandardKind.IN_MEMORY.factory();
    try(DB db = factory.create("test")) {
      assertEquals("test", db.name());
    }
  }
  
  @Test(expected = IllegalStateException.class)
  public void testInMemoryDBCloseCreateADocument() {
    DBFactory factory = StandardKind.IN_MEMORY.factory();
    try(DB db = factory.create("test")) {
      db.close();
      db.createADocument("");  
    }
  }
  
  @Test(expected = IllegalStateException.class)
  public void testInMemoryDBCloseCreateEmptyDocument() {
    DBFactory factory = StandardKind.IN_MEMORY.factory();
    try(DB db = factory.create("test")) {
      db.close();
      db.createAnEmptyDocument();  
    }
  }
  
  @Test
  public void testInMemoryDBfindAllAppend() {
    DBFactory factory = StandardKind.IN_MEMORY.factory();
    Document doc = factory.createAnEmptyDocument();
    doc.put("answer", 42);
    
    Optional<Document> maybeDoc;
    try(DB db = factory.create("foobar")) {  
      db.appendDocument(doc);
      maybeDoc = db.findAllDocuments().findFirst();
    }
    
    assertEquals(42, (int)maybeDoc.flatMap(d -> d.get("answer", Integer.class)).get());
  }
}
