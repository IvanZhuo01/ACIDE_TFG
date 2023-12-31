% Case distinction
% Single-line statements
% natural join -> natural inner join

/development on
/abolish

%The Dog and Cat Club annual dinner is going to take place in a few weeks,
%and the organizing committee is preparing the dinner guest list. Each year they browse the database of the All Pets Club looking for people that own at least one cat and one dog. The owners come to the dinner with all their cats and dogs. However, this year two additional constraints have been introduced:
%- People owning more than 5 animals are not allowed (the dinner would become too noisy).
%- No animals sharing the same name are allowed at the party. This means that if two people have a cat or dog sharing the same name neither of them will be invited. This severe restriction follows after last year incident, when someone cried 'Tiger' and dozens of pets started running without control.

%--- All Pets Club database
create table Owner(id integer primary key, name varchar(50));

create table Pet(code integer primary key, name varchar(50), specie varchar(20));

create table PetOwner(id integer, code integer, primary key (id,code), foreign key (id) references Owner(id), foreign key (code) references Pet(code));


delete from PetOwner;
delete from Pet;
delete from Owner;

insert into Owner values (1,'Mark Costas');
insert into Owner values (2,'Helen Kaye');
insert into Owner values (3,'Robin Scott');
insert into Owner values (4,'Tom Cohen');

insert into Pet values (100,'Wilma','dog');
insert into Pet values (101,'Kitty','cat');
insert into Pet values (102,'Wilma','cat');
insert into Pet values (103,'Lucky','dog');
insert into Pet values (104,'Rocky','dog');
insert into Pet values (105,'Oreo', 'cat');
insert into Pet values (106,'Cecile','turtle');
insert into Pet values (107,'Chelsea','dog');

insert into PetOwner values(1,100);
insert into PetOwner values(1,101);
insert into PetOwner values(2,102);
insert into PetOwner values(2,103);
insert into PetOwner values(3,104);
insert into PetOwner values(3,105);
insert into PetOwner values(4,106);
insert into PetOwner values(4,107);

create or replace view CatsOrDogsOwner(id,aname,specie) AS SELECT O.id, P.name, P.specie from Owner O, Pet P, PetOwner PO where O.id = PO.id and P.code = PO.code and (specie='cat' or specie='dog');
create or replace view CatsAndDogsOwner(id,aname) as select A.id,A.aname from CatsOrDogsOwner A, CatsOrDogsOwner B where A.id=B.id and A.specie=B.specie;
create or replace view LessThan6(id) as select id from CatsOrDogsOwner group by id having count(*)<6;

create or replace view NoCommonName(id) as select id from CatsAndDogsOwner minus select B.id from CatsAndDogsOwner A, CatsAndDogsOwner B where A.id <> B.id and A.aname = B.aname;
create or replace view Guest(id,name) as select id, name from Owner natural inner join NoCommonName natural inner join LessThan6;
-------- correct version of CatsAndDogsOwner
--create or replace view CatsAndDogsOwner(id,aname) as select A.id,A.aname from CatsOrDogsOwner A, CatsOrDogsOwner B where A.id=B.id and A.specie<>B.specie;

