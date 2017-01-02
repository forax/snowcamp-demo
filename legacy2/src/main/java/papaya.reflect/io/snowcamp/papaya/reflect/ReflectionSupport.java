package io.snowcamp.papaya.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import io.snowcamp.papaya.doc.Document;
import io.snowcamp.papaya.doc.Sequence;
import sun.misc.Unsafe;

public class ReflectionSupport {
  private static final class TypedObjetInvocationHandler implements InvocationHandler {
    final Document document;
    private final Supplier<Document> docSupplier;
    private final Supplier<Sequence> seqSupplier;

    TypedObjetInvocationHandler(Document document, Supplier<Document> docSupplier, Supplier<Sequence> seqSupplier) {
      this.document = document;
      this.docSupplier = docSupplier;
      this.seqSupplier = seqSupplier;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      String name = method.getName();
      if (method.getDeclaringClass() == Object.class) {
        switch(name) {
        case "hashCode":
          return System.identityHashCode(proxy);
        case "equals":
          return proxy == args[0];
        case "toString":
          return document.toString();
        default:
          throw new UnsupportedOperationException(method + " not supported");
        }
      }
      
      if (name.startsWith("get") && name.length() > 3) {
        String key = propertyName(name);
        return unwrap(docSupplier, seqSupplier, document.get(key), method.getReturnType());
      }
      
      if (name.startsWith("set") && name.length() > 3) {
        String key = propertyName(name);
        document.put(key, wrap(docSupplier, seqSupplier, args[0]));
        return null;
      }
      
      throw new UnsupportedOperationException(method + " not supported");
    }
  }

  public static <T> T asTypedObject(Supplier<Document> docSupplier, Supplier<Sequence> seqSupplier, Document document, Class<T> type) {
    return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type },
        new TypedObjetInvocationHandler(document, docSupplier, seqSupplier)));
  }

  static String propertyName(String name) {
    return Character.toLowerCase(name.charAt(3)) + name.substring(4);
  }

  public static Document asDocument(Supplier<Document> docSupplier, Supplier<Sequence> seqSupplier, Object element) {
    Class<?> type = element.getClass();
    InvocationHandler invocationHandler;
    if (Proxy.isProxyClass(type) && (invocationHandler = Proxy.getInvocationHandler(element)) instanceof TypedObjetInvocationHandler) {
      return ((TypedObjetInvocationHandler)invocationHandler).document;
    }
    
    Document document = docSupplier.get();
    fields(type)
      .forEach(f -> { 
        Object result = call(element, f);
        document.put(f.getName(), wrap(docSupplier, seqSupplier, result));
      });
    return document;
  }
  
  static Object wrap(Supplier<Document> docSupplier, Supplier<Sequence> seqSupplier, Object element) {
    Class<?> type = element.getClass();
    if (type.isPrimitive() || type == String.class || Document.class.isAssignableFrom(type) || Sequence.class.isAssignableFrom(type)) {
      return element;
    }
    if (Iterable.class.isAssignableFrom(type)) {
      Sequence seq = seqSupplier.get();
      for(Object v: (Iterable<?>)element) {
        seq.add(wrap(docSupplier, seqSupplier, v));
      }
      return seq;
    }
    if (Map.class.isAssignableFrom(type)) {
      Document doc = docSupplier.get();
      ((Map<?,?>)element).forEach((key, v) -> {
        doc.put(key.toString(), wrap(docSupplier, seqSupplier, v));
      });
      return doc;
    }
    return asDocument(docSupplier, seqSupplier, element);
  }
  
  static Object unwrap(Supplier<Document> docSupplier, Supplier<Sequence> seqSupplier, Object element, Class<?> type) {
    if (type.isPrimitive() || type == String.class || Iterable.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type)) {
      return element;
    }
    if (!(element instanceof Document)) {
      throw new UnsupportedOperationException("can not transform " + element + " to " + type.getSimpleName());
    }
    return asTypedObject(docSupplier, seqSupplier, (Document)element, type);
  }
  
  private static Stream<Field> fields(Class<?> type) {
    return allSupertypes(type)
        .flatMap(t -> Arrays.stream(t.getDeclaredFields()))
        .filter(field -> !Modifier.isTransient(field.getModifiers()))
        .peek(field -> field.setAccessible(true));
  }

  private static Stream<Class<?>> allSupertypes(Class<?> type) {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<Class<?>>() {
      private Class<?> t = type;
      
      @Override
      public boolean hasNext() {
        return t != null;
      }
      @Override
      public Class<?> next() {
        try {
          return t;
        } finally {
          t = t.getSuperclass();
        }
      }
    }, 0), false);
  }

  private static Object call(Object target, Field field) {
    try {
      return field.get(target);
    } catch(IllegalAccessException e) {
      UNSAFE.throwException(e);
      throw null;
    }
  }
  
  private static final Unsafe UNSAFE;
  static {
    Field field;
    try {
      field = Unsafe.class.getDeclaredField("theUnsafe");
    } catch (NoSuchFieldException e) {
      throw new AssertionError(e);
    }
    field.setAccessible(true);
    try {
      UNSAFE = (Unsafe)field.get(null);
    } catch (IllegalAccessException e) {
      throw new AssertionError(e);
    }
  }
}
