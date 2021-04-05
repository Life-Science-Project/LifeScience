insert into section values (1, 'root', null);
insert into section values (2, 'child section 1', 1);
insert into section values (3, 'child section 2', 1);

insert into method (id, name, general_info_id, section_id) values (1, 'test method 1', null, 1);
insert into method (id, name, general_info_id, section_id) values (2, 'test method 2', null, 2);

insert into container (id, description, name, method_id) values (1, 'desc 1.1', 'name 1.1', 1);
insert into container (id, description, name, method_id) values (2, 'desc 1.2', 'name 1.2', 1);
insert into container (id, description, name, method_id) values (3, 'desc 2', 'name 2', 2);

update method set general_info_id = 1 where id = 1;
update method set general_info_id = 3 where id = 2;
