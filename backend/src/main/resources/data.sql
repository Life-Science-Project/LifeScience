insert into roles(id, name) values (1, 'ROLE_USER') on conflict do nothing;
insert into roles(id, name) values (2, 'ROLE_ADMIN') on conflict do nothing;
insert into roles(id, name) values (3, 'ROLE_MODERATOR') on conflict do nothing;
-- nextId = 4
ALTER SEQUENCE role_seq RESTART WITH 4;
