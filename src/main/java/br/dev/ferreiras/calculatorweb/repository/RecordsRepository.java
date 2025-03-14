package br.dev.ferreiras.calculatorweb.repository;

import br.dev.ferreiras.calculatorweb.entity.Records;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RecordsRepository extends JpaRepository<Records, Long> {
  /**
   * @param username username associated to his records
   * @param paging   initial page and total pages
   * @return List of records bound to a username
   */

  @Query(
      """
                  SELECT r FROM Records r where r.username = :username AND r.deleted = false
          """
  )
  Page<Records> findRecordsByUsername(@Param("username") String username, Pageable paging);

  @Query(
      """
                  SELECT r FROM Records r where r.username = :username AND r.deleted = true
          """
  )
  Page<Records> findSoftDeletedRecordsByUsername(@Param("username") String username, Pageable paging);


}

