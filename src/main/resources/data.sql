insert into owner (name, cpf,email,phone) values ('Joao da Silva','243.242.490-59','joao.silva@test.com','(21)9900-00001');
insert into owner (name, cpf,email,phone) values ('Maria Jose','399.864.640-05','maria.jose@test.com','(21)9900-00002');
insert into owner (name, cpf,email,phone) values ('Pedro Carvalho','839.093.660-74','pedro.carvalho@test.com','(21)9900-00003');

insert into animal (name,owner_id,age,gender) values ('Bruce',(select id from owner where email = 'joao.silva@test.com'),1,'MALE');
insert into animal (name,owner_id,age,gender) values ('Bela',(select id from owner where email = 'joao.silva@test.com'),8,'FEMALE');
insert into animal (name,owner_id,age,gender) values ('Toto',(select id from owner where email = 'maria.jose@test.com'),5,'MALE');