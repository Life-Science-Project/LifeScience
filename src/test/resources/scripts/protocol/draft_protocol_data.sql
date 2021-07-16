insert into draft_protocol (id, name, owner_id)
values (1, 'draft_protocol_test', 1);
insert into draft_protocol (id, name, owner_id)
values (2, 'draft_protocol_second_test', 1);
-- nextId = 3
alter sequence draft_protocol_seq restart with 3;

insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (1, 1);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (1, 2);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (2, 1);

insert into section (id, name, order_num, published, visible)
values (1, 'draft_protocol_section', 2, false, false);
insert into section (id, name, order_num, published, visible)
values (2, 'section_to_add', 2, false, false);

insert into draft_protocol_sections (draft_protocol_id, sections_id)
values (2, 1)
