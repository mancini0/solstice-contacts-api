insert into contacts(id,first_name,last_name,company,profile_image,email,birth_date, work_phone,mobile_phone)
values
(1,'Mike','Mancini','CME Group',null,'mancini0@gmail.com',date '1987-05-28','3123510940','6076241505'),
(2,'Diego','Costa','Atletico Madrid',null,'diego.costa@gmail.com',date '1988-10-07','3254581366','3126241505'),
(3,'Edison','Cavani','Paris Saint Germain',null,'edison.cavani@gmail.com',date '1988-02-14','3254582333','7296241505'),
(4,'Luis','Suarez','FC Barcelona',null,'luis.suarez@gmail.com',date '1987-01-24','4254582363','7296241509'),
(5,'Neymar','Santos','Paris Saint Germain',null,'neymar.santos@gmail.com',date '1991-02-05','4252582365','7296241512'),
(6,'Kylian','Mbappe','Paris Saint Germain',null,'kylian.mbappe@gmail.com',date '1998-12-20','4452582267','7296241513'),
(7,'Andre','Gignac','Tigres UANL',null,'andre.gignac@gmail.com',date '1998-12-05','4452582268','7296248493'),
(8,'Wilfried','Zaha','Crystal Palace FC',null,'wilfried.zaha@gmail.com',date '1992-11-10','1458953456','6045581367'),
(9,'Alex','Oxlade-Chamberlain','Liverpool FC',null,'alex.oxladechamberlain@gmail.com',date '1993-08-15','1458953456','6045581367'),
(10,'Diego','Milito', 'Inter Milan',null,'diego.milito@gmail.com',date '1979-06-12','4583641985','4583641985'),
(11,'Diego','Forlan', 'Atletico Madrid',null,'diego.forlan@gmail.com',date '1979-05-19','4583641985','4583641985'),
(12,'Diego','Maradona', 'FC Barcelona ',null,'diego.maradona@gmail.com',date '1960-10-30','4583641256','4583641985');


insert into addresses(id, apt , city, province , country, street, street_number, contact_id )
values
(77,'11M','Chicago','Illinois','United States','Oak Street', 65,1),
(78,null,'Lagarto', 'Sergipe', 'Brazil','Pine Street',123,2),
(79,null,'Salto','Salto Partido', 'Uruguay','Maple Street',123,3),
(80,'20B','Salto','Salto Partido','Uruguay','Ash Street',123,4),
--Let Neymar have multiple addresses
(81, '20C','Birmingham', 'Alabama', 'United States', 'Albert Einstein Blvd', 123,5),
(82, '21C','Endicott', 'New York', 'United States', 'Richard Feynman Blvd', 123,5),
(83, '22C','Bishkek', 'Chuy', 'Kyrgyzstan' ,'Felix Bloch Blvd', 123,5),
(84, '23C','Sao Paulo', 'Sao Paulo Province', 'Brazil', 'Simon Bolivar Ave', 123,5),
--end multiple address
(85, null,'Bondy', 'lle-de-France','France', 'Marie Curie Blvd', 123,6),
(86, null,'Martigues', 'Alpes-Cote de Azur', 'France', 'Louis de Broglie Blvd', 123,7),
(87, null,'Abidjan','Abidjan Province', 'Ivory Coast', 'Alassane Ouattara Ave', 123,8),
(88, null,'Portsmouth', 'Hampshire', 'United Kingdom', 'Paul Dirac Blvd', 123,9),
(89, null,'Bernal', 'Buenos Aires', 'Argentina', 'Pope Francis Blvd', 123,10),
(90, null, 'Montevideo', 'Montevideo','Uruguay', 'Porfirio Rubirosa Blvd', 123,11),
(91, '182L','Lanus', 'Buenos Aires','Argentina', 'Ernesto Sabato Blvd', 123,12);

