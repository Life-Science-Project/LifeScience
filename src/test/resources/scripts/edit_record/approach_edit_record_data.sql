insert into approach_edit_record (id, last_edit_date, approach_id)
values (1, parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into approach_edit_record (id, last_edit_date, approach_id)
values (2, parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
-- nextId = 3
alter sequence approach_edit_record_seq restart with 3;

insert into section (id, name, order_num, published, visible)
values (1, 'section 1', 1, false, true);
insert into section (id, name, order_num, published, visible)
values (2, 'section 2', 2, false, false);
