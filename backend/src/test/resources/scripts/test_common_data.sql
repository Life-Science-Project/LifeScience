insert into category
values (1, 'root', null);
insert into category
values (2, 'child category 1', 1);
insert into category
values (3, 'child category 2', 1);

insert into article (id, category_id)
values (1, 1);
insert into article (id, category_id)
values (2, 2);

insert into users (id, email, enabled, first_name, last_name, password, username)
VALUES (1, 'e', true, 'test user name', 'test user last_name', 'password', 'sample');
insert into users (id, email, enabled, first_name, last_name, password, username)
VALUES (2, 'e', true, 'test user 2 name', 'test user 2 last_name', 'password2', 'sample2');

insert into article_version (id, name, state, author_id, main_article_id)
values (1, 'master 1', 3, 1, 1);
insert into article_version (id, name, state, author_id, main_article_id)
values (2, 'version 2.1', 1, 1, 1);

insert into section (id, description, name, article_version_id)
values (1, 'desc 1.1', 'name 1.1', 1);
insert into section (id, description, name, article_version_id)
values (2, 'desc 1.2', 'name 1.2', 1);
insert into section (id, description, name, article_version_id)
values (3, 'desc 2', 'name 2', 2);
