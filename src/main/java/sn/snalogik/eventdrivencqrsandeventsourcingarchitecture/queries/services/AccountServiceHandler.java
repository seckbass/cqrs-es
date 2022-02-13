package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.enums.OperationType;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events.AccountActivatedEvent;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events.AccountCreatedEvent;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events.AccountCreditedEvent;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events.AccountDebitedEvent;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.queries.AllAccountQuery;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.queries.OneAccountQuery;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.entities.Account;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.entities.Operation;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.repositories.AccountRepository;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.repositories.OperationRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor @Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("*************************************");
        log.info("AccountCreatedEvent received");
        accountRepository.save(Account
                .builder()
                .id(event.getId())
                .balance(event.getBalance())
                .status(event.getStatus())
                .build());
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("*************************************");
        log.info("AccountActivatedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("*************************************");
        log.info("AccountDebitedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        operationRepository.save(
                Operation.builder()
                        .account(account)
                        .amount(event.getAmount())
                        .date(event.getDate())
                        .type(OperationType.DEBIT)
                        .build()
        );
        account.setBalance(account.getBalance() - event.getAmount());
    }

    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("*************************************");
        log.info("AccountCreditedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        operationRepository.save(
                Operation.builder()
                        .account(account)
                        .amount(event.getAmount())
                        .date(event.getDate())
                        .type(OperationType.CREDIT)
                        .build()
        );
        account.setBalance(account.getBalance() + event.getAmount());
    }

    @QueryHandler
    public List<Account> on(AllAccountQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(OneAccountQuery query) {
        return accountRepository.findById(query.getId()).get();
    }
}
