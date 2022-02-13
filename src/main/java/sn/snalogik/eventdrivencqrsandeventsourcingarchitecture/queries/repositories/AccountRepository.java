package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.entities.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
}
