insert into public_approach_co_authors (public_approach_id, co_authors_id)
values (1, 1);
insert into public_approach_co_authors (public_approach_id, co_authors_id)
values (1, 2);

insert into draft_approach (id, name, owner_id, creation_date)
values (1, 'first approach', 1, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'));
-- nextId = 2
alter sequence draft_approach_seq restart with 2;

insert into public_approach_sections (public_approach_id, sections_id)
values (1, 4);
insert into public_approach_sections (public_approach_id, sections_id)
values (1, 5);
insert into public_approach_sections (public_approach_id, sections_id)
values (1, 6);

insert into draft_approach_categories (draft_approach_id, categories_id)
values (1, 1);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (1, 1);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (1, 2);
