module papaya {
  requires transitive papaya.doc;
  
  exports io.snowcamp.papaya.api;
  exports io.snowcamp.papaya.spi;
  
  uses io.snowcamp.papaya.api.DBFactory;
}