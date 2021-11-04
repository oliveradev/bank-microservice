package com.bootcamp.transactionservice.config;

import com.bootcamp.transactionservice.handlers.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    /**
     * Routes router function.
     *
     * @param transactionHandler the transaction handler
     * @return the router function
     */
    @Bean
    public RouterFunction<ServerResponse> routes(TransactionHandler transactionHandler){

        return route(GET("/api/transaction"), transactionHandler::findAll)
                .andRoute(GET("/api/transaction/{identityNumber}"), transactionHandler::findTransactionsByIdentityNumber)
                .andRoute(GET("/api/transaction/commission/{identityNumber}"), transactionHandler::findAllCommissions)
                .andRoute(POST("/api/transaction"), transactionHandler::newTransaction)
                .andRoute(GET("/api/transaction/report/{identityNumber}"),transactionHandler::reportLastTen)
                .andRoute(GET("/api/transaction/search/{identityNumber}/{fromDate}/{toDate}")
                        ,transactionHandler::findTransactionsByRangeOfDates);
    }
}
