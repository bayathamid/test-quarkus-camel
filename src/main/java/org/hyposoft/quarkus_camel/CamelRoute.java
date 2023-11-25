package org.hyposoft.quarkus_camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class CamelRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        from("timer://t1?period=1000")        
        .setBody().simple("{\"id\":1,\"time\":\"${date-with-timezone:now:America/Los_Angeles:YYYY/MM/dd hh:mm:ss z}\"}")
        .unmarshal().json(JsonLibrary.Jackson,Package.class)
        .process(exchange -> {
            Package package1 = exchange.getIn().getBody(Package.class);            
            exchange.getMessage().setBody(package1.id);
        })
        .log("${body}");
    }
    
}
