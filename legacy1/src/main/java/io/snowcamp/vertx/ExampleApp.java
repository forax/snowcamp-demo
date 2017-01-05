package io.snowcamp.vertx;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
    Configuration configuration = loadConfiguration();

    Router router = Router.router(vertx);
    
    // route to JSON REST APIs 
    router.get("/all").handler(this::getAllDBs);
    router.get("/get/:name/:id").handler(this::getARecord);
    
    // otherwise serve static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router::accept).listen(configuration.getPort());
    System.out.println("listen on port " + configuration.getPort());
  }

  private Configuration loadConfiguration() {
    Configuration configuration;
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      InputStream inputStream = this.getClass().getResourceAsStream("configuration.xml");
      configuration = (Configuration) unmarshaller.unmarshal(inputStream);
    } catch (JAXBException e) {
      System.out.println("Arggh, cannot read configuration file");
      configuration = new Configuration();
      configuration.setPort(8080);
    }
    return configuration;
  }

  private void getAllDBs(RoutingContext routingContext) {
    routingContext.response()
       .putHeader("content-type", "application/json")
       .end(Stream.of("bd1", "bd2").map(Json::encodePrettily).collect(joining(", ", "[", "]")));
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
