package com.mancini.contacts.api.jpa.test;


import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    public void contractsBaseEndpointFindAll() throws Exception {
        mockMvc.perform(get("/contacts")).andExpect(status().isOk())
                                                    .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
                                                    .andExpect(jsonPath("$.page.totalElements").value(12));
    }

    @Test
    public void addressesBaseEndpointFindAll() throws Exception {
        mockMvc.perform(get("/addresses")).andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("$.page.totalElements").value(15));
    }


    @Test //Diego Forlan, Diego Milito, Diego Costa, and the G.O.A.T Diego Maradona.
    public void contractsSimpleQuery() throws Exception {
        mockMvc.perform(get("/contacts?firstName=Diego")).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(4))
                .andExpect(jsonPath("$.page.totalPages").value(1));


    }


    @Test//Since there are 4 Diegos, passing a size=1 (to return one result per page) should produce 4 pages.
    public void paginatedQueryReturnsCorrectPageCount() throws Exception {
        mockMvc.perform(get("/contacts?firstName=Diego&size=1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(4))
                .andExpect(jsonPath("$.page.totalPages").value(4));
    }

    /**Since I'm using QueryDsl, I should be able to query a Contact through one of its address fields,
     * accessed via dot notation, (even through address info lives in a seperate table, and is not nested
     * inside of a Contact entity.
     *
     * ie, /contracts?address.city=Salto
     *
     * (Both Luis Suarez and Edison Cavani are from Salto, Uruguay)
     * @throws Exception
     */
    @Test
    public void complexDslQueriesShouldWorkAcrossEntities() throws Exception {
        mockMvc.perform(get("/contacts?addresses.city=Salto")).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$._embedded.contacts", hasSize(2)))
                .andExpect((jsonPath("$._embedded.contacts[*].lastName", Matchers.containsInAnyOrder("Suarez","Cavani"))));

        }


    /**Since I'm using QueryDsl, I should be able to query a Contact through one of its address fields,
     * accessed via dot notation, but also filter results on other fields present in the Entity. The above
     * test shows there are two players from Salto, adding a filter on firstName=Edison should reduce the result set to 1.
     *
     * ie, /contracts?firstName=Diego&address.city=Salto
     *
     * (Both Luis Suarez and Edison Cavani are from Salto, Uruguay)
     * @throws Exception
     */

    @Test
    public void complexDslQueriesShouldHonorOtherFilters() throws Exception {
        mockMvc.perform(get("/contacts?addresses.city=Salto&firstName=Edison")).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(1))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$._embedded.contacts", hasSize(1)))
                .andExpect((jsonPath("$._embedded.contacts[*].lastName", Matchers.containsInAnyOrder("Cavani"))));

    }








}
