package com.bootcamp.depositservice.services.impl;

import com.bootcamp.depositservice.models.dto.DebitAccountDTO;
import com.bootcamp.depositservice.services.IDebitAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DebitAccountServiceImpl implements IDebitAccountService {

    private static final Logger log = LoggerFactory.getLogger(DebitAccountServiceImpl.class);

    @Autowired
    WebClient.Builder webClientBuilder;

    @Override
    public Mono<DebitAccountDTO> findByAccountNumber(String typeofdebit, String accountNumber) {
        log.info("TYPE ACCOUNT:  "+typeofdebit);
        log.info("ACCOUNT NUMBER:"+accountNumber);
        Mono<DebitAccountDTO> account = Mono.empty();
        if(typeofdebit.equals("SAVING_ACCOUNT")){
             return account = webClientBuilder
                    .build()
                    .get()
                    .uri("localhost:9001/api/savingAccount/account/"+accountNumber)
                    .retrieve()
                    .bodyToMono(DebitAccountDTO.class);
        } else{
            return Mono.empty();
        }
    }

    @Override
    public Mono<DebitAccountDTO> updateDebit(DebitAccountDTO account) {
        if(account.getTypeAccount().equals("SAVING_ACCOUNT")) {
            /*return client.baseUrl("http://SAVINGACCOUNT-SERVICE/api/savingAccount")
                    .build()
                    .put()
                    .uri("/{id}", Collections.singletonMap("id", debitAccountDTO.getId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(debitAccountDTO)
                    .retrieve()
                    .bodyToMono(DebitAccountDTO.class);*/
            log.info("Cuenta Actualizada");
            return webClientBuilder
                    .build()
                    .put()
                    .uri("localhost:9001/api/savingAccount/" + account.getId())
                    .body(Mono.just(account), DebitAccountDTO.class)
                    .retrieve()
                    .bodyToMono(DebitAccountDTO.class);
        }else{
            return Mono.empty();
        }
    }
}
