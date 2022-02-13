package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.commands;

import lombok.Getter;

@Getter
public class CreateAccountCommand extends BaseCommand<String> {
    private double balance;
    private String currency;

    public CreateAccountCommand(String id, double balance, String currency) {
        super(id);
        this.balance = balance;
        this.currency = currency;
    }
}
