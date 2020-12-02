insert into account (email, password) values ('admin', 'admin');

insert into administrator (id, first_name, last_name, account_id) values (1001, 'Jovan', 'Bodroza', 1);

insert into cultural_offer (name, description, longitude, latitude, archived) values ('ponuda 1', 'opis 1', 0, 0, false);

insert into category (name, archived) values ('Institution', false);
insert into category (name, archived) values ('Manifestation', false);
insert into category (name, archived) values ('Cultural Good', false);

insert into subcategory (category_id, id, name, archived) values (1, 100000001, 'Museum', false);
insert into subcategory (category_id, id, name, archived) values (1, 100000002, 'Gallery', false);
insert into subcategory (category_id, id, name, archived) values (2, 100000001, 'Festival', false);
insert into subcategory (category_id, id, name, archived) values (2, 100000002, 'Fair', false);
insert into subcategory (category_id, id, name, archived) values (3, 100000001, 'Monument', false);
insert into subcategory (category_id, id, name, archived) values (3, 100000002, 'Landmark', false);