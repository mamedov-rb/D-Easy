package com.rmamedov.deasy.paymentservice.service;

import com.rmamedov.deasy.paymentservice.model.repository.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<Account> findByBankAccountNumber(String accountNumber);


    Flux<Account> saveAll(Account... accounts);

}
