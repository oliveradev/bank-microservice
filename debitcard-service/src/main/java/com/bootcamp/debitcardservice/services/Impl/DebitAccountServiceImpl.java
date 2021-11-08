package com.bootcamp.debitcardservice.services.Impl;

import com.bootcamp.debitcardservice.documents.dto.AccountRequest;
import com.bootcamp.debitcardservice.documents.dto.Pasive;
import com.bootcamp.debitcardservice.repositories.DebitCardRepository;
import com.bootcamp.debitcardservice.services.IDebitAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class DebitAccountServiceImpl implements IDebitAccountService {

    private static final Logger log = LoggerFactory.getLogger(DebitAccountServiceImpl.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private DebitCardRepository repository;

    @Override
    public Mono<AccountRequest> findByAccountNumber(String typeofdebit, String accountNumber) {
        if(typeofdebit.equals("SAVING_ACCOUNT")){
            return webClientBuilder
                    .baseUrl("http://localhost:9002/api/savingAccount")
                    .build()
                    .get()
                    .uri("account/{accountNumber}", accountNumber)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(AccountRequest.class))
                    .doOnNext(c -> log.info("Account Response: Account Amount", c.getAmount()));
        }else if(typeofdebit.equals("CURRENT_ACCOUNT")){
            return webClientBuilder
                    .baseUrl("http://localhost:9004/api/currentAccount")
                    .build()
                    .get()
                    .uri("account/{accountNumber}", accountNumber)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(AccountRequest.class))
                    .doOnNext(c -> log.info("Account Response: Account Amount", c.getAmount()));
        }else if(typeofdebit.equals("FIXEDTERM_ACCOUNT")){
            return webClientBuilder
                    .baseUrl("http://localhost:9005/api/fixedtermAccount")
                    .build()
                    .get()
                    .uri("account/{accountNumber}", accountNumber)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(AccountRequest.class))
                    .doOnNext(c -> log.info("Account Response: Account Amount", c.getAmount()));
        }else {
            log.info("Error encontrando el número de cuenta");
            return Mono.empty();
        }
    }

    @Override
    public Mono<Pasive> getSavingAccount(String accountNumber) {
        return webClientBuilder.baseUrl("http://localhost:9002/api/savingAccount")
                .build()
                .get()
                .uri("/account/{accountNumber}", accountNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Pasive.class))
                .doOnNext(c -> log.info("SavingAccount Response: savingaccount={}", c.getId()));
    }

    @Override
    public Mono<Pasive> getCurrentAccount(String accountNumber) {
        return webClientBuilder.baseUrl("http://localhost:9004/api/currentAccount")
                .build()
                .get()
                .uri("/account/{accountNumber}", accountNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Pasive.class))
                .doOnNext(c -> log.info("CurrentAccount Response: CurrentAccount={}", c.getAccountNumber()));
    }

    @Override
    public Mono<Pasive> getFixedTermAccount(String accountNumber) {
        return webClientBuilder.baseUrl("http://localhost:9005/api/fixedTermAccount")
                .build()
                .get()
                .uri("/account/{accountNumber}", accountNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Pasive.class))
                .doOnNext(c -> log.info("FixedTermAccount Response: FixedTermAccount={}", c.getAccountNumber()));
    }

    @Override
    public Mono<AccountRequest> getAccountAmount(String typeofdebit, String accountNumber) {
        if(typeofdebit.equals("SAVING_ACCOUNT")){
            return getSavingAccount(accountNumber).map(saving -> AccountRequest
                    .builder().id(saving.getId()).accountNumber(saving.getAccountNumber())
                    .amount(saving.getAmount()).typeOfAccount(saving.getTypeOfAccount()).build());
        } else if(typeofdebit.equals("CURRENT_ACCOUNT")){
            return getCurrentAccount(accountNumber).map(current -> AccountRequest
                    .builder().id(current.getId()).accountNumber(current.getAccountNumber())
                    .amount(current.getAmount()).typeOfAccount(current.getTypeOfAccount()).build());
        } else if(typeofdebit.equals("FIXEDTERM_ACCOUNT")){
            return getFixedTermAccount(accountNumber).map(fixedTerm -> AccountRequest
                    .builder().id(fixedTerm.getId()).accountNumber(fixedTerm.getAccountNumber())
                    .amount(fixedTerm.getAmount()).typeOfAccount(fixedTerm.getTypeOfAccount()).build());
        } else return Mono.empty();
    }

    @Override
    public Mono<Pasive> searchEspecificAccount(String pan, double amount, String passwd) {
        return repository.findByPan(pan).flatMap(debitcard -> {
                    if( debitcard.getPassword().equals(passwd)) {
                        Mono<List<Pasive>> accounts = Flux.fromIterable(debitcard.getAccounts())
                                .flatMapSequential(account ->
                                        getAccountAmount(account.getTypeOfAccount(), account.getNumberOfAccount())
                                                .map(accountValue -> Pasive.builder()
                                                        .id(accountValue.getId())
                                                        .typeOfAccount(accountValue.getTypeOfAccount())
                                                        .accountNumber(accountValue.getAccountNumber())
                                                        .amount(accountValue.getAmount()).build())

                                ).collectList();
                        return accounts;
                    } else return Mono.error(new Throwable("The password is incorrect"));
                }
        ).flatMap(accounts -> {

            List<Pasive> lista= accounts.stream()
                    .filter(account -> account.getAmount()>amount).collect(Collectors.toList());

//            if(lista.isEmpty()){
////                return Mono.empty();
////            } else return Mono.just(lista.get(0))
////                    .doOnNext(account -> LOGGER.info("la encontrada es : "
////                            +account.getId()));

            return Mono.just(lista.stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Do not have an account with enough amount")));
        });
    }
}
