package br.dev.ferreiras.calculatorWeb.repository;

import br.dev.ferreiras.calculatorWeb.entity.Records;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordsRepository extends JpaRepository<Records, Long> {
  /**
   * @param username username associated to his records
   * @param paging initial page and total pages
   * @return List of records bound to a username
   */
  @Query ("""
          SELECT r from Records r WHERE r.username = :username
          """
  )
  Page<Records> findRecordsByUsername(String username, Pageable paging);

}

