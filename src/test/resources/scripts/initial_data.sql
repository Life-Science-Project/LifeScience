insert into roles (id, name)
values (1, 'ROLE_USER');
insert into roles (id, name)
values (2, 'ROLE_ADMIN');
insert into roles (id, name)
values (3, 'ROLE_MODERATOR');
-- nextId = 4
alter sequence role_seq restart with 4;

insert into favorite_group (id, name) values (1, 'Favorite');
-- nextFavoriteGroupId = 2
alter sequence favorite_group_seq restart with 2;

insert into users (id, first_name, last_name, favorite_group_id, academic_degree)
values (1, 'Alex', 'R', 1, 1);
insert into users (id, first_name, last_name)
values (2, 'Ben', 'S');
insert into users (id, first_name, last_name)
values (4, 'Regular', 'RS');
alter sequence user_personal_data_seq restart with 5;

-- login=email@email.ru, password=password, role = user
insert into credentials (id, email, password, user_personal_data_id)
values (1, 'email@email.ru', '$2a$10$qL3JuO4sEC7h9bw1Me9Kn.cnJGmK5dp68MI3B0ynKrJXvDy/iRG86', 1);
-- login=admin@gmail.ru, password=password, role = admin
insert into credentials (id, email, password, user_personal_data_id)
values (2, 'admin@gmail.ru', '$2a$10$qL3JuO4sEC7h9bw1Me9Kn.cnJGmK5dp68MI3B0ynKrJXvDy/iRG86', 2);
-- login=simple@gmail.ru, password=user123, role = user
insert into credentials (id, email, password, user_personal_data_id)
values (3, 'simple@gmail.ru', '$2a$10$deGk.zxpc23BWE7Upb89IOG1eELe3cK0RIA0h91aB/wjLFOkE/a8.', null);
-- login=regular@gmail.ru, password=user123, role = user
insert into credentials (id, email, password, user_personal_data_id)
values (4, 'regular@gmail.ru', '$2a$10$deGk.zxpc23BWE7Upb89IOG1eELe3cK0RIA0h91aB/wjLFOkE/a8.', 4);
-- nextId = 4
alter sequence credentials_seq restart with 5;

insert into users_roles (user_id, role_id)
values (1, 1);
insert into users_roles (user_id, role_id)
values (2, 2);
insert into users_roles (user_id, role_id)
values (3, 1);

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

insert into public_approach (id, name, creation_date, owner_id)
values (1, 'approach 1', parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into category_approaches (categories_id, approaches_id)
values (1, 1);
-- nextPublicApproachId = 2
alter sequence public_approach_seq restart with 2;
