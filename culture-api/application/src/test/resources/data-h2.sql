insert into authority (name) values ('ROLE_ADMIN');
insert into authority (name) values ('ROLE_USER');

insert into account (email, password, activated) values ('admin1@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user1@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user2@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user3@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', false);

insert into account_authority (account_id, authority_id) values (1, 1);
insert into account_authority (account_id, authority_id) values (2, 2);
insert into account_authority (account_id, authority_id) values (3, 2);

insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'firstName1', 'lastName1', 2);
insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'firstName2', 'lastName2', 3);

insert into category (name, archived) values ('Category1', false);
insert into category (name, archived) values ('Category2', false);
insert into category (name, archived) values ('Category3', false);
insert into category (name, archived) values ('Category4', false);

insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 1, 'Subcategory11', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 1, 'Subcategory12', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 2, 'Subcategory21', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 2, 'Subcategory22', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 3, 'Subcategory31', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 3, 'Subcategory32', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 1, 'Subcategory13', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 1, 'Subcategory14', true);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 2, 'Subcategory23', true);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 3, 'Subcategory33', false);

insert into cultural_offer (name, address, longitude, latitude, subcategory_id, category_id, archived, description) values ('Culturaloffer1', 'address1', 15.0, 15.0, 1, 1, false, 'description1');
insert into cultural_offer (name, address, longitude, latitude, subcategory_id, category_id, archived, description) values ('Culturaloffer2', 'address2', 30.0, 30.0, 1, 1, false, 'description2');
insert into cultural_offer (name, address, longitude, latitude, subcategory_id, category_id, archived, description) values ('Culturaloffer3', 'address3', 45.0, 45.0, 2, 1, false, 'description3');

insert into subscription (user_id, offer_id) values (1, 1);
insert into subscription (user_id, offer_id) values (1, 3);
insert into subscription (user_id, offer_id) values (2, 2);

insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 1, 'comment', 4.34, null, null, false, 1, '2020-02-05 08:34:34');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 1, 'comment', 3.22, null, null, false, 2, '2020-05-24 08:34:34');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 1, 'comment', 4.55, null, null, false, 1, '2020-02-05 08:34:34');

insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733078/us15fniqaebg9n5w6rp0.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733081/lm4lerjb1z6ufk4w1dev.jpg');
