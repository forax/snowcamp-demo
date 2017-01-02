module papaya.inmemory {
  requires json;
  requires papaya;
  requires papaya.doc;  // should not be necessary but the VM requires it
  requires papaya.reflect;
  
  provides io.snowcamp.papaya.api.DBFactory with
    io.snowcamp.papaya.inmemory.InMemoryDBFactory;
}