package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {
    @TargetAggregateIdentifier
    @Getter
    private T id;

    protected BaseCommand(T id) {
        this.id = id;
    }
}
