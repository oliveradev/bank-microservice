package com.bootcamp.debitcardservice.documents.controller;

import com.bootcamp.debitcardservice.documents.dto.AccountCommand;
import com.bootcamp.debitcardservice.documents.dto.AccountRequest;
import com.bootcamp.debitcardservice.documents.dto.CustomerCommand;
import com.bootcamp.debitcardservice.documents.dto.Pasive;
import com.bootcamp.debitcardservice.documents.entities.DebitCard;
import com.bootcamp.debitcardservice.services.ICustomerRequestService;
import com.bootcamp.debitcardservice.services.IDebitAccountService;
import com.bootcamp.debitcardservice.services.IDebitCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/debitcard")
public class DebitCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebitCardController.class);
    @Autowired
    private IDebitCardService service;
    @Autowired
    private IDebitAccountService accountService;

    @Autowired
    private ICustomerRequestService customerService;

    @GetMapping()
    public Flux<DebitCard> getAllDebitCard() {

        Flux<DebitCard> debitCards = service.findAll();
        return debitCards;
    }

    @PostMapping("/{customerIdentityNumber}")
    public Mono<ResponseEntity<DebitCard>> saveDebitCard(@PathVariable String customerIdentityNumber , @Valid @RequestBody Mono<DebitCard> request) {


        return request.flatMap(debitCard -> customerService.getCustomer(customerIdentityNumber)
                        .flatMap(client -> {
                            debitCard.setCustomer(CustomerCommand.builder()
                                    .customerIdentityNumber(customerIdentityNumber)
                                    .name(client.getName())
                                    .code(client.getCustomerType().getCode()).build());

                            return accountService.findByAccountNumber(debitCard.getAccounts().get(0).getTypeOfAccount()
                                    , debitCard.getAccounts().get(0).getNumberOfAccount());
                        })
                        .flatMap(account -> {
                            LOGGER.info("customerIdentityNumber of Account : " +account.getCustomerIdentityNumber());
                            LOGGER.info("el cliente es : " +customerIdentityNumber);
                            if(account.getAccountNumber() !=null
                                    && account.getCustomerIdentityNumber().equals(customerIdentityNumber)) {
                                return service.create(debitCard);
                            } else return Mono.empty();
                        })
                ).map(customerCreate -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customerCreate))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST)));
    }

    @PostMapping("/add/{customerIdentityNumber}")
    public Mono<ResponseEntity<DebitCard>> addDebitCard(@PathVariable String customerIdentityNumber ,@RequestBody Mono<AccountCommand> request) {

        return request.flatMap(account -> service.findDebitCardByCustomer_CustomerIdentityNumber(customerIdentityNumber)
                        .flatMap(debitcard -> {
                            debitcard.getAccounts().add(account);
                            return accountService.findByAccountNumber(account.getTypeOfAccount(), account.getNumberOfAccount())
                                    .flatMap(accountFound -> {
                                        if(accountFound.getCustomerIdentityNumber().equals(customerIdentityNumber)){
                                            return service.update(debitcard);
                                        } else return Mono.empty();
                                    });

                        })).map(debitUpdate -> ResponseEntity.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(debitUpdate))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST)));
    }
    @GetMapping("/debitUses/{pan}/{amount}/{password}")
    public Mono<Pasive> debitPaymentOrRetire(@PathVariable String pan, @PathVariable double amount, @PathVariable String password) {

        return accountService.searchEspecificAccount(pan
                        , amount, password)
                .doOnNext(account -> LOGGER.info("la cuenta es : " +account.getId()));
    }



    @GetMapping("/getBalance/{pan}")
    public Mono<ResponseEntity<AccountRequest>> getBalance(@PathVariable(name="pan",required= true) String pan ) {
        return	  service.findByPan(pan).flatMap(debitcard -> {
                    AccountCommand account = debitcard.getAccounts()
                            .get(0);
                    return accountService.findByAccountNumber(account.getTypeOfAccount(), account.getNumberOfAccount());
                }).map(account -> ResponseEntity.ok()
                        .body(account))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }


    @DeleteMapping("/{pan}")
    public Mono<ResponseEntity<Void>> deleteByID(@PathVariable(name="pan",required= true) String pan) {

        return service.findByPan(pan).flatMap(debitcard -> service.delete(debitcard))
                .map(c -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("findByPan/{pan}")
    public Mono<DebitCard> findByID(@PathVariable(name="pan",required= true) String pan) {

        return	service.findByPan(pan);

    }
}
