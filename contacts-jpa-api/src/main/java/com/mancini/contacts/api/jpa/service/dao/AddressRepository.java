package com.mancini.contacts.api.jpa.service.dao;

import com.mancini.contacts.domain.jpa.Address;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends PagingAndSortingRepository<Address,Long>,
        QuerydslPredicateExecutor<Address> {
}