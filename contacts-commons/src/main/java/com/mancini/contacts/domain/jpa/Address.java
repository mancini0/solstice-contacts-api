package com.mancini.contacts.domain.jpa;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.querydsl.core.annotations.QueryEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@QueryEntity
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "addresses")
@Validated
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="contact_id",nullable = false)
    private Contact contact;
    @NotBlank @Size(max=25)
    private String  city;
    @Size(max=25)
    private String province;
    @NotBlank @Size(max=25)
    private String  country;
    private Integer streetNumber;
    @Size(max=10)
    private String apt;
    @Size(max=50)
    private String street;

}
