package com.mancini.contacts.domain.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.querydsl.core.annotations.QueryEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@QueryEntity
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "contacts")
@Validated
public class Contact {
   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   @Access(value = AccessType.PROPERTY)
   private long id;
   @NotBlank @Size(min=1,max=25)
   private String firstName;
   @NotBlank @Size(min=2,max=25)
   private String lastName;
   @Size(max=25)
   private String company;
   @Size(max=250)
   private String profileImage;
   @Email @Size(min=4,max=50)
   private String email;

   private java.util.Date birthDate;
   @Size(min=10,max=10)
   private String workPhone;
   @NotBlank @Size(min=10,max=10)
   private String mobilePhone;
   @OneToMany(mappedBy = "contact" ,cascade = CascadeType.REMOVE, orphanRemoval = true)
   private List<Address> addresses;
}
