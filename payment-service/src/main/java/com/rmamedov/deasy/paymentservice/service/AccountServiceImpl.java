package com.rmamedov.deasy.paymentservice.service;

import com.rmamedov.deasy.paymentservice.model.repository.Account;
import com.rmamedov.deasy.paymentservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Mono<Account> findByBankAccountNumber(final String accountNumber) {
        return accountRepository.findByBankAccountNumber(accountNumber).log();
    }

    @Override
    @Transactional
    public Flux<Account> saveAll(final Account... accounts) {
        return accountRepository.saveAll(List.of(accounts));
    }

}
