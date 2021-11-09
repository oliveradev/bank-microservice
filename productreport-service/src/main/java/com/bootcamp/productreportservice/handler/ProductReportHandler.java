package com.bootcamp.productreportservice.handler;


import com.bootcamp.productreportservice.documents.dto.CustomerCommand;
import com.bootcamp.productreportservice.documents.entities.ProductReport;
import com.bootcamp.productreportservice.services.IProductReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class ProductReportHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductReportHandler.class);

    @Autowired
    private IProductReportService service;

    public Mono<ServerResponse> generateReportCustomer(ServerRequest request) {
        ProductReport reportProduct = new ProductReport();
        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");
        LOGGER.info("El customerIdentityNumber es " + customerIdentityNumber);
        return Mono.just(reportProduct).flatMap(report -> service.getCustomer(customerIdentityNumber)
                        .flatMap(customer -> {
                            reportProduct.setCustomer(CustomerCommand.builder()
                                    .name(customer.getName()).code(customer.getCustomerType().getCode())
                                    .customerIdentityNumber(customer.getCustomerIdentityNumber()).build());
                            return service.getSavingAccount(customerIdentityNumber);
                        }).flatMap(savingAccount -> {
                            if(savingAccount.getAccountNumber()!= null) {
                                reportProduct.getProductos().add(savingAccount);
                            }
                            return service.getFixedTermAccount(customerIdentityNumber);
                        }).flatMap(fixedtermaccount -> {
                            if(fixedtermaccount.getAccountNumber()!= null) {
                                reportProduct.getProductos().add(fixedtermaccount);
                            }
                            return service.getCurrentAccount(customerIdentityNumber);
                        }).flatMap(currentaccount -> {
                            if(currentaccount.getAccountNumber()!= null) {
                                reportProduct.getProductos().add(currentaccount);
                            }
                            return service.getCreditCard(customerIdentityNumber);
                        }).flatMap(creditcard -> {
                            if(creditcard.getPan()!= null) {
                                creditcard.setTypeOfAccount("CREDITCARD");
                                reportProduct.getProductos().add(creditcard);
                            }
                            return service.getCredit(customerIdentityNumber));
                        })
                        .flatMap(credit -> {
                            if(credit.getContractNumber() != null) {
                                    credit.setTypeOfAccount("CREDIT");
                                reportProduct.getProductos().add(credit);
                            }

                            return Mono.just(reportProduct);
                        }))
                .flatMap(c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
