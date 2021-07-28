insert into section (id, name, order_num, published, visible)
values (1, 'general 1', 1, false, true);
insert into section (id, name, order_num, published, visible)
values (2, 'general 2', 2, true, false);
insert into section (id, name, order_num, published, visible)
values (3, 'last', 2, false, false);
insert into section (id, name, order_num, published, visible)
values (4, 'last 2', 2, false, false);
insert into section (id, name, order_num, published, visible)
values (5, 'last 3', 3, false, false);
-- nextId = 5
alter sequence section_seq restart with 5;
