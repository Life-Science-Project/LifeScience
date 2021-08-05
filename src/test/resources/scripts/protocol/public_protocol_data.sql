insert into public_protocol (id, name, rating, approach_id, owner_id)
values (1, 'first published', 0, 1, 1);
alter sequence public_protocol_seq restart with 2;

insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (1, 1);
insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (1, 2);

insert into draft_protocol (id, name, approach_id, owner_id)
values (2, 'second protocol', 1, 1);
-- nextId = 3
alter sequence draft_protocol_seq restart with 3;

insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (2, 1);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (2, 2);

insert into section (id, name, order_num, published, hidden)
values (1, 'section', 1, true, false);

insert into section (id, name, order_num, published, hidden)
values (2, 'section 2', 2, true, false);

insert into public_protocol_sections (public_protocol_id, sections_id)
values (1, 1);
