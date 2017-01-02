package io.snowcamp.papaya.spi;

import java.util.function.Supplier;

import io.snowcamp.papaya.api.DBFactory;
import io.snowcamp.papaya.api.DBKind;
import io.snowcamp.papaya.inmemory.InMemoryDBFactory;

public enum StandardKind implements DBKind {
  IN_MEMORY(InMemoryDBFactory::new),
  FILE(() -> { throw new UnsupportedOperationException(); });
  
  private final Supplier<DBFactory> supplier;
  
  private StandardKind(Supplier<DBFactory> supplier) {
    this.supplier = supplier;
  }

  @Override
  public DBFactory factory() {
    return supplier.get();
  }
}