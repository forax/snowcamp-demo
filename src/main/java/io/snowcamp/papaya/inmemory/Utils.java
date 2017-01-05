package io.snowcamp.papaya.inmemory;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.RandomAccess;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import io.snowcamp.papaya.doc.Document;
import io.snowcamp.papaya.doc.Sequence;

class Utils {
  public static JSONObject parseDocument(String document) {
    return new JSONObject(new JSONTokener(document));
  }

  public static Document jSONObjectAsDocument(JSONObject object) {
    return new DocumentImpl(object);
  }
  
  public static Sequence jSONArrayAsSequence(JSONArray array) {
    return new SequenceImpl(array);
  }
  
  static class DocumentImpl extends AbstractMap<String, Object> implements Document {
    final JSONObject object;
       
    DocumentImpl(JSONObject object) {
      this.object = object;
    }

    @Override
    public String toString() {
      return object.toString(2);
    }
    
    @Override
    public Object get(Object key) {
      if (!(key instanceof String)) {
        return null;
      }
      return wrap(object.opt((String)key));
    }
    
    @Override
    public Object put(String key, Object value) {
      Object oldValue = wrap(object.opt(key));
      object.put(key, unwrap(value));
      return oldValue;
    }
    
    @Override
    public Set<Entry<String, Object>> entrySet() {
      return new AbstractSet<Entry<String, Object>>() {
        @Override
        public int size() {
          return object.length();
        }
        
        @Override
        public Iterator<Entry<String, Object>> iterator() {
          Iterator<String> it = object.keys();
          return new Iterator<Entry<String, Object>>() {
            @Override
            public boolean hasNext() {
              return it.hasNext();
            }

            @Override
            public Entry<String, Object> next() {
              String key = it.next();
              Object value = wrap(object.get(key));
              return new SimpleImmutableEntry<>(key, value);
            }
          };
        }
      };
    }
  }
  
  static class SequenceImpl extends AbstractList<Object> implements RandomAccess, Sequence {
    final JSONArray array;

    SequenceImpl(JSONArray array) {
      this.array = array;
    }

    @Override
    public String toString() {
      return array.toString(2);
    }
    
    @Override
    public int size() {
      return array.length();
    }
    
    @Override
    public Object get(int index) {
      return wrap(array.get(index));
    }
    
    @Override
    public Object set(int index, Object object) {
      Object value = wrap(get(index));
      array.put(index, unwrap(object));
      return value;
    }
    
    @Override
    public boolean add(Object object) {
      array.put(unwrap(object));
      return true;
    }
  }
  
  static Object wrap(Object object) {
    if (object instanceof JSONObject) {
      return new DocumentImpl((JSONObject)object);
    }
    if (object instanceof JSONArray) {
      return new SequenceImpl((JSONArray)object);
    }
    return object;
  }
  
  static Object unwrap(Object object) {
    if (object instanceof DocumentImpl) {
      return ((DocumentImpl)object).object;
    }
    if (object instanceof SequenceImpl) {
      return ((SequenceImpl)object).array;
    }
    if (object == null || object instanceof String || object instanceof Number) {
      return object;
    }
    throw new UnsupportedOperationException("invalid JSON type " + object);
  }
}
