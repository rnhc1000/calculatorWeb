package br.dev.ferreiras.calculatorweb.repository;

import br.dev.ferreiras.calculatorweb.entity.Records;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsRepository extends JpaRepository<Records, Long> {
  /**
   * @param username username associated to his records
   * @param paging   initial page and total pages
   * @return List of records bound to a username
   */

  @Query (
          """
                  SELECT r FROM Records r where r.username = ?1% AND r.deleted = false
          """
  )
  Page<Records> findRecordsByUsername(String username, Pageable paging);



}

