insert into section (id, name, order_num, published, hidden)
values (1, 'general 1', 1, false, false);
insert into section (id, name, order_num, published, hidden)
values (2, 'general 2', 2, true, true);
insert into section (id, name, order_num, published, hidden)
values (3, 'last', 2, false, true);
insert into section (id, name, order_num, published, hidden)
values (4, 'last 2', 2, false, true);
insert into section (id, name, order_num, published, hidden)
values (5, 'last 3', 3, false, true);
-- nextId = 6
alter sequence section_seq restart with 6;
