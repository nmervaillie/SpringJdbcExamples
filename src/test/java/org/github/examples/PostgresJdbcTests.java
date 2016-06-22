package org.github.examples;

import org.github.examples.dao.TestDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringJdbcExamplesApplication.class)
@ActiveProfiles("postgres")
public class PostgresJdbcTests {

    @Autowired
    private TestDao testDao;

    @Test
    public void insertShouldReturnId() {

        // When
        Long generatedId = testDao.save("toto");

        // Then
        assertThat(generatedId).isNotNull().isPositive();
    }

}
