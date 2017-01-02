package io.snowcamp.vertx;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

// java
// --add-exports java.base/sun.nio.ch=ALL-UNNAMED
// --add-exports java.base/sun.net.dns=ALL-UNNAMED
// ExampleApp

// Examples of request:
//   http://localhost:8080/all
//   http://localhost:8080/get/foo/3
public class ExampleApp extends AbstractVerticle {
  @Override
  public void start() {
    Router router = Router.router(vertx);
    
    // route to JSON REST APIs 
    router.get("/all").handler(this::getAllDBs);
    router.get("/get/:name/:id").handler(this::getARecord);
    
    // otherwise serve static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    System.out.println("listen on port 8080");
  }
  
  private void getAllDBs(RoutingContext routingContext) {
    routingContext.response()
       .putHeader("content-type", "application/json")
       .end(Arrays.asList("bd1", "bd2").stream().map(Json::encodePrettily).collect(joining(", ", "[", "]")));
  }
  
  private void getARecord(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    HttpServerRequest request = routingContext.request();
    String name = requireNonNull(request.getParam("name"));
    int id = Integer.parseInt(request.getParam("id"));
    if (name.isEmpty() || id < 0) {  
      response.setStatusCode(404).end();
      return;
    } 
    HashMap<String, String> resultMap = new HashMap<>();
    resultMap.put("id", "" + id);
    resultMap.put("name", name);
    routingContext.response()
       .putHeader("content-type", "application/json")
       .end(Json.encodePrettily(resultMap));
  }
  
  public static void main(String[] args) {
    // development option, avoid caching to see changes of
    // static files without having to reload the application,
    // obviously, this line should be commented in production
    //System.setProperty("vertx.disableFileCaching", "true");
    
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ExampleApp());
  }
}
