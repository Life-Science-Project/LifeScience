insert into users (DTYPE, id, email, password, academic_degree, doctor_degree, first_name, last_name, orcid, research_id, refresh_token)
values ('user', 1, 'admin', '$2a$10$rpa/.GjMX2a4BuLoGRHr8OaAxE3sBZ1XjyiAJnmW52x84D24LrxAG' /* password = admin */,
        0, 0, 'Admin', 'Admin-Admin', '123', '222', 'admin_refresh'),
       ('user', 2, 'user', '$2a$10$CHR3GaUcPWgToK3igKkNQu8rlecOz2Y4w20hWwsVZKUWIdQddwQu6' /* password = user */,
        1, 1, 'User', 'User-User', '123', '222', 'user_refresh'),
       ('user', 3, 'moderator', '$2a$10$b9FzIlCSy/I/WPyBsg/0EexDYMFrOB26jIDln.AQM6YVoRspF8H1y' /* password = moderator */,
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
