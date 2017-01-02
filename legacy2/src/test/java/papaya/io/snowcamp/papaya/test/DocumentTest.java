package io.snowcamp.papaya.test;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

import io.snowcamp.papaya.api.DBFactory;
import io.snowcamp.papaya.doc.Document;
import io.snowcamp.papaya.spi.StandardKind;

@SuppressWarnings("static-method")
public class DocumentTest {
  @Test
  public void blob1() {
    DBFactory factory = StandardKind.IN_MEMORY.factory();
    Document doc = factory.createAnEmptyDocument();
    byte[] blob = new byte[] { 'h', 'e', 'l', 'l', 'o'};
    doc.putBlob("test", blob);
    
    assertTrue(doc.getBlob("test").isPresent());
    assertFalse(doc.getBlob("anotherTest").isPresent());
    assertEquals(ByteBuffer.wrap(blob), doc.getBlob("test").map(ByteBuffer::wrap).get());
  }
}
