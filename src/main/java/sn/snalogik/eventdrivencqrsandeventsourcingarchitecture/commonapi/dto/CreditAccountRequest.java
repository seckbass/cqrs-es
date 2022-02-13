package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditAccountRequest {
    private String accountId;
    private double amount;
    private String currency;
}
