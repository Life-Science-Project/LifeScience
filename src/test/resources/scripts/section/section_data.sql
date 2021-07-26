insert into section (id, name, order_num, published, visible)
values (1, 'general 1', 1, false, true);
insert into section (id, name, order_num, published, visible)
values (2, 'general 2', 2, true, false);
insert into section (id, name, order_num, published, visible)
values (3, 'last', 2, false, false);
insert into section (id, name, order_num, published, visible)
values (4, 'last', 3, false, false);
insert into section (id, name, order_num, published, visible)
values (5, 'ab', 3, true, true);
insert into section (id, name, order_num, published, visible)
values (6, 'cd', 3, true, true);
insert into section (id, name, order_num, published, visible)
values (7, 'de', 3, true, true);
-- nextId = 5
alter sequence section_seq restart with 5;
