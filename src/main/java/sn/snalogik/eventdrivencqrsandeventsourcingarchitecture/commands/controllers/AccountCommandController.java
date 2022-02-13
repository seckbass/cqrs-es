package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commands.controllers;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.commands.CreateAccountCommand;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.commands.CreditAccountCommand;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.commands.DebitAccountCommand;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.dto.CreateAccountRequest;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.dto.CreditAccountRequest;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.dto.DebitAccountRequest;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/account/")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequest request) {
        return commandGateway.send(new CreateAccountCommand(
                "d75f8a74-5c76-4916-ae09-81b7d0449631",
                request.getBalance(),
                request.getCurrency()
        ));
    }

    @PutMapping("credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequest request) {
        return commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
    }

    @PutMapping("debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequest request) {
        return commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }
}
