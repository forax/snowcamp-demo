package io.snowcamp.papaya.spi;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Supplier;

import io.snowcamp.papaya.api.DBFactory;
import io.snowcamp.papaya.api.DBKind;
import io.snowcamp.papaya.inmemory.InMemoryDBFactory;

public enum StandardKind implements DBKind {
  IN_MEMORY(() -> {
    return new InMemoryDBFactory();
    //ServiceLoader<DBFactory> loader = ServiceLoader.load(DBFactory.class, DBFactory.class.getClassLoader());
    //Iterator<DBFactory> it = loader.iterator();
    //return it.next();  // you should really use findFirst() here !
  }),
  FILE(() -> { throw new UnsupportedOperationException(); });
  
  private final Supplier<? extends DBFactory> supplier;
  
  private StandardKind(Supplier<? extends DBFactory> supplier) {
    this.supplier = supplier;
  }

  @Override
  public DBFactory factory() {
    return supplier.get();
  }
}