package com.rmamedov.deasy.paymentservice.repository;

import com.rmamedov.deasy.paymentservice.model.repository.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, String> {

    Mono<Account> findByBankAccountNumber(final String accountNumber);

}
