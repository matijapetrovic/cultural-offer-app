insert into category (name) values ('Institution');
insert into category (name) values ('Manifestation');
insert into category (name) values ('Cultural Good');

insert into subcategory (category_id, id, name, archived) values (1, 1, 'Museum', false);
insert into subcategory (category_id, id, name, archived) values (1, 2, 'Gallery', false);
insert into subcategory (category_id, id, name, archived) values (2, 1, 'Festival', false);
insert into subcategory (category_id, id, name, archived) values (2, 2, 'Fair', false);
insert into subcategory (category_id, id, name, archived) values (3, 1, 'Monument', false);
insert into subcategory (category_id, id, name, archived) values (3, 2, 'Landmark', false);