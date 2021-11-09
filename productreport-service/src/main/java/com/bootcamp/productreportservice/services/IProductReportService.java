package com.bootcamp.productreportservice.services;

import com.bootcamp.productreportservice.documents.dto.*;
import reactor.core.publisher.Mono;

public interface IProductReportService {

    Mono<CustomerRequest> getCustomer(String customerIdentityNumber);
    Mono<Credit> getCredit(String customerIdentityNumber);
    Mono<CreditCard> getCreditCard(String customerIdentityNumber);
    Mono<SavingAccount> getSavingAccount(String customerIdentityNumber);
    Mono<CurrentAccount> getCurrentAccount(String customerIdentityNumber);
    Mono<FixedTermAccount> getFixedTermAccount(String customerIdentityNumber);

}
