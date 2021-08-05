insert into public_protocol (id, name, rating, approach_id, owner_id)
values (2, 'proto', 0, 1, 1);
alter sequence public_protocol_seq restart with 3;

insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (2, 1);

insert into section (id, name, order_num, published, hidden)
values (15, 'general 12', 1, false, false);
insert into section (id, name, order_num, published, hidden)
values (16, 'general 13', 2, false, true);
insert into section (id, name, order_num, published, hidden)
values (17, 'general 13', 2, true, true);
-- nextId = 18
alter sequence section_seq restart with 18;

insert into protocol_edit_record (id, protocol_id)
values (1, 2);

insert into protocol_edit_record_created_sections (protocol_edit_record_id, created_sections_id)
values (1, 15);
insert into protocol_edit_record_created_sections (protocol_edit_record_id, created_sections_id)
values (1, 16);

insert into public_protocol_sections (public_protocol_id, sections_id)
values (2, 17);

insert into protocol_edit_record_deleted_sections (protocol_edit_record_id, deleted_sections_id)
values (1, 17);
