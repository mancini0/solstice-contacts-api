package com.mancini.contacts.api.jpa.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void applicationContextLoads() throws Exception {
    }

    @Test
    public void rootEndpointIs200() throws Exception {
            mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void contractsEndpointIs200() throws Exception {
        mockMvc.perform(get("/contacts")).andExpect(status().isOk())
                                                    .andExpect(content().contentType("application/hal+json;charset=UTF-8"));
    }

    @Test
    public void addressesEndpointIs200() throws Exception {
        mockMvc.perform(get("/addresses")).andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"));
    }


    @Test
    public void contractsSimpleQuery() throws Exception {
        mockMvc.perform(get("/contacts")).andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"));
    }







}
