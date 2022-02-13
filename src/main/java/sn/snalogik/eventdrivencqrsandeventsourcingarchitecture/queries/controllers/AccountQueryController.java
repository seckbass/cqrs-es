package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.queries.AllAccountQuery;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.queries.OneAccountQuery;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.entities.Account;

import java.util.List;

@RestController
@RequestMapping("/query/account/")
@AllArgsConstructor @Slf4j
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping("list")
    public List<Account> accountList() {
        return queryGateway.query(new AllAccountQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
    }

    @GetMapping("{id}")
    public Account oneAccount(@PathVariable String id) {
        return queryGateway.query(new OneAccountQuery(id), ResponseTypes.instanceOf(Account.class)).join();
    }
}
