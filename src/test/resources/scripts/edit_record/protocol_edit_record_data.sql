insert into public_protocol (id, name, rating, approach_id, owner_id)
values (1, 'first published', 0, 1, 1);
insert into public_protocol (id, name, rating, approach_id, owner_id)
values (2, 'second published', 0, 1, 1);
alter sequence public_approach_seq restart with 3;

insert into protocol_edit_record (id, last_edit_date, protocol_id)
values (1, parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into protocol_edit_record (id, last_edit_date, protocol_id)
values (2, parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 2);
-- nextId = 3
alter sequence protocol_edit_record_seq restart with 3;

insert into section (id, name, order_num, published, hidden)
values (1, 'section 1', 1, true, false);
insert into section (id, name, order_num, published, hidden)
values (2, 'section 2', 2, true, true);
insert into section (id, name, order_num, published, hidden)
values (3, 'section 3', 3, false, true);

insert into public_protocol_sections (public_protocol_id, sections_id)
values (1, 1);
insert into public_protocol_sections (public_protocol_id, sections_id)
values (2, 3);

insert into protocol_edit_record_created_sections (protocol_edit_record_id, created_sections_id)
values (1, 3);
insert into protocol_edit_record_deleted_sections (protocol_edit_record_id, deleted_sections_id)
values (2, 3);
