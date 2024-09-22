package br.dev.ferreiras.calculatorWeb.repository;

import br.dev.ferreiras.calculatorWeb.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface OperationsRepository extends JpaRepository<Operation, Long> {

  @Query ("""
          SELECT cost c FROM Operation o WHERE operationId = :operationId
          """)
  BigDecimal findOperationsCostById(Long operationId);

  @Query ("""
          SELECT cost c FROM Operation o WHERE operation = :operation
          """)
  BigDecimal findOperationsCostByOperation(String operation);

  @Query ("""
          SELECT o.operation,  o.cost c FROM Operation o
          """)
  List<Object> findAllCosts();

}
