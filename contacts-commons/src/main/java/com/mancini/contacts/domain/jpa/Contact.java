package com.mancini.contacts.domain.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.querydsl.core.annotations.QueryEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@QueryEntity
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "contacts")
public class Contact {
   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   @Access(value = AccessType.PROPERTY)
   private long id;
   private String firstName;
   private String lastName;
   private String company;
   private String profileImage;
   private String email;
   private java.util.Date birthDate;
   private String workPhone;
   private String mobilePhone;
   @OneToMany(mappedBy = "contact")
   @JsonManagedReference
   private List<Address> addresses;
}
