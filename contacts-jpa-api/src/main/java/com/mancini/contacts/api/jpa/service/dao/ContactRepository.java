package com.mancini.contacts.api.jpa.service.dao;

import com.mancini.contacts.domain.jpa.Contact;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactRepository extends PagingAndSortingRepository<Contact,Long>,
        QuerydslPredicateExecutor<Contact>{
}
