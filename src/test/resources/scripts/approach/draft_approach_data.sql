insert into draft_approach (id, creation_date, name, owner_id)
values (1, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'approach 1', 1);

insert into draft_approach (id, creation_date, name, owner_id)
values (2, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'approach 22', 1);

insert into draft_approach (id, creation_date, name, owner_id)
values (3, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'approach 333', 1);
insert into draft_approach_sections (draft_approach_id, sections_id)
values (3, 3);

alter sequence draft_approach_seq restart with 3;

insert into draft_approach_categories values (1, 1);
insert into draft_approach_categories values (1, 2);

insert into draft_approach_participants (draft_approach_id, participants_id) values (1, 1);
insert into draft_approach_participants (draft_approach_id, participants_id) values (1, 4);