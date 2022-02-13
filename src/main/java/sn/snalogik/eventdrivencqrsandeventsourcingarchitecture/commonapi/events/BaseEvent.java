package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaseEvent<T> {
    private T id;
}
