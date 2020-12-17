insert into category (name, archived) values ('Category1', false);
insert into category (name, archived) values ('Category2', false);
insert into category (name, archived) values ('Category3', false);
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

insert into authority (name) values ('ROLE_ADMIN');
insert into authority (name) values ('ROLE_USER');

insert into account (email, password, activated) values ('admin1@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);
insert into account (email, password, activated) values ('user1@gmail.com', '$2a$10$o0TztuG9xNJB9uB0M9PNDuiU2c5EJPs/8M82eJIOfZBX9i9vgfSvu', true);

insert into account_authority (account_id, authority_id) values (1, 1);
insert into account_authority (account_id, authority_id) values (2, 2);