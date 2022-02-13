package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateStreamCreationException;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.commands.CreateAccountCommand;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.commands.CreditAccountCommand;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.commands.DebitAccountCommand;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.enums.AccountStatus;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events.AccountActivatedEvent;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events.AccountCreatedEvent;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events.AccountCreditedEvent;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events.AccountDebitedEvent;

import java.util.Date;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus accountStatus;

    public AccountAggregate() {
        // REQUIRED BY AXOM
    }

    // a decision function
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getBalance() < 0) {
            throw new RuntimeException("Not possible...");
        }

        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED));
    }

    // an evolution function
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getBalance();
        this.currency = event.getCurrency();
        this.accountStatus = event.getStatus();

        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    // an evolution function
    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        //this.accountId = event.getId();
        this.accountStatus = event.getStatus();
    }

    // a decision function
    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand) {
        if(creditAccountCommand.getAmount() < 0) {
            throw new RuntimeException("Amount is negative");
        }

        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency(),
                new Date()));
    }

    // an evolution function
    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        //this.accountId = event.getId();
        this.balance += event.getAmount();
    }

    // a decision function
    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand) {
        if(debitAccountCommand.getAmount() < 0) {
            throw new RuntimeException("Amount is negative");
        }

        if(this.balance < debitAccountCommand.getAmount()) {
            throw new RuntimeException("Balance not sufficient : " + balance);
        }

        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency(),
                new Date()));
    }

    // an evolution function
    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        //this.accountId = event.getId();
        this.balance -= event.getAmount();
    }



    @ExceptionHandler(AggregateStreamCreationException.class)
    public ResponseEntity<String> exceptionHandler(AggregateStreamCreationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
