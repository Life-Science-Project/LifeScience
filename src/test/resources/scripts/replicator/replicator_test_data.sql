insert into roles (id, name)
values (1, 'ROLE_USER');
insert into roles (id, name)
values (2, 'ROLE_ADMIN');
insert into roles (id, name)
values (3, 'ROLE_MODERATOR');
-- nextId = 4
alter sequence role_seq restart with 4;


-- USERS --
insert into users (id, first_name, last_name, academic_degree)
values (1, 'Alex', 'R', 1);
alter sequence user_personal_data_seq restart with 2;
-- login=admin@gmail.ru, password=password, role = admin
insert into credentials (id, email, password, user_personal_data_id)
values (1, 'admin@gmail.ru', '$2a$10$qL3JuO4sEC7h9bw1Me9Kn.cnJGmK5dp68MI3B0ynKrJXvDy/iRG86', 1);
alter sequence credentials_seq restart with 2;

insert into users_roles (user_id, role_id)
values (1, 2);


-- CATEGORIES --
insert into category (id, creation_date, name)
values (1, parsedatetime('17-09-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 1');

insert into category (id, creation_date, name)
values (2, parsedatetime('17-10-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 2');

insert into category (id, creation_date, name)
values (3, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'child 1-2');
insert into category_sub_categories (parents_id, sub_categories_id)
values (1, 3);

insert into category (id, creation_date, name)
values (4, parsedatetime('18-10-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 2');
insert into category_sub_categories (parents_id, sub_categories_id)
values (2, 4);

insert into category (id, creation_date, name)
values (5, parsedatetime('19-10-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 2');
insert into category_sub_categories (parents_id, sub_categories_id)
values (2, 5);
insert into category_sub_categories (parents_id, sub_categories_id)
values (4, 5);
-- nextCategoryId = 6
alter sequence category_seq restart with 6;


-- APPROACHES --
insert into public_approach (id, name, creation_date, owner_id)
values (1, 'approach 1', parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into category_approaches (categories_id, approaches_id)
values (1, 1);

insert into draft_approach (id, creation_date, name, owner_id)
values (1, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'approach 1', 1);
insert into draft_approach_categories values (1, 1);
insert into draft_approach_participants (draft_approach_id, participants_id) values (1, 1);


-- PROTOCOLS --
insert into public_protocol (id, name, rating, approach_id, owner_id)
values (1, 'first published', 0, 1, 1);
