package com.example.demo.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EmailRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	from("direct:sendEmail")
        .toD("smtp://{{spring.mail.host}}:{{spring.mail.port}}"
            + "?username={{spring.mail.username}}"
            + "&password={{spring.mail.password}}"
            + "&from={{app.mail.from}}"
            + "&subject=Correo%20de%20prueba");

    }
}
