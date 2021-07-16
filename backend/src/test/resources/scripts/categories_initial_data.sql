insert into category (id, creation_date, name) values (0, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'root');

insert into category (id, creation_date, name) values (1, parsedatetime('17-09-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 1');
insert into category_sub_categories (parents_id, sub_categories_id) values (0, 1);

insert into category (id, creation_date, name) values (2, parsedatetime('17-10-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 2');
insert into category_sub_categories (parents_id, sub_categories_id) values (0, 2);

insert into category (id, creation_date, name) values (4, parsedatetime('18-10-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 2');
insert into category_sub_categories (parents_id, sub_categories_id) values (2, 4);

insert into category (id, creation_date, name) values (5, parsedatetime('19-10-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'catalog 2');
insert into category_sub_categories (parents_id, sub_categories_id) values (2, 5);
insert into category_sub_categories (parents_id, sub_categories_id) values (4, 5);

insert into category (id, creation_date, name) values (3, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'child 1-2');
insert into category_sub_categories (parents_id, sub_categories_id) values (1, 3);

insert into public_approach (id, name, creation_date, owner_id) values (1, 'approach 1', parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into category_approaches (category_id, approaches_id) values (1, 1)
