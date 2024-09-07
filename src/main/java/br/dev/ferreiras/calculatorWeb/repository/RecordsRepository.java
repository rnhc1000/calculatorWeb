package br.dev.ferreiras.calculatorWeb.repository;

import br.dev.ferreiras.calculatorWeb.entity.Records;
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
//  @Query (value =
//          """
//                  SELECT * FROM calculator.tb_records WHERE username = ?1
//          """
//          , nativeQuery = true
//  )
  @Query (
          """
                  SELECT r FROM Records r where r.username = ?1%
                  """
  )
  Page<Records> findRecordsByUsername(String username, Pageable paging);

}

