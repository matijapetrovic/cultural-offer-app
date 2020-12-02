insert into authority (id, name) values (1, 'ROLE_ADMIN');
insert into authority (id, name) values (2, 'ROLE_USER');

insert into account (id, email, password, password_changed, activated) values (1, 'admin@gmail.com', '$2a$10$ShxMC2hNJzcZI0So7aIZWeAi455lp/o3WUgw.Lf/Kj.PE2QVlQjdO', true, true);

insert into regular_user (id, first_name, last_name, account_id) values (1000, 'Aleksandar', 'Aleksic', 1);

insert into account_authority (user_id, authority_id) values (1, 2);

insert into category (name, archived) values ('Institution', false);
insert into category (name, archived) values ('Manifestation', false);
insert into category (name, archived) values ('Cultural Good', false);

insert into subcategory (category_id, id, name, archived) values (1, 1, 'Museum', false);
insert into subcategory (category_id, id, name, archived) values (1, 2, 'Gallery', false);
insert into subcategory (category_id, id, name, archived) values (2, 1, 'Festival', false);
insert into subcategory (category_id, id, name, archived) values (2, 2, 'Fair', false);
insert into subcategory (category_id, id, name, archived) values (3, 1, 'Monument', false);
insert into subcategory (category_id, id, name, archived) values (3, 2, 'Landmark', false);