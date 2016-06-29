package org.github.examples.web;

import org.github.examples.dao.ProductRepository;
import org.github.examples.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/products/{productId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Product find(@PathVariable("productId") Integer productId) {
        return productRepository.findOne(productId);
    }


}
