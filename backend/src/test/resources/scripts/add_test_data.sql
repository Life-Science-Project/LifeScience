insert into roles (id, name)
values (1, 'ROLE_USER');
insert into roles (id, name)
values (2, 'ROLE_ADMIN');
insert into roles (id, name)
values (3, 'ROLE_MODERATOR');

insert into section (id, name, order_num, published, visible)
values (1, 'general 1', 1, false, true);
insert into section (id, name, order_num, published, visible)
values (2, 'general 2', 2, true, false);
insert into section (id, name, order_num, published, visible)
values (10, 'last', 2, false, false);
