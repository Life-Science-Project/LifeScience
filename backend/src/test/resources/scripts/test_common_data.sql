insert into category
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
values (1, 'password', 'sample', 1);
insert into user_credential (id, password, username, user_id)
values (2, 'password2', 'sample2', 2);

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
