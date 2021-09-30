insert into draft_approach (id, creation_date, name, owner_id)
values (1, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'approach 1', 1);

insert into draft_approach (id, creation_date, name, owner_id)
values (2, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'approach 22', 1);

insert into draft_approach (id, creation_date, name, owner_id)
values (3, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'approach 333', 1);

alter sequence draft_approach_seq restart with 3;

insert into draft_approach_categories values (1, 1);
insert into draft_approach_categories values (1, 2);

insert into draft_approach_participants (draft_approach_id, participants_id) values (1, 1);
insert into draft_approach_participants (draft_approach_id, participants_id) values (1, 4);
insert into draft_approach_participants (draft_approach_id, participants_id) values (2, 1);
insert into draft_approach_participants (draft_approach_id, participants_id) values (3, 1);

insert into draft_approach_aliases (draft_approach_id, aliases)
values (1, 'approach 1 alias');
insert into draft_approach_aliases (draft_approach_id, aliases)
values (1, 'approach 1 alias 2');
