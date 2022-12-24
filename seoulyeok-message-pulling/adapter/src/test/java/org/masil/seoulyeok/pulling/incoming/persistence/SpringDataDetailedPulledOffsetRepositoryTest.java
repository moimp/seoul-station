package org.masil.seoulyeok.pulling.incoming.persistence;

import com.masil.commons.clocks.Clocks;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.masil.seoulyeok.pulling.Status;
import org.masil.seoulyeok.pulling.incoming.persistence.config.DataJdbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@Transactional
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@SpringJUnitConfig(classes = {SpringDataJdbcConfig.class, DataJdbcConfig.class})
@AutoConfigureEmbeddedDatabase(type = POSTGRES)
@Sql(scripts = {"classpath:schema.sql"})
class SpringDataDetailedPulledOffsetRepositoryTest {

    @Autowired
    SpringDataPulledOffsetRepository sut;
    private static final LocalDateTime ANY_CURRENT_POSITION = Clocks.now();
    private static final long _1_ANY_PULLED_OFFSET_ID = 1L;
    private static final long _2_ANY_PULLED_OFFSET_ID = 2L;
    private static final long _3_ANY_PULLED_OFFSET_ID = 3L;
    private static final long ANY_PIPELINE_ID = 1L;
    private static final long ANY_DESTINATION_ID = 1L;

    @Test
    void create() {
        PulledOffsetJdbcEntity expected = PulledOffsetJdbcEntity.of(_1_ANY_PULLED_OFFSET_ID, ANY_PIPELINE_ID, ANY_DESTINATION_ID, ANY_CURRENT_POSITION);
        sut.save(expected);

        PulledOffsetJdbcEntity actual = sut.findById(_1_ANY_PULLED_OFFSET_ID).get();

        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Test
    void findAll() {
        PulledOffsetJdbcEntity _1_expected = PulledOffsetJdbcEntity.of(_1_ANY_PULLED_OFFSET_ID, ANY_PIPELINE_ID, ANY_DESTINATION_ID, ANY_CURRENT_POSITION);
        PulledOffsetJdbcEntity _2_expected = PulledOffsetJdbcEntity.of(_2_ANY_PULLED_OFFSET_ID, ANY_PIPELINE_ID, ANY_DESTINATION_ID, ANY_CURRENT_POSITION);
        PulledOffsetJdbcEntity _3_expected = PulledOffsetJdbcEntity.of(_3_ANY_PULLED_OFFSET_ID, ANY_PIPELINE_ID, ANY_DESTINATION_ID, ANY_CURRENT_POSITION);
        sut.save(_1_expected.withStatus(Status.ON_PROGRESS));
        sut.save(_2_expected.withStatus(Status.READY));
        sut.save(_3_expected.withStatus(Status.EXIT));

        List<PulledOffsetJdbcEntity> actual = sut.findAllOnProgressedPulledOffset();

        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getId()).isEqualTo(_1_ANY_PULLED_OFFSET_ID);
    }

    @Test
    void findBypipelineIdTest() {
        PulledOffsetJdbcEntity expected = PulledOffsetJdbcEntity.of(_1_ANY_PULLED_OFFSET_ID, ANY_PIPELINE_ID, ANY_DESTINATION_ID, ANY_CURRENT_POSITION);
        sut.save(expected);

        PulledOffsetJdbcEntity actual = sut.findByPipelineId(ANY_PIPELINE_ID);

        assertThat(actual.getId()).isEqualTo(expected.getId());
    }
}
