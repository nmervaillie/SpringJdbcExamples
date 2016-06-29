package org.github.examples;

import javax.annotation.PostConstruct;

import org.github.examples.config.DatabaseConfiguration;
import org.github.examples.config.WebConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
public class WadlTests {

    private MockMvc restMvc;

    @Autowired
    private WebApplicationContext wac;

    @PostConstruct
    public void setup() throws Exception {

        restMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testReadProduct() throws Exception {

        // When
        restMvc.perform(get("/application.wadl"))

        // THEN
        .andDo(print())
        .andExpect(status().isOk());
    }
}
