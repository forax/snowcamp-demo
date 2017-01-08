package io.snowcamp.papaya.inmemory;

import org.json.JSONArray;
import org.json.JSONObject;

import io.snowcamp.papaya.api.DB;
import io.snowcamp.papaya.api.DBFactory;
import io.snowcamp.papaya.doc.Document;
import io.snowcamp.papaya.doc.Sequence;
import io.snowcamp.papaya.reflect.ReflectionSupport;

public class InMemoryDBFactory implements DBFactory {
  @Override
  public DB create(String name) {
    return new InMemoryDB(name, this);
  }
  
  @Override
  public <T> T createAnEmptyObject(Class<T> type) {
    return ReflectionSupport.asTypedObject(this::createAnEmptyDocument, this::createAnEmptySequence, createAnEmptyDocument(), type);
  }

  @Override
  public Document createAnEmptyDocument() {
    return Utils.jSONObjectAsDocument(new JSONObject());
  }

  @Override
  public Sequence createAnEmptySequence() {
    return Utils.jSONArrayAsSequence(new JSONArray());
  }
  
  @Override
  public Document createADocument(String document) {
    JSONObject object = Utils.parseDocument(document);
    return Utils.jSONObjectAsDocument(object);
  }
}
