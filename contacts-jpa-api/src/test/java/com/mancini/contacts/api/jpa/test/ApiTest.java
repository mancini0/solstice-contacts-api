package com.mancini.contacts.api.jpa.test;


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiTest {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Before
    public void resetDatabase() {
        //I should execute these in batch to save a few milliseconds each test case.
        jdbcTemplate.execute("DELETE FROM ADDRESSES");
        jdbcTemplate.execute("DELETE FROM CONTACTS");
        jdbcTemplate.execute("insert into contacts(id,first_name,last_name,company,profile_image,email,birth_date, work_phone,mobile_phone)\n" +
                "values\n" +
                "(1,'Mike','Mancini','CME Group',null,'mancini0@gmail.com',date '1987-05-28','3123510940','6076241505'),\n" +
                "(2,'Diego','Costa','Atletico Madrid',null,'diego.costa@gmail.com',date '1988-10-07','3254581366','3126241505'),\n" +
                "(3,'Edison','Cavani','Paris Saint Germain',null,'edison.cavani@gmail.com',date '1988-02-14','3254582333','7296241505'),\n" +
                "(4,'Luis','Suarez','FC Barcelona',null,'luis.suarez@gmail.com',date '1987-01-24','4254582363','7296241509'),\n" +
                "(5,'Neymar','Santos','Paris Saint Germain',null,'neymar.santos@gmail.com',date '1991-02-05','4252582365','7296241512'),\n" +
                "(6,'Kylian','Mbappe','Paris Saint Germain',null,'kylian.mbappe@gmail.com',date '1998-12-20','4452582267','7296241513'),\n" +
                "(7,'Andre','Gignac','Tigres UANL',null,'andre.gignac@gmail.com',date '1998-12-05','4452582268','7296248493'),\n" +
                "(8,'Wilfried','Zaha','Crystal Palace FC',null,'wilfried.zaha@gmail.com',date '1992-11-10','1458953456','6045581367'),\n" +
                "(9,'Alex','Oxlade-Chamberlain','Liverpool FC',null,'alex.oxladechamberlain@gmail.com',date '1993-08-15','1458953456','6045581367'),\n" +
                "(10,'Diego','Milito', 'Inter Milan',null,'diego.milito@gmail.com',date '1979-06-12','4583641985','4583641985'),\n" +
                "(11,'Diego','Forlan', 'Atletico Madrid',null,'diego.forlan@gmail.com',date '1979-05-19','4583641985','4583641985'),\n" +
                "(12,'Diego','Maradona', 'FC Barcelona ',null,'diego.maradona@gmail.com',date '1960-10-30','4583641256','4583641985')");
        jdbcTemplate.execute("insert into addresses(id, apt , city, province , country, street, street_number, contact_id )\n" +
                "values\n" +
                "(77,'11M','Chicago','Illinois','United States','Oak Street', 65,1),\n" +
                "(78,null,'Lagarto', 'Sergipe', 'Brazil','Pine Street',123,2),\n" +
                "(79,null,'Salto','Salto Partido', 'Uruguay','Maple Street',123,3),\n" +
                "(80,'20B','Salto','Salto Partido','Uruguay','Ash Street',123,4),\n" +
                "--Let Neymar have multiple addresses\n" +
                "(81, '20C','Birmingham', 'Alabama', 'United States', 'Albert Einstein Blvd', 123,5),\n" +
                "(82, '21C','Endicott', 'New York', 'United States', 'Richard Feynman Blvd', 123,5),\n" +
                "(83, '22C','Bishkek', 'Chuy', 'Kyrgyzstan' ,'Felix Bloch Blvd', 123,5),\n" +
                "(84, '23C','Sao Paulo', 'Sao Paulo Province', 'Brazil', 'Simon Bolivar Ave', 123,5),\n" +
                "--end multiple address\n" +
                "(85, null,'Bondy', 'lle-de-France','France', 'Marie Curie Blvd', 123,6),\n" +
                "(86, null,'Martigues', 'Alpes-Cote de Azur', 'France', 'Louis de Broglie Blvd', 123,7),\n" +
                "(87, null,'Abidjan','Abidjan Province', 'Ivory Coast', 'Alassane Ouattara Ave', 123,8),\n" +
                "(88, null,'Portsmouth', 'Hampshire', 'United Kingdom', 'Paul Dirac Blvd', 123,9),\n" +
                "(89, null,'Bernal', 'Buenos Aires', 'Argentina', 'Pope Francis Blvd', 123,10),\n" +
                "(90, null, 'Montevideo', 'Montevideo','Uruguay', 'Porfirio Rubirosa Blvd', 123,11),\n" +
                "(91, '182L','Lanus', 'Buenos Aires','Argentina', 'Ernesto Sabato Blvd', 123,12)");
    }

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

    /**
     * Since I configured the following properties,
     * <p>
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.resources.add-mappings=false
     * <p>
     * , and added a @ControllerAdvice, @ResponseBody class,
     * <p>
     * Spring should return a 404 status and a json response body if I access an unmapped endpoint.
     */
    @Test
    public void expect400FromUnmappedPath() throws Exception {
        mockMvc.perform(get("/nonsense")).andDo(print()).andExpect(status().is(404))
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"));
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

    /**
     * Since I'm using QueryDsl, I should be able to query a Contact through one of its address fields,
     * accessed via dot notation, (even through address info lives in a separate table, and is not nested
     * inside of a Contact entity. Contracts to Addresses is a many-to-one relationship.
     * <p>
     * ie, /contracts?address.city=Salto
     * <p>
     * (Both Luis Suarez and Edison Cavani are from Salto, Uruguay)
     *
     * @throws Exception
     */
    @Test
    public void complexDslQueriesShouldWorkAcrossEntitiesFromNonOwningSide() throws Exception {
        mockMvc.perform(get("/contacts?addresses.city=Salto")).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$._embedded.contacts", hasSize(2)))
                .andExpect((jsonPath("$._embedded.contacts[*].lastName", Matchers.containsInAnyOrder("Suarez", "Cavani"))));

    }

    /**
     * Repeat the above test, but this time from the "many" side of the many-to-one relationship (ie. the address side)
     */
    @Test
    public void complexDslQueriesShouldWorkAcrossEntitiesFromOwningSide() throws Exception {
        mockMvc.perform(get("/addresses?contact.firstName=Neymar")).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(4))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$._embedded.addresses", hasSize(4)))
                .andExpect((jsonPath("$._embedded.addresses[*].city",
                        Matchers.containsInAnyOrder("Endicott", "Birmingham", "Bishkek", "Sao Paulo"))));
    }


    /**
     * Since I'm using QueryDsl, I should be able to query a Contact through one of its address fields,
     * accessed via dot notation, but also filter results on other fields present in the entity. The above
     * test shows there are two players from Salto, adding a filter on firstName=Edison should reduce the result set to 1.
     * <p>
     * ie, /contracts?firstName=Diego&address.city=Salto
     * <p>
     * (Both Luis Suarez and Edison Cavani are from Salto, Uruguay)
     *
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


    /**
     * An HTTP DELETE to "/contacts/5" (Neymar, contactId = 5) should cascade the delete to the four address
     * entities associated with him (addressIds 81,82,83,84)
     */

    @Test
    public void deletesShouldCascadeToChildren() throws Exception {
        //First confirm both the contact to be deleted and its associated addresses exist.
        mockMvc.perform(get("/contacts/5")).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Neymar"));

        mockMvc.perform(get("/contacts/5/addresses")).andExpect(status().isOk())
                .andExpect((jsonPath("$._embedded.addresses[*].city",
                        Matchers.containsInAnyOrder("Endicott", "Birmingham", "Bishkek", "Sao Paulo"))));

        mockMvc.perform(delete("/contacts/5")).andExpect(status().isNoContent());


        mockMvc.perform(get("/contacts/5/addresses")).andExpect(status().is(404))
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
                .andExpect((jsonPath("$.httpStatus").value("Not Found")));


    }

    /** POSTing to /contacts should create a new contact. The restful links to the new entities address should
     * not be populated, since the address information has not been posted.
     * @throws Exception
     */
    @Test
    public void entityPostsShouldCreateNewRecord() throws Exception {
        //First confirm both the contact to be deleted and its associated addresses exist.
        String kelly ="{  \"firstName\" : \"Kelly\",  \"lastName\" : \"Jones\", \"mobilePhone\":\"6076247163\" }";
        mockMvc.perform(post("/contacts").contentType(MediaType.APPLICATION_JSON).content(kelly))
        .andExpect(status().isCreated());

        mockMvc.perform(get("/contacts?firstName=Kelly")).andExpect(status().isOk())
                .andExpect((jsonPath("$._embedded.contacts[*].mobilePhone",
                        Matchers.containsInAnyOrder("6076247163"))));
        }

}

