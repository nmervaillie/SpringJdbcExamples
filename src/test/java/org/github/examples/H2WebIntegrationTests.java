package org.github.examples;

import javax.annotation.PostConstruct;

import org.github.examples.config.DatabaseConfiguration;
import org.github.examples.config.WebConfiguration;
import org.github.examples.dao.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
public class H2WebIntegrationTests {

    @Autowired
    private ProductRepository productRepository;

    private MockMvc restMvc;

    @Autowired
    private WebApplicationContext wac;

    @PostConstruct
    public void setup() throws Exception {

        restMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testReadProduct() throws Exception {

        // Given

        // When
        restMvc.perform(get("/products/0")
                .contentType(MediaType.APPLICATION_JSON_UTF8))

        // THEN
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("test product"))
        .andExpect(jsonPath("$.description").value("test product description"));

    }


}
