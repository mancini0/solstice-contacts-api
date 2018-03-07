package com.mancini.contacts.domain.jpa;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.querydsl.core.annotations.QueryEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@QueryEntity
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="contact_id",nullable = false)
    private Contact contact;
    private String  city;
    private String province;
    private String  country;
    private Integer streetNumber;
    private String apt;
    private String street;

}
