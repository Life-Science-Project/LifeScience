insert into category (id, name, order_number, parent_id)
values (1, 'root', 1, null);
insert into category
values (2, 'child category 1', 2, 1);
insert into category
values (3, 'child category 2', 3, 1);

insert into article (id, category_id)
values (1, 1);
insert into article (id, category_id)
values (2, 2);

insert into user_details (id, academic_degree, doctor_degree, first_name, last_name, orcid, research_id)
values (1, 0, 0, 'alex1', 'ln alex', '123', '222');
insert into user_details (id, academic_degree, doctor_degree, first_name, last_name, orcid, research_id)
values (2, 0, 0, 'alex1', 'ln alex', '123', '222');

insert into user_credential (id, password, username, user_id)
values (1, 'admin', 'admin', 1);
insert into user_credential (id, password, username, user_id)
values (2, 'user', 'user', 2);

insert into roles (id, name)
values (1, 'ROLE_USER');
insert into roles (id, name)
values (2, 'ROLE_ADMIN');
insert into roles (id, name)
values (3, 'ROLE_MODERATOR');

insert into users_roles(user_id, role_id)
values (1, 1);
insert into users_roles(user_id, role_id)
values (1, 2);
insert into users_roles(user_id, role_id)
values (1, 3);
insert into users_roles(user_id, role_id)
values (2, 1);

insert into article_version (id, name, state, author_id, main_article_id)
values (1, 'master 1', 3, 1, 1);
insert into article_version (id, name, state, author_id, main_article_id)
values (2, 'version 2.1', 1, 1, 1);

insert into section (id, description, name, article_version_id, order_number)
values (1, 'desc 1.1', 'name 1.1', 1, 1);
insert into section (id, description, name, article_version_id, order_number)
values (2, 'desc 1.2', 'name 1.2', 1, 2);
insert into section (id, description, name, article_version_id, order_number)
values (3, 'desc 2', 'name 2', 2, 3);
