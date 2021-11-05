package com.bootcam.currentservice.services.Impl;

import com.bootcam.currentservice.models.entities.Account;
import com.bootcam.currentservice.repositories.AccountRepository;
import com.bootcam.currentservice.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AccountServiceImpl implements IAccountService {

    @Autowired
    AccountRepository repository;

    @Override
    public Flux<Account> findAllByCustomerIdentityNumber(String customerIdentityNumber) {
        return null;
    }

    @Override
    public Mono<Account> findByCustomerIdentityNumber(String customerIdentityNumber) {
        return repository.findByCustomerIdentityNumber(customerIdentityNumber);
    }

    @Override
    public Mono<Account> validateCustomerIdentityNumber(String customerIdentityNumber) {
        return null;
    }


    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return null;
    }

    @Override
    public Mono<Account> create(Account o) {
        return repository.save(o);
    }

    @Override
    public Mono<Void> delete(Account o) {
        return repository.delete(o);
    }

    @Override
    public Flux<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Account> findById(String o) {
        return repository.findById(o);
    }
}
