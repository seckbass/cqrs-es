package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events;

import lombok.Getter;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.enums.AccountStatus;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {

    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountCreatedEvent(String id, double balance, String currency, AccountStatus status) {
        super(id);
        this.balance = balance;
        this.currency = currency;
        this.status = status;
    }
}
