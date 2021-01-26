insert into authority (name) values ('ROLE_ADMIN');
insert into authority (name) values ('ROLE_USER');

insert into account (email, password, activated) values ('admin1@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user1@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user2@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user3@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', false);

insert into account (email, password, activated) values ('admin2@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);


insert into account_authority (account_id, authority_id) values (1, 1);
insert into account_authority (account_id, authority_id) values (2, 2);
insert into account_authority (account_id, authority_id) values (3, 2);

insert into account_authority (account_id, authority_id) values (5, 1);



insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'firstName1', 'lastName1', 2);
insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'firstName2', 'lastName2', 3);

insert into administrator (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'adminName1', 'adminSurname2', 1);
insert into administrator (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'adminName2', 'adminSurname2', 5);


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

insert into cultural_offer (name, address, longitude, latitude, subcategory_id, category_id, archived, description, rating, review_count) values ('CulturalOffer1', 'address1', 15.0, 15.0, 1, 1, false, 'description1', 0.0, 0);
insert into cultural_offer (name, address, longitude, latitude, subcategory_id, category_id, archived, description, rating, review_count) values ('CulturalOffer2', 'address2', 30.0, 30.0, 1, 1, false, 'description2', 0.0, 0);
insert into cultural_offer (name, address, longitude, latitude, subcategory_id, category_id, archived, description, rating, review_count) values ('CulturalOffer3', 'address3', 45.0, 45.0, 2, 1, false, 'description3', 0.0, 0);

insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 1, 'News 11', '2020-02-04 00:19:35', 3, 'news description 11', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 1, 'News 12', '2020-08-08 06:02:44', 3,  'news description 12', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 1, 'News 13', '2020-08-09 18:48:16', 3, 'news description 13', true);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 2, 'News 24', '2020-09-14 16:46:36', 3, 'news description 24', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 2, 'News 25', '2020-02-21 16:45:27', 3,  'news description 25', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 2, 'News 26', '2020-05-03 04:49:45', 3, 'news description 26', true);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 3, 'News 37', '2020-08-18 18:18:35', 3,  'news description 37', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 3, 'News 38', '2020-04-05 11:23:46', 3,  'news description 38', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 3, 'News 39', '2020-04-09 09:47:01', 3,  'news description 39', true);


insert into subscription (user_id, offer_id) values (1, 1);
insert into subscription (user_id, offer_id) values (1, 3);
insert into subscription (user_id, offer_id) values (2, 2);

insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 1, 'comment', 4.34, null, null, false, 1, '2020-02-05 08:34:34');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 1, 'comment', 3.22, null, null, false, 2, '2020-05-24 08:34:34');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 1, 'comment', 4.55, null, null, false, 1, '2020-02-05 08:34:34');

insert into reply (id, cultural_offer_id, author_id, comment) values (1, 1, 3, 'Reply comment 1');
insert into reply (id, cultural_offer_id, author_id, comment) values (2, 1, 3, 'Reply comment 2');


update review set reply_id = 1, reply_cultural_offer_id = 1 where id = 1;
update review set reply_id = 2, reply_cultural_offer_id = 1 where id = 2;


insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733078/us15fniqaebg9n5w6rp0.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733081/lm4lerjb1z6ufk4w1dev.jpg');

/* Cultural offer add images */
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733078/us15fniqaebg9n5w6rp0.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733081/lm4lerjb1z6ufk4w1dev.jpg');

/* Cultural offer update images */
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733078/us15fniqaebg9n5w6rp0.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733081/lm4lerjb1z6ufk4w1dev.jpg');
-- IMAGES FOR NEWS
---------------------
-- Free News Images
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733078/us15fniqaebg9n5w6rp0.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733081/lm4lerjb1z6ufk4w1dev.jpg');


insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733078/us15fniqaebg9n5w6rp0.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733081/lm4lerjb1z6ufk4w1dev.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733078/us15fniqaebg9n5w6rp0.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733081/lm4lerjb1z6ufk4w1dev.jpg');


-- Taken News Images
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733078/us15fniqaebg9n5w6rp0.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733081/lm4lerjb1z6ufk4w1dev.jpg');



insert into news_images (news_id, news_cultural_offer_id, images_id) values (1, 1, 13);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (2, 1, 14);
---------------------
---------------------




