insert into public_approach (id, name, creation_date, owner_id)
values (2, 'approach 2', parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into category_approaches (categories_id, approaches_id) values (1, 2);

insert into approach_edit_record (id, last_edit_date, approach_id)
values (1, parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into approach_edit_record (id, last_edit_date, approach_id)
values (2, parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 2);
-- nextId = 3
alter sequence approach_edit_record_seq restart with 3;

insert into section (id, name, order_num, published, hidden)
values (1, 'section 1', 1, true, false);
insert into section (id, name, order_num, published, hidden)
values (2, 'section 2', 2, true, true);
insert into section (id, name, order_num, published, hidden)
values (3, 'section 3', 3, false, true);

insert into public_approach_sections (public_approach_id, sections_id)
values (1, 1);
insert into public_approach_sections (public_approach_id, sections_id)
values (2, 3);

insert into approach_edit_record_created_sections (approach_edit_record_id, created_sections_id)
values (1, 3);
insert into approach_edit_record_deleted_sections (approach_edit_record_id, deleted_sections_id)
values (2, 3);
