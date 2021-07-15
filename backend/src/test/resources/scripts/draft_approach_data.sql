insert into draft_approach (id, creation_date, name, owner_id)
values (1, parsedatetime('17-08-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'approach 1', 1);

alter sequence draft_approach_seq restart with 1;

insert into draft_approach_categories values (1, 1);
insert into draft_approach_categories values (1, 2);