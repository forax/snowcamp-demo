package io.snowcamp.papaya.test;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import io.snowcamp.papaya.api.DB;
import io.snowcamp.papaya.api.DBFactory;
import io.snowcamp.papaya.doc.Document;
import io.snowcamp.papaya.spi.StandardKind;

@SuppressWarnings("static-method")
public class DBObjectAPI {
  public interface TestValue {
    String getFoo();
    void setFoo(String foo);
  }
  
  @Test(expected = IllegalStateException.class)
  public void testInMemoryDBCloseCreateADocument() {
    DBFactory factory = StandardKind.IN_MEMORY.factory();
    try(DB db = factory.create("test")) {
      db.close();
      db.createAnEmptyObject(TestValue.class);
    }
  }
  
  @Test
  public void testInMemoryDBfindAllAppend() {
    DBFactory factory = StandardKind.IN_MEMORY.factory();
    TestValue object = factory.createAnEmptyObject(TestValue.class);
    object.setFoo("bang");
    
    Optional<TestValue> maybeObject;
    try(DB db = factory.create("foobar")) {  
      db.appendObject(object);
      maybeObject = db.findAllObjects(TestValue.class).findFirst();
    }
    
    assertEquals("bang", maybeObject.map(TestValue::getFoo).get());
  }
  
  @Test
  public void testInMemoryDBfindAllAppend2() {
    DBFactory factory = StandardKind.IN_MEMORY.factory();
    TestValue object = factory.createAnEmptyObject(TestValue.class);
    object.setFoo("hello");
    
    Optional<Document> maybeDoc;
    try(DB db = factory.create("foobaz")) {  
      db.appendObject(object);
      maybeDoc = db.findAllDocuments().findFirst();
    }
    
    assertEquals("hello", maybeDoc.flatMap(d -> d.get("foo", String.class)).get());
  }
  
  @SuppressWarnings("unused")
  public static class Pair {
    private String first = "theFirst";
    private String second = "theSecond";
  }
  
  @Test
  public void testInMemoryDBPair() {
    DBFactory factory = StandardKind.IN_MEMORY.factory();
    
    Document doc;
    try(DB db = factory.create("foobaz")) {  
      db.appendObject(new Pair());
      doc = db.findAllDocuments().findFirst().get();
    }
    
    assertEquals("theFirst", doc.get("first"));
    assertEquals("theSecond", doc.get("second"));
  }
}
