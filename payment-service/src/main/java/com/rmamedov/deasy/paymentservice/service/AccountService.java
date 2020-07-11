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
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Mono<Account> findByBankAccountNumber(final String accountNumber) {
        return accountRepository.findByBankAccountNumber(accountNumber).log();
    }

    @Transactional
    public Flux<Account> saveAll(final Account... accounts) {
        return accountRepository.saveAll(List.of(accounts));
    }

//    @PostConstruct
//    public void init() {
//        final var account1 = new Account();
//        account1.setBankAccountNumber("123");
//        final var holder1 = new Holder();
//        holder1.setFirstName(RandomStringUtils.randomAlphabetic(10));
//        holder1.setLastName(RandomStringUtils.randomAlphabetic(10));
//        holder1.setPhone(RandomStringUtils.randomNumeric(10));
//        account1.setHolder(holder1);
//        account1.setBalance(BigDecimal.valueOf(Long.parseLong(RandomStringUtils.randomNumeric(6))));
//
//        final var account2 = new Account();
//        account2.setBankAccountNumber("456");
//        final var holder2 = new Holder();
//        holder2.setFirstName(RandomStringUtils.randomAlphabetic(10));
//        holder2.setLastName(RandomStringUtils.randomAlphabetic(10));
//        holder2.setPhone(RandomStringUtils.randomNumeric(10));
//        account2.setHolder(holder2);
//        account2.setBalance(BigDecimal.valueOf(Long.parseLong(RandomStringUtils.randomNumeric(6))));
//
//        accountRepository.saveAll(List.of(account1, account2)).log().subscribe();
//    }

}
