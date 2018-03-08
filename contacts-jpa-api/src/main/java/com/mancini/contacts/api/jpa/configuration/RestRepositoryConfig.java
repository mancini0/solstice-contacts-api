package com.mancini.contacts.api.jpa.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**I would love it if Spring Boot allowed me to apply JSR-303 bean validation annotations
 * on my Entity classes, and validated against those annotations in the web tier, BEFORE
 * attempting to persist to the database. Olivier Gierke explains why Spring-Data does not
 * do so as the default behavior in his comment dated 20160816 here:
 * https://github.com/spring-projects/spring-boot/issues/6574
 *
 * Spring's default behavior means that "invalid" POST / PUT requests are returned to the user as
 * HTTP 500s, instead of HTTP 400s. To me, an HTTP 400 seems more appropriate, so I add this configuration
 * class.
 *
 *
 */


@Configuration
public class RestRepositoryConfig extends RepositoryRestConfigurerAdapter {


    @Bean
    @Primary
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }


    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        Validator validator = validator();
        //bean validation always before save and create
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }


}