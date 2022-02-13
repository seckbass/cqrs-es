package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events;

import lombok.Getter;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.enums.AccountStatus;

@Getter
public class AccountActivatedEvent extends BaseEvent<String> {

    private AccountStatus status;

    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
