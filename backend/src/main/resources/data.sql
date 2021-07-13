insert into roles(name) values ('ROLE_USER') on conflict do nothing;
insert into roles(name) values ('ROLE_ADMIN') on conflict do nothing;
insert into roles(name) values ('ROLE_MODERATOR') on conflict do nothing;
