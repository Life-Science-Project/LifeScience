insert into roles values (1, 'ROLE_USER') on conflict do nothing;
insert into roles values (2, 'ROLE_ADMIN') on conflict do nothing;
insert into roles values (3, 'ROLE_MODERATOR') on conflict do nothing;
