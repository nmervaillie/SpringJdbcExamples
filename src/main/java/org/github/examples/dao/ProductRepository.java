package org.github.examples.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.github.examples.model.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nurkiewicz.jdbcrepository.JdbcRepository;
import com.nurkiewicz.jdbcrepository.RowUnmapper;

@Repository
public class ProductRepository extends JdbcRepository<Product, Integer> {


    public ProductRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, "product");
    }

    @Override
    protected <S extends Product> S postCreate(S entity, Number generatedId) {
        entity.setId(generatedId.intValue());
        return entity;
    }

    public static final RowMapper<Product> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Product.class);

    public static final RowUnmapper<Product> ROW_UNMAPPER = new RowUnmapper<Product>() {

        public Map<String, Object> mapColumns(Product product) {
            Map<String, Object> row = new LinkedHashMap<String, Object>();
            row.put("id", product.getId());
            row.put("category_id", product.getCategoryId());
            row.put("name", product.getName());
            row.put("description", product.getDescription());
            row.put("price", product.getPrice());
            row.put("image_url", product.getImageUrl());
            row.put("soldout", product.isSoldout());
            row.put("promotion", product.isPromotion());
            return row;
        }
    };

}
