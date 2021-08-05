insert into draft_protocol (id, name, approach_id, owner_id)
values (1, 'first protocol', 1, 1);
insert into draft_protocol (id, name, approach_id, owner_id)
values (2, 'second protocol', 1, 1);
insert into draft_protocol (id, name, approach_id, owner_id)
values (3, 'third protocol', 1, 1);

-- nextId = 4
alter sequence draft_protocol_seq restart with 4;


insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (1, 1);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (2, 1);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (3, 1);

insert into section (id, name, order_num, published, hidden)
values (10, 'general 1', 1, false, false);
insert into section (id, name, order_num, published, hidden)
values (11, 'general 2', 2, false, true);
-- nextId = 12
alter sequence section_seq restart with 12;

insert into draft_protocol_sections (draft_protocol_id, sections_id)
values (1, 10);
insert into draft_protocol_sections (draft_protocol_id, sections_id)
values (1, 11);
