package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.commonapi.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAccountRequest {
    private double balance;
    private String currency;
}
