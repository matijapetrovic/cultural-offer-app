insert into authority (id, name) values (1, 'ROLE_ADMIN');
insert into authority (id, name) values (2, 'ROLE_USER');

insert into account (email, password, activated) values ('admin@gmail.com', '$2a$10$ShxMC2hNJzcZI0So7aIZWeAi455lp/o3WUgw.Lf/Kj.PE2QVlQjdO', true);
insert into account_authority (account_id, authority_id) values (1, 1);
insert into administrator (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Jovan', 'Bodroza', 1);

insert into account (email, password, activated) values ('user@gmail.com', '$2a$10$ShxMC2hNJzcZI0So7aIZWeAi455lp/o3WUgw.Lf/Kj.PE2QVlQjdO', true);
insert into account_authority (account_id, authority_id) values (2, 2);
insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Aleksandar', 'Aleksic', 2);

insert into category (name, archived) values ('Institution', false);
insert into category (name, archived) values ('Manifestation', false);
insert into category (name, archived) values ('Cultural Good', false);

insert into subcategory (category_id, id, name, archived) values (1, nextval('subcategory_id_seq'), 'Museum', false);
insert into subcategory (category_id, id, name, archived) values (1, nextval('subcategory_id_seq'), 'Gallery', false);
insert into subcategory (category_id, id, name, archived) values (2, nextval('subcategory_id_seq'), 'Festival', false);
insert into subcategory (category_id, id, name, archived) values (2, nextval('subcategory_id_seq'), 'Fair', false);
insert into subcategory (category_id, id, name, archived) values (3, nextval('subcategory_id_seq'), 'Monument', false);
insert into subcategory (category_id, id, name, archived) values (3, nextval('subcategory_id_seq'), 'Landmark', false);

insert into cultural_offer (name, subcategory_id, category_id, longitude, latitude, archived) values ('offer',1, 1, 80, 80, false);

insert  into review (id, cultural_offer_id, comment, archived) values (nextval('review_id_seq'), 1, 'aaa', false);

insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1606935300/m6paxwralnvpfl8qwtwn.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1606935301/ksaqdgvg0ntswti5djem.jpg');