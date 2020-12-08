insert into authority (id, name) values (1, 'ROLE_ADMIN');
insert into authority (id, name) values (2, 'ROLE_USER');

insert into account (id, email, password,  activated) values (1, 'admin@gmail.com', '$2a$10$ShxMC2hNJzcZI0So7aIZWeAi455lp/o3WUgw.Lf/Kj.PE2QVlQjdO', true);

insert into regular_user (id, first_name, last_name, account_id) values (1000, 'Aleksandar', 'Aleksic', 1);

insert into account_authority (account_id, authority_id) values (1, 1);

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

insert into cultural_offer (name, subcategory_id, category_id, longitude, latitude, archived) values ('offer',100000001, 1, 80, 80, false);

insert  into review (id, cultural_offer_id, comment, archived) values (1001, 1, 'aaa', false);

insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1606935300/m6paxwralnvpfl8qwtwn.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1606935301/ksaqdgvg0ntswti5djem.jpg');