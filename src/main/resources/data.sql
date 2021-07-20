insert into roles(id, name) values (1, 'ROLE_USER') on conflict do nothing;
insert into roles(id, name) values (2, 'ROLE_ADMIN') on conflict do nothing;
insert into roles(id, name) values (3, 'ROLE_MODERATOR') on conflict do nothing;
-- nextId = 4
alter sequence role_seq restart with 4;

insert into category (id, creation_date, name)
values (0, TO_TIMESTAMP('2017-03-31 9:30:20', 'YYYY-MM-DD HH:MI:SS'), 'root') on conflict do nothing;
