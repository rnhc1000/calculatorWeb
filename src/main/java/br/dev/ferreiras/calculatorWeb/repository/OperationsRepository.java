package br.dev.ferreiras.calculatorWeb.repository;

import br.dev.ferreiras.calculatorWeb.entity.Operation;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

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
}
