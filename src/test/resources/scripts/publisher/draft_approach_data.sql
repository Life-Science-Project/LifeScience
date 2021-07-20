insert into draft_approach (id, creation_date, name, owner_id)
values (1, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'first approach', 1);
insert into draft_approach (id, creation_date, name, owner_id)
values (2, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'bradford', 1);
insert into draft_approach (id, creation_date, name, owner_id)
values (3, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'third', 1);

-- nextId = 4
alter sequence draft_approach_seq restart with 4;

insert into draft_approach_categories (draft_approach_id, categories_id)
values (1, 0);
insert into draft_approach_categories (draft_approach_id, categories_id)
values (2, 0);
insert into draft_approach_categories (draft_approach_id, categories_id)
values (3, 0);

insert into draft_approach_participants (draft_approach_id, participants_id)
values (2, 1);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (2, 2);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (1, 1);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (1, 2);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (3, 1);

insert into section (id, name, order_num, published, visible)
values (8, 'general 1', 1, false, true);
insert into section (id, name, order_num, published, visible)
values (9, 'general 2', 2, false, false);
-- nextId = 10
alter sequence section_seq restart with 10;

insert into draft_approach_sections (draft_approach_id, sections_id)
values (1, 8);
insert into draft_approach_sections (draft_approach_id, sections_id)
values (1, 9);
