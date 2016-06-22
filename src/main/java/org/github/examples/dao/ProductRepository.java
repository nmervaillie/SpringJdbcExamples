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

//            new RowMapper<Product>() {
//
//        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//            Product p = new Product();
//            p.setId(rs.getInt("id"));
//            p.setCategoryId(new Category(rs.getInt("category_id")));
//            p.setName(rs.getString("name"));
//            p.setDescription(rs.getString("description"));
//            p.setPrice(rs.getFloat("price"));
//
//
//            <column name="name" type="varchar(100)">
//            <constraints nullable="false" />
//            </column>
//            <column name="description" type="varchar(1000)">
//            <constraints nullable="false" />
//            </column>
//            <column name="price" type="float" />
//            <column name="image_url" type="varchar(200)" />
//            <column name="soldout" type="boolean" />
//            <column name="promotion" type="boolean" />
//
//                    ,
//                    rs.getString("user_name"),
//                    rs.getString("contents"),
//                    rs.getTimestamp("created_time")
//            );
//        }
//    };

}
