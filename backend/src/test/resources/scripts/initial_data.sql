insert into roles (id, name)
values (1, 'ROLE_USER');
insert into roles (id, name)
values (2, 'ROLE_ADMIN');
insert into roles (id, name)
values (3, 'ROLE_MODERATOR');

-- login=email, password=password
INSERT INTO credentials (id, email, password)
VALUES (1, 'email', '$2a$10$qL3JuO4sEC7h9bw1Me9Kn.cnJGmK5dp68MI3B0ynKrJXvDy/iRG86');

insert into category (id, creation_date, name) values (0, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'root');

insert into category (id, creation_date, name) values (1, parsedatetime('17-09-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 1');
insert into category_parents (category_id, parents_id) values (1, 0);

insert into category (id, creation_date, name) values (2, parsedatetime('17-10-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 2');
insert into category_parents (category_id, parents_id) values (2, 0);

insert into category (id, creation_date, name) values (4, parsedatetime('18-10-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 2');
insert into category_parents (category_id, parents_id) values (4, 2);

insert into category (id, creation_date, name) values (5, parsedatetime('19-10-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 2');
insert into category_parents (category_id, parents_id) values (5, 2);
insert into category_parents (category_id, parents_id) values (5, 4);

insert into category (id, creation_date, name) values (3, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'child 1-2');
insert into category_parents (category_id, parents_id) values (3, 1);

insert into public_approach (id, name, creation_date) values (1, 'approach 1', parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'));
insert into public_approach_categories (public_approach_id, categories_id) values (1, 3)
