drop table if exists addresses;
drop table if exists contacts;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 25 increment 1;
create table addresses (id int8 not null, apt varchar(10), city varchar(25) not null , province varchar(25), country varchar(25) not null,street varchar(50), street_number int4, contact_id int8 not null, primary key (id));
create table contacts (id int8 not null, birth_date timestamp, company varchar(25), email varchar(50),first_name varchar(25) not null , last_name varchar(25) not null, mobile_phone varchar(10) not null, profile_image varchar(250), work_phone varchar(10), primary key (id));
alter table if exists addresses add constraint FKm9mmllcmwk2caolb4kspwgmdl foreign key (contact_id) references contacts;