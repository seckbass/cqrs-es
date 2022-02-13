package sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.snalogik.eventdrivencqrsandeventsourcingarchitecture.queries.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
