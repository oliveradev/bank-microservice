package com.bootcamp.productreportservice.services.Impl;

import com.bootcamp.productreportservice.documents.dto.*;
import com.bootcamp.productreportservice.services.IProductReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductReportServiceImpl implements IProductReportService {

    @Autowired
    WebClient.Builder webClientBuilder;

    @Override
    public Mono<CustomerRequest> getCustomer(String customerIdentityNumber) {
        return null;
    }

    @Override
    public Mono<Credit> getCredit(String customerIdentityNumber) {
        return null;
    }

    @Override
    public Mono<Creditcard> getCreditCard(String customerIdentityNumber) {
        return null;
    }

    @Override
    public Mono<SavingAccount> getSavingAccount(String customerIdentityNumber) {
        return null;
    }

    @Override
    public Mono<Current> getCurrentAccount(String customerIdentityNumber) {
        return null;
    }

    @Override
    public Mono<FixedTermAccount> getFixedTermAccount(String customerIdentityNumber) {
        return null;
    }
}
