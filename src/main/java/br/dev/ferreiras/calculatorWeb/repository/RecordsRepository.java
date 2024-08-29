package br.dev.ferreiras.calculatorWeb.repository;

import br.dev.ferreiras.calculatorWeb.entity.Records;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsRepository extends JpaRepository<Records,Long> {

}
