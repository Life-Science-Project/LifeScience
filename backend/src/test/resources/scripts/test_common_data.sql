insert into section
values (1, 'root', null);
insert into section
values (2, 'child section 1', 1);
insert into section
values (3, 'child section 2', 1);

insert into method (id, section_id)
values (1, 1);
insert into method (id, section_id)
values (2, 2);

insert into users (id, email, enabled, first_name, last_name, password, username)
VALUES (1, 'e', true, 'test user name', 'test user last_name', 'password', 'sample');
insert into users (id, email, enabled, first_name, last_name, password, username)
VALUES (2, 'e', true, 'test user 2 name', 'test user 2 last_name', 'password2', 'sample2');

insert into method_version (id, name, state, author_id, main_method_id)
values (1, 'master 1', 3, 1, 1);
insert into method_version (id, name, state, author_id, main_method_id)
values (2, 'version 2.1', 1, 1, 1);

insert into container (id, description, name, method_id)
values (1, 'desc 1.1', 'name 1.1', 1);
insert into container (id, description, name, method_id)
values (2, 'desc 1.2', 'name 1.2', 1);
insert into container (id, description, name, method_id)
values (3, 'desc 2', 'name 2', 2);
