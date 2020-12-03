insert into account (email, password) values ('admin', 'admin');

insert into administrator (id, first_name, last_name, account_id) values (1001, 'Jovan', 'Bodroza', 1);

insert into category (name, archived) values ('Institution', false);
insert into category (name, archived) values ('Manifestation', false);
insert into category (name, archived) values ('Cultural Good', false);

insert into subcategory (category_id, id, name, archived) values (1, 100000001, 'Museum', false);
insert into subcategory (category_id, id, name, archived) values (1, 100000002, 'Gallery', false);
insert into subcategory (category_id, id, name, archived) values (2, 100000001, 'Festival', false);
insert into subcategory (category_id, id, name, archived) values (2, 100000002, 'Fair', false);
insert into subcategory (category_id, id, name, archived) values (3, 100000001, 'Monument', false);
insert into subcategory (category_id, id, name, archived) values (3, 100000002, 'Landmark', false);

insert into cultural_offer (name, description, subcategory_id, category_id, longitude, latitude, archived) values ('offer', 'very good offer 10/10 would visit', 100000001, 1, 80, 80, false);

insert  into review (id, cultural_offer_id, comment, archived) values (1001, 1, 'aaa', false);

insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1606935300/m6paxwralnvpfl8qwtwn.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1606935301/ksaqdgvg0ntswti5djem.jpg');