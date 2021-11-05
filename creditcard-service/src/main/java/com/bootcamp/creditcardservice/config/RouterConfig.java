package com.bootcamp.creditcardservice.config;


import com.bootcamp.creditcardservice.handlers.CreditCardHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;



import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(CreditCardHandler creditCardHandler){
        return route(RequestPredicates.GET("/api/creditcard"), creditCardHandler::findAll)
                .andRoute(RequestPredicates.GET("/api/creditcard/payment/{pan}"), creditCardHandler::findCreditCardByPan)
                .andRoute(RequestPredicates.GET("/api/creditcard/{id}"), creditCardHandler::findCreditCard)
                .andRoute(RequestPredicates.GET("/api/creditcard/customer/{customerIdentityNumber}"), creditCardHandler::findCreditCardByCustomer)
                .andRoute(RequestPredicates.POST("/api/creditcard/{customerIdentityNumber}"), creditCardHandler::newCreditCard)
                .andRoute(RequestPredicates.PUT("/api/creditcard/{id}"), creditCardHandler::updateCreditCard)
                .andRoute(RequestPredicates.DELETE("/api/creditcard/{id}"), creditCardHandler::deleteCreditCard);

    }
}
