package org.github.examples;

import org.github.examples.config.DatabaseConfiguration;
import org.github.examples.dao.ProductRepository;
import org.github.examples.dao.TestDao;
import org.github.examples.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
public class H2JdbcTests {

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
    public void testReadProduct() {

        // Given

        // When
        Product res = productRepository.findOne(0);

        // Then
        assertThat(res.getId()).isEqualTo(0);
        assertThat(res.getCategoryId()).isEqualTo(0);
        assertThat(res.getName()).isEqualTo("test product");
        assertThat(res.getDescription()).isEqualTo("test product description");
    }

    @Test
    public void testInsertProduct() {

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
