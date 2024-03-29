Spring-Data-REST / QueryDsl / Spring-Data-JPA based CRUD Api.

Features:

1) Controller-less - Spring Data Rest implements the controllers for me, based on the Spring-Data repositories it finds
on the classpath. By default, Spring-Data-Rest handles all of the HTTP verbs you would need for a CRUD API.
 PUTs / POSTs / DELETES / GETS

 See https://projects.spring.io/spring-data-rest/ for more information.

2)Querydsl \ Spring-Data integration:

    - Querydsl entities are auto-generated by the com.querydsl.apt.QuerydslAnnotationProcessorbuilt
    maven plugin as part of the build process, for any entity class marked with the @QueryEntity annotation.
    See my contacts-commons module pom.xml (/solstice-contacts-list/contacts-commons/pom.xml). After building, the generated source
    files (QAddress, QContact) can be inspected  here:
    /solstice-contacts-list/contacts-commons/target/generated-sources/annotations/com/mancini/contacts/domain/jpa

    -Inside the contacts-jpa-api module, my repositories interfaces are declared as follows:

        public interface ContactRepository extends PagingAndSortingRepository<Contact,Long>,
                QuerydslPredicateExecutor<Contact>{
        }

        public interface AddressRepository extends PagingAndSortingRepository<Address,Long>,
                QuerydslPredicateExecutor<Address> {
        }

        "Auto-magically", Spring-Data takes care of the implementation for me! This keeps my code base clean and concise,
        and allows me to query my entities in complex ways (with paging and sorting built in), with little effort:
        localhost:8080/contacts?firstName=Diego&addresses.city=Endicott
        localhost:8080/contacts?firstName=Diego&addresses.city=Salto&size=1 (return 1 entry at a time, with pagination links to other matches.)

       Spring-Data-Jpa used in the context of Spring-Boot means my JPA provider (Hibernate) is automatically configured.
       (I don't have to wire up an EntityManager, or even write boilerplate code to establish database connections,
       Spring-Boot takes care of that.

        For more info on Querydsl:
        https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/
        https://www.credera.com/blog/technology-insights/java/can-querydsl-part-1-enhance-simplify-existing-spring-data-jpa-repositories/

        For more info on Spring-Data:
        https://projects.spring.io/spring-data/
        https://projects.spring.io/spring-data-jpa/

  3) JSR-303 Bean Validation is supported. I can mark fields of my entity class with JSR-303 annotations, and configure
     Spring-Data to enforce the validation when a user attempts to save a malformed entity. For example, below are
     some annotated fields of my Contact class:

        @Email @Size(min=4,max=50)
        private String email;
        private java.util.Date birthDate;
        @Size(min=10,max=10)
        private String workPhone;

     Spring will check that when any user attempts to create or modify a Contact, the fields will have the proper format.

     See my configuration:
     /solstice-contacts-list/contacts-jpa-api/src/main/java/com/mancini/contacts/api/jpa/configuration/RestRepositoryConfig.java

     also see http://hibernate.org/validator/

Usage:

mvn test
