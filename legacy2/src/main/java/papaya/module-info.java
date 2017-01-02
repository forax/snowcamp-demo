module papaya {
  requires json;
  requires transitive papaya.doc;
  requires papaya.reflect;
  
  exports io.snowcamp.papaya.api;
  exports io.snowcamp.papaya.spi;
}