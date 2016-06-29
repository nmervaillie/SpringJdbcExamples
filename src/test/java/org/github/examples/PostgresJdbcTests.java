package org.github.examples;

import org.github.examples.config.DatabaseConfiguration;
import org.github.examples.dao.ProductRepository;
import org.github.examples.dao.TestDao;
import org.github.examples.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
@WebAppConfiguration
@ActiveProfiles("postgres")
public class PostgresJdbcTests {

    @Autowired
    private TestDao testDao;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insertShouldReturnId() {

        // When
        Long generatedId = testDao.save("toto");

        // Then
        assertThat(generatedId).isNotNull().isPositive();
    }

    @Test
    public void insertSpringJdbc() {

        // Given
        Product p = new Product();
        p.setCategoryId(0);
        p.setName("test product");
        p.setDescription("a description");
        p.setImageUrl("http://aaa.bbb.ccc");
        p.setSoldout(false);
        p.setPromotion(false);

        long countBefore = productRepository.count();

        // When
        Product res = productRepository.save(p);

        // Then
        assertThat(res.getId()).isNotNull();
        assertThat(productRepository.count()).isEqualTo(countBefore + 1);
    }

}
