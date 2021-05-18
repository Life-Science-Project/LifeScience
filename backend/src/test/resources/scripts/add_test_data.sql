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
insert into article (id, category_id)
values (3, 1);

insert into users (id, email, password, academic_degree, doctor_degree, first_name, last_name, orcid, research_id, refresh_token)
values (1, 'admin', '$2a$10$rpa/.GjMX2a4BuLoGRHr8OaAxE3sBZ1XjyiAJnmW52x84D24LrxAG' /* password = admin */,
        0, 0, 'Admin', 'Admin-Admin', '123', '222', 'admin_refresh'),
       (2, 'user', '$2a$10$CHR3GaUcPWgToK3igKkNQu8rlecOz2Y4w20hWwsVZKUWIdQddwQu6' /* password = user */,
        1, 1, 'User', 'User-User', '123', '222', 'user_refresh'),
       (3, 'moderator', '$2a$10$b9FzIlCSy/I/WPyBsg/0EexDYMFrOB26jIDln.AQM6YVoRspF8H1y' /* password = moderator */,
        1, 1, 'Moderator', 'Moderator-Moderator', '125', '239', 'moderator_refresh');

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

-- Article 1
-- Article published
insert into article_version (id, name, state, author_id, main_article_id)
values (1, 'master 1', 3, 1, 1);
insert into article_version (id, name, state, author_id, main_article_id)
values (2, 'version 1.1', 1, 1, 1);
insert into article_version (id, name, state, author_id, main_article_id)
values (3, 'version 2.1', 1, 1, 1);
insert into article_version (id, name, state, author_id, main_article_id)
values (4, 'version 4.1', 0, 2, 1);
insert into article_version (id, name, state, author_id, main_article_id)
values (5, 'version 5.1', 1, 2, 1);
insert into article_version (id, name, state, author_id, main_article_id)
values (6, 'version 5.1', 1, 1, 2);
-- Protocol published
insert into article_version (id, name, state, author_id, main_article_id)
values (7, 'version 1.2', 4, 1, 1);
-- Article 2
insert into article_version (id, name, state, author_id, main_article_id)
values (8, 'version 8', 1, 1, 2);

insert into section (id, description, name, article_version_id, order_number, visible)
values (1, 'desc 1.1', 'name 1.1', 1, 1, true);
insert into section (id, description, name, article_version_id, order_number, visible)
values (2, 'desc 1.2', 'name 1.2', 1, 2, true);
insert into section (id, description, name, article_version_id, order_number, visible)
values (3, 'desc 1.3', 'name 1.3', 1, 3, false);
insert into section (id, description, name, article_version_id, order_number, visible)
values (4, 'desc 2', 'name 2', 3, 3, true);
insert into section (id, description, name, article_version_id, order_number, visible)
values (5, 'desc 3', 'name 3', 6, 1, true);
insert into section (id, description, name, article_version_id, order_number, visible)
values (6, 'desc 4', 'name 4', 7, 1, true);
-- Article version 8
--  Section with no content
insert into section (id, description, name, article_version_id, order_number, visible)
values (7, 'desc 6', 'name 6', 8, 1, true);
--  Section with content
insert into section (id, description, name, article_version_id, order_number, visible)
values (8, 'desc 8', 'name 8', 8, 1, true);
--  Section with content
insert into section (id, description, name, article_version_id, order_number, visible)
values (9, 'desc 9', 'name 9', 8, 1, true);

insert into users_favourite_articles(user_id, favourite_articles_id)
values (2, 1);
insert into users_favourite_articles(user_id, favourite_articles_id)
values (2, 2);

insert into review_request(id, destination, version_id)
values (1, 1, 4);
insert into review_request(id, destination, version_id)
values (2, 1, 4);
insert into review_request(id, destination, version_id)
values (3, 0, 1);
insert into review_request(id, destination, version_id)
values (4, 1, 6);
insert into review_request(id, destination, version_id)
values (5, 1, 6);
insert into review_request(id, destination, version_id)
values (6, 1, 6);

insert into review(id, comment, reviewer_id, resolution, review_request_id)
values (1, 'comment 1', 1, 1, 3);
insert into review(id, comment, reviewer_id, resolution, review_request_id)
values (2, 'comment 2', 1, 1, 4);
insert into review(id, comment, reviewer_id, resolution, review_request_id)
values (3, 'comment 3', 1, 1, 5);
insert into review(id, comment, reviewer_id, resolution, review_request_id)
values (4, 'comment 4', 1, 1, 6);
insert into review(id, comment, reviewer_id, resolution, review_request_id)
values (5, 'comment 5', 1, 1, 1);
