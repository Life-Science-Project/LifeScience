insert into draft_approach (id, name, owner_id)
values (1, 'first approach', 1);
insert into draft_approach (id, name, owner_id)
values (2, 'bradford', 1);
insert into draft_approach (id, name, owner_id)
values (3, 'third', 1);
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
values (1, 'general 1', 1, false, true);
insert into section (id, name, order_num, published, visible)
values (2, 'general 2', 2, true, false);
-- nextId = 3
alter sequence section_seq restart with 3;

insert into draft_approach_sections (draft_approach_id, sections_id)
values (3, 1);
