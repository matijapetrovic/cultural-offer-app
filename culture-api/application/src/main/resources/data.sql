-- Passwords are hashed using BCrypt algorithm https://www.dailycred.com/article/bcrypt-calculator
-- Passwords for all users are: 123

-- Script generates extra small database for cultural offer app
-- It generates:
-- 	- 2 authorities
-- 	- 10 accounts
-- 		- 5 for administrators
-- 		- 5 for regular users
-- 	- 5 administrators
-- 	- 5 regular users
-- 	- 3 categories
-- 	- 6 subcategories
-- 		- 2 per category
-- 	- 10 cultural offers
-- 	- 20 news
-- 		- 2 per cultural offer
-- 	- 20 reviews
-- 		- 2 per cultural offer
-- 	- 20 replies
-- 	- 29 images
-- 		- 9 for cultural offers
-- 		- 15 for news
-- 		- 5 for reviews

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------- Inserting authority ------------------------------------------------------------------------------------------
insert into authority (name) values ('ROLE_ADMIN');
insert into authority (name) values ('ROLE_USER');
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------- Inserting account ------------------------------------------------------------------------------------------
insert into account (email, password, activated) values ('admin1@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('admin2@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('admin3@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('admin4@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('admin5@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);

insert into account (email, password, activated) values ('user1@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user2@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user3@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user4@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user5@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------- Inserting account_authority --------------------------------------------------------------------------------------
insert into account_authority (account_id, authority_id) values (1, 1);
insert into account_authority (account_id, authority_id) values (2, 1);
insert into account_authority (account_id, authority_id) values (3, 1);
insert into account_authority (account_id, authority_id) values (4, 1);
insert into account_authority (account_id, authority_id) values (5, 1);

insert into account_authority (account_id, authority_id) values (6, 2);
insert into account_authority (account_id, authority_id) values (7, 2);
insert into account_authority (account_id, authority_id) values (8, 2);
insert into account_authority (account_id, authority_id) values (9, 2);
insert into account_authority (account_id, authority_id) values (10, 2);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------- Inserting administrator ---------------------------------------------------------------------------------------
insert into administrator (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Shawanna', 'Mills', 1);
insert into administrator (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Blake', 'Hamill', 2);
insert into administrator (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Kelvin', 'Larkin', 3);
insert into administrator (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Mason', 'Crist', 4);
insert into administrator (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Perry', 'Gislason', 5);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------ Inserting category ------------------------------------------------------------------------------------------
insert into category (name, archived) values ('Institution', false);
insert into category (name, archived) values ('Manifestation', false);
insert into category (name, archived) values ('Cultural Good', false);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------- Inserting subcategory ----------------------------------------------------------------------------------------
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 1, 'Museum', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 1, 'Gallery', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 2, 'Festival', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 2, 'Fair', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 3, 'Monument', false);
insert into subcategory (id, category_id, name, archived) values (nextval('subcategory_id_seq'), 3, 'Landmark', false);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------- Inserting cultural_offer ---------------------------------------------------------------------------------------
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 1: Super Saiyan Goku', 'Description 1: Whoa. This is heavy.', -20.98635, -28.547265, 1, 1, false);
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 2: Nappa', 'Description 2: Stella! Another one of these damn kids jumped in front of my car! Come on out here! Help me take him in the house!', -162.97425, -38.081847, 2, 1, false);
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 3: Mr. Popo', 'Description 3: I m gonna get that son of a bitch.', 22.11193, -60.239935, 3, 2, false);
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 4: Uh Shenlong', 'Description 4: My equipment. That reminds me, Marty. You better not hook up to the amplifier. There s a slight possibility of overload.', 111.612988, -70.78216, 4, 2, false);
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 5: Android 19', 'Description 5: You re late! Do you have no concept of time?', 27.795519, 56.924236, 5, 3, false);
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 6: Freeza', 'Description 6: He laid out Biff in one punch. I didn t know he had it in him. He s never stood up to Biff in his life!', 10.562506, 71.667605, 6, 3, false);
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 7: Captain Ginyu', 'Description 7: Course! From a group of Lybian Nationalists They wanted me to build them a bomb, so I took their plutonium and in turn I gave them a shiny bomb caseing full of used pinball machine parts!', -151.872544, -12.767895, 1, 1, false);
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 8: Android 20', 'Description 8: Since you re new here, I-I m gonna cut you a break, today. So, why don t you make like a tree and get outta here?', -17.588061, 53.050166, 2, 1, false);
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 9: Whis', 'Description 9: We re the, uh... We re the Pinheads.', 137.418139, -64.069131, 3, 2, false);
insert into cultural_offer (name, description, longitude, latitude, subcategory_id, category_id, archived) values ('Cultural offer 10: Super Saiyan Blue Goku', 'Description 10: No, no, no, no, no, this sucker s electrical, but I need a nuclear reaction to generate the 1.21 gigawatts of electricity I need.', 55.043149, -72.526566, 4, 2, false);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------- Inserting regular_user ----------------------------------------------------------------------------------------
insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Luella', 'Morar', 6);
insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Simone', 'Kutch', 7);
insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Albert', 'Mohr', 8);
insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Rod', 'Parisian', 9);
insert into regular_user (id, first_name, last_name, account_id) values (nextval('user_id_seq'), 'Darryl', 'Stokes', 10);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------- Inserting subscription ----------------------------------------------------------------------------------------
insert into subscription (user_id, offer_id) values (6, 1);
insert into subscription (user_id, offer_id) values (6, 2);
insert into subscription (user_id, offer_id) values (7, 3);
insert into subscription (user_id, offer_id) values (7, 4);
insert into subscription (user_id, offer_id) values (8, 5);
insert into subscription (user_id, offer_id) values (8, 6);
insert into subscription (user_id, offer_id) values (9, 7);
insert into subscription (user_id, offer_id) values (9, 8);
insert into subscription (user_id, offer_id) values (10, 9);
insert into subscription (user_id, offer_id) values (10, 10);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------- Inserting news --------------------------------------------------------------------------------------------
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 1, 'News 1: Julie', '2020-06-17 10:46:15', 2, 'Description 1: There are some things you can t share without ending up liking each other, and knocking out a twelve-foot mountain troll is one of them.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 1, 'News 2: Joshua Burgin', '2020-03-06 03:13:50', 1, 'Description 2: To the well-organized mind, death is but the next great adventure.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 2, 'News 3: Rachel Green', '2020-06-15 07:50:24', 1, 'Description 3: Harry, suffering like this proves you are still a man! This pain is part of being human...the fact that you can feel pain like this is your greatest strength.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 2, 'News 4: Dr. Long', '2020-05-02 05:34:28', 3, 'Description 4: If you want to know what a man’s like, take a good look at how he treats his inferiors, not his equals.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 3, 'News 5: Joey Tribbiani', '2020-02-08 03:53:51', 1, 'Description 5: You re a wizard, Harry.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 3, 'News 6: Emma Geller', '2020-04-19 17:22:47', 1, 'Description 6: We could all have been killed - or worse, expelled.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 4, 'News 7: Sophie', '2020-02-17 17:34:21', 1, 'Description 7: Never trust anything that can think for itself if you can t see where it keeps its brain.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 4, 'News 8: David', '2020-06-23 15:20', 3, 'Description 8: You re a wizard, Harry.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 5, 'News 9: Peter Becker', '2020-07-22 06:20:30', 2, 'Description 9: Never trust anything that can think for itself if you can t see where it keeps its brain.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 5, 'News 10: Mark Robinson', '2020-10-25 03:43:44', 3, 'Description 10: Things we lose have a way of coming back to us in the end, if not always in the way we expect.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 6, 'News 11: Bonnie', '2020-04-24 02:11:20', 1, 'Description 11: Things we lose have a way of coming back to us in the end, if not always in the way we expect.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 6, 'News 12: Fun Bobby', '2020-04-15 14:06:15', 3, 'Description 12: It does not do to dwell on dreams and forget to live.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 7, 'News 13: Mr. Zelner', '2020-08-24 08:31:34', 2, 'Description 13: It does not do to dwell on dreams and forget to live.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 7, 'News 14: Mike Hannigan', '2020-09-12 10:21:38', 3, 'Description 14: No story lives unless someone wants to listen. The stories we love best do live in us forever. So whether you come back by page or by the big screen, Hogwarts will always be there to welcome you home.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 8, 'News 15: Gary', '2020-05-19 07:44:26', 1, 'Description 15: I solemnly swear that I am up to no good.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 8, 'News 16: Nora Tyler Bing', '2020-06-13 16:11:46', 3, 'Description 16: Of course it is happening inside your head, Harry, but why on earth should that mean that it is not real?', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 9, 'News 17: Jack Geller', '2020-04-27 07:38:04', 2, 'Description 17: Of course it is happening inside your head, Harry, but why on earth should that mean that it is not real?', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 9, 'News 18: Ben', '2020-10-10 16:35:31', 3, 'Description 18: After all this time? Always.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 10, 'News 19: Estelle Leonard', '2020-08-10 08:42:28', 2, 'Description 19: Never trust anything that can think for itself if you can t see where it keeps its brain.', false);
insert into news (id, cultural_offer_id, title, posted_date, author_id, text, archived) values (nextval('news_id_seq'), 10, 'News 20: Dr. Long', '2020-06-13 08:33:04', 1, 'Description 20: Things we lose have a way of coming back to us in the end, if not always in the way we expect.', false);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------- Inserting review -------------------------------------------------------------------------------------------
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 1, 'Comment 1: The programs that Chuck Norris writes don t have version numbers because he only writes them once. If a user reports a bug or has a feature request they don t live to see the sun set.', 4.17, null, null, false, 7, '2020-09-16 00:52:32');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 1, 'Comment 2: Quantum cryptography does not work on Chuck Norris. When something is being observed by Chuck it stays in the same state until he s finished.', 3.98, null, null, false, 8, '2020-05-10 07:32:45');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 2, 'Comment 3: Chuck Norris  addition operator doesn t commute; it teleports to where he needs it to be.', 0.84, null, null, false, 7, '2020-02-16 18:52:32');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 2, 'Comment 4: Chuck Norris s keyboard doesn t have a Ctrl key because nothing controls Chuck Norris.', 3.54, null, null, false, 8, '2020-11-26 07:37:23');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 3, 'Comment 5: Chuck Norris s beard can type 140 wpm.', 3.25, null, null, false, 6, '2020-05-09 07:31:53');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 3, 'Comment 6: Chuck Norris doesn t get compiler errors, the language changes itself to accommodate Chuck Norris.', 2.73, null, null, false, 6, '2020-01-06 19:11:41');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 4, 'Comment 7: When Chuck Norris presses Ctrl+Alt+Delete, worldwide computer restart is initiated.', 1.07, null, null, false, 7, '2020-05-14 01:07:34');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 4, 'Comment 8: Chuck Norris can spawn threads that complete before they are started.', 3.50, null, null, false, 8, '2020-09-12 16:09:40');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 5, 'Comment 9: Chuck Norris doesn t get compiler errors, the language changes itself to accommodate Chuck Norris.', 3.00, null, null, false, 7, '2020-09-09 01:45:45');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 5, 'Comment 10: Chuck Norris does not use exceptions when programming. He has not been able to identify any of his code that is not exceptional.', 0.27, null, null, false, 6, '2020-02-06 07:36:20');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 6, 'Comment 11: The only pattern Chuck Norris knows is God Object.', 0.59, null, null, false, 7, '2020-06-01 18:20:39');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 6, 'Comment 12: There is no Esc key on Chuck Norris  keyboard, because no one escapes Chuck Norris.', 3.01, null, null, false, 8, '2020-02-15 05:51:25');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 7, 'Comment 13: The class object inherits from Chuck Norris.', 1.86, null, null, false, 7, '2020-06-23 16:42:34');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 7, 'Comment 14: Chuck Norris can binary search unsorted data.', 3.63, null, null, false, 8, '2020-10-03 07:21:06');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 8, 'Comment 15: Chuck Norris is immutable. If something s going to change, it s going to have to be the rest of the universe.', 3.82, null, null, false, 7, '2020-11-17 19:44:30');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 8, 'Comment 16: When Chuck Norris points to null, null quakes in fear.', 0.07, null, null, false, 8, '2020-05-09 09:14:20');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 9, 'Comment 17: Chuck Norris programs do not accept input.', 3.85, null, null, false, 7, '2020-02-10 11:31:24');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 9, 'Comment 18: Chuck Norris doesn t need the cloud to scale his applications, he uses his laptop.', 2.63, null, null, false, 8, '2020-05-13 03:17:34');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 10, 'Comment 19: Chuck Norris can recite π. Backwards.', 2.10, null, null, false, 6, '2020-07-12 11:01:32');
insert into review (id, cultural_offer_id, comment, rating, reply_cultural_offer_id, reply_id, archived, user_id, date) values (nextval('review_id_seq'), 10, 'Comment 20: Chuck Norris doesn t get compiler errors, the language changes itself to accommodate Chuck Norris.', 1.82, null, null, false, 8, '2020-06-20 09:36:12');
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------- Inserting reply -------------------------------------------------------------------------------------------
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 1, 2, 'Description 1: Some old wounds never truly heal, and bleed again at the slightest word.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 1, 1, 'Description 2: Laughter is poison to fear.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 2, 2, 'Description 3: Why is it that when one man builds a wall, the next man immediately needs to know what s on the other side?');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 2, 1, 'Description 4: When you play a game of thrones you win or you die.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 3, 2, 'Description 5: A lion doesn t concern itself with the opinion of sheep.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 3, 3, 'Description 6: The North remembers.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 4, 1, 'Description 7: A lion doesn t concern itself with the opinion of sheep.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 4, 1, 'Description 8: Things are not always as they seemed, much that may seem evil can be good.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 5, 2, 'Description 9: When the snows fall and the white winds blow, the lone wolf dies but the pack survives.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 5, 3, 'Description 10: Some old wounds never truly heal, and bleed again at the slightest word.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 6, 1, 'Description 11: When you play a game of thrones you win or you die.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 6, 3, 'Description 12: Summer will end soon enough, and childhood as well.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 7, 1, 'Description 13: Winter is coming.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 7, 1, 'Description 14: Hodor? Hodor.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 8, 2, 'Description 15: And so he spoke, and so he spoke, that Lord of Castamere, but now the rains weep o er his hall, with no one there to hear. Yes, now the rains weep o er his hall, and not a soul to hear.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 8, 3, 'Description 16: And so he spoke, and so he spoke, that Lord of Castamere, but now the rains weep o er his hall, with no one there to hear. Yes, now the rains weep o er his hall, and not a soul to hear.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 9, 2, 'Description 17: Once you’ve accepted your flaws, no one can use them against you.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 9, 1, 'Description 18: Knowledge could be more valuable than gold, more deadly than a dagger.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 10, 1, 'Description 19: Hodor? Hodor.');
insert into reply (id, cultural_offer_id, author_id, comment) values (nextval('reply_id_seq'), 10, 1, 'Description 20: The things I do for love.');
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------- Updating review -------------------------------------------------------------------------------------------
update review set reply_id = 1, reply_cultural_offer_id = 1 where id = 1;
update review set reply_id = 2, reply_cultural_offer_id = 1 where id = 2;
update review set reply_id = 3, reply_cultural_offer_id = 2 where id = 3;
update review set reply_id = 4, reply_cultural_offer_id = 2 where id = 4;
update review set reply_id = 5, reply_cultural_offer_id = 3 where id = 5;
update review set reply_id = 6, reply_cultural_offer_id = 3 where id = 6;
update review set reply_id = 7, reply_cultural_offer_id = 4 where id = 7;
update review set reply_id = 8, reply_cultural_offer_id = 4 where id = 8;
update review set reply_id = 9, reply_cultural_offer_id = 5 where id = 9;
update review set reply_id = 10, reply_cultural_offer_id = 5 where id = 10;
update review set reply_id = 11, reply_cultural_offer_id = 6 where id = 11;
update review set reply_id = 12, reply_cultural_offer_id = 6 where id = 12;
update review set reply_id = 13, reply_cultural_offer_id = 7 where id = 13;
update review set reply_id = 14, reply_cultural_offer_id = 7 where id = 14;
update review set reply_id = 15, reply_cultural_offer_id = 8 where id = 15;
update review set reply_id = 16, reply_cultural_offer_id = 8 where id = 16;
update review set reply_id = 17, reply_cultural_offer_id = 9 where id = 17;
update review set reply_id = 18, reply_cultural_offer_id = 9 where id = 18;
update review set reply_id = 19, reply_cultural_offer_id = 10 where id = 19;
update review set reply_id = 20, reply_cultural_offer_id = 10 where id = 20;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------- Inserting image -------------------------------------------------------------------------------------------
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733078/us15fniqaebg9n5w6rp0.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733081/lm4lerjb1z6ufk4w1dev.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733084/ixa45k3kbengeuvdghyp.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733086/zf1ouljmqtb9mcolllto.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733087/j9cchs7irfl6muck3pnt.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733090/devlkkkolpuc9ysosdri.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733136/xsd5sldhzzzcjtwepzq1.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733138/o7vwfdlansomh4khyo3m.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733139/hzbk79o9wspizjiazizy.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733140/e6jnqc2v1khjkny5qe6k.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733142/evrbgdt2yvwvcydksu6f.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733145/gimy8nhi8ejsfqfkj97b.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733146/qso7wdo3pavqj9uiqrzc.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733148/ynwk1ntwiesfn35fboiy.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733149/heukyyzuuf7rsqtjvzpn.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733151/iwgw0dy83okxvlxdkusr.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733153/bsjrqavijnznbn8dfytf.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733154/wvgcwcfkbrpl48qzks4q.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733156/h2yhx6pmwn90caj09hmd.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733158/cgufwzxmhqnp3jbdmway.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733160/jvry901gzxbgsnhujvr2.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733163/v79doz6wjug9x4whr6zc.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733166/srqzfccfzevaa9tvhhwp.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733167/vrs5q2o9qz34krszygn7.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733169/aeli2hklwmvlhronx4wt.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733172/iarnsuieukmwtcmxzfnj.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733175/ne20uo9rlr0su4v3gdco.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733291/jpsxxlrhczadnclrobm2.jpg');
insert into image (url) values ('http://res.cloudinary.com/culture-app/image/upload/v1607733294/ytawo05c94hhfvjntfmr.jpg');
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------ Inserting cultural_offer_images -----------------------------------------------------------------------------------
insert into cultural_offer_images (cultural_offer_id, images_id) values (1, 1);
insert into cultural_offer_images (cultural_offer_id, images_id) values (2, 2);
insert into cultural_offer_images (cultural_offer_id, images_id) values (3, 3);
insert into cultural_offer_images (cultural_offer_id, images_id) values (4, 4);
insert into cultural_offer_images (cultural_offer_id, images_id) values (5, 5);
insert into cultural_offer_images (cultural_offer_id, images_id) values (6, 6);
insert into cultural_offer_images (cultural_offer_id, images_id) values (7, 7);
insert into cultural_offer_images (cultural_offer_id, images_id) values (8, 8);
insert into cultural_offer_images (cultural_offer_id, images_id) values (9, 9);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------- Inserting news_images ----------------------------------------------------------------------------------------
insert into news_images (news_id, news_cultural_offer_id, images_id) values (1, 1, 10);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (2, 1, 11);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (3, 2, 12);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (4, 2, 13);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (5, 3, 14);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (6, 3, 15);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (7, 4, 16);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (8, 4, 17);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (9, 5, 18);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (10, 5, 19);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (11, 6, 20);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (12, 6, 21);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (13, 7, 22);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (14, 7, 23);
insert into news_images (news_id, news_cultural_offer_id, images_id) values (15, 8, 24);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------- Inserting review_images ---------------------------------------------------------------------------------------
insert into review_images (review_id, review_cultural_offer_id, images_id) values (1, 1, 25);
insert into review_images (review_id, review_cultural_offer_id, images_id) values (2, 1, 26);
insert into review_images (review_id, review_cultural_offer_id, images_id) values (3, 2, 27);
insert into review_images (review_id, review_cultural_offer_id, images_id) values (4, 2, 28);
insert into review_images (review_id, review_cultural_offer_id, images_id) values (5, 3, 29);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

