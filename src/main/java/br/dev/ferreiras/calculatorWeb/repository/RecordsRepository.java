package br.dev.ferreiras.calculatorWeb.repository;

import br.dev.ferreiras.calculatorWeb.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsRepository extends JpaRepository<Records,Long> {
}
