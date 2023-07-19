insert into owner (name, cpf,email,phone) values ('Joao da Silva','243.242.490-59','joao.silva@test.com','(21)9900-00001');
insert into owner (name, cpf,email,phone) values ('Maria Jose','399.864.640-05','maria.jose@test.com','(21)9900-00002');
insert into owner (name, cpf,email,phone) values ('Pedro Carvalho','839.093.660-74','pedro.carvalho@test.com','(21)9900-00003');


insert into breed (name) values ('Campolina');
insert into breed (name) values ('Mangalarga');
insert into breed (name) values ('Mangalarga Marchador');
insert into breed (name) values ('Pampa');
insert into breed (name) values ('Pantaneiro');

insert into animal (name,age,gender,owner_id,breed_id) values ('Bruce',1,'MALE',(select id from owner where email = 'joao.silva@test.com'),(select id from breed where name = 'Campolina'));
insert into animal (name,age,gender,owner_id,breed_id) values ('Bela',8,'FEMALE',(select id from owner where email = 'joao.silva@test.com'),(select id from breed where name = 'Mangalarga Marchador'));
insert into animal (name,age,gender,owner_id,breed_id) values ('Toto',5,'MALE',(select id from owner where email = 'maria.jose@test.com'),(select id from breed where name = 'Pampa'));