package com.bootcam.currentservice.services.Impl;

import com.bootcam.currentservice.models.dto.Credit;
import com.bootcam.currentservice.models.dto.CreditCard;
import com.bootcam.currentservice.services.ICreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Service
public class CreditServiceImpl implements ICreditService {

    private static final Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

    @Autowired
    private WebClient.Builder webClient;
    @Override
    public Mono<Boolean> validateDebtorCredit(String customerIdentityNumber) {
        return null;
    }

    @Override
    public Flux<Credit> getCredit(String customerIdentityNumber) {
        Map<String, Object> params = new HashMap<>();
        log.info("---Obtiene crédito----");
        params.put("Customer Identity Number", customerIdentityNumber);
        return null;
    }

    @Override
    public Mono<CreditCard> getCreditcard(String customerIdentityNumber) {
        return null;
    }
}
