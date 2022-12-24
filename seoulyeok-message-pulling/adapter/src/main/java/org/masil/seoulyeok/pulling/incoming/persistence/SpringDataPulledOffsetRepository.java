package org.masil.seoulyeok.pulling.incoming.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringDataPulledOffsetRepository extends CrudRepository<PulledOffsetJdbcEntity, Long> {

    @Query("SELECT * FROM pulled_offset AS p WHERE p.status IN ('ON_PROGRESS')")
    List<PulledOffsetJdbcEntity> findAllOnProgressedPulledOffset();

    PulledOffsetJdbcEntity findByPipelineId(Long id);

}
