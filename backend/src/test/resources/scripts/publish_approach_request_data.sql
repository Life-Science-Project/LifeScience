insert into draft_approach (id, name, owner_id)
values (1, 'first approach', 1);
insert into draft_approach (id, name, owner_id)
values (2, 'second approach', 1);

-- state = PENDING
insert into publish_approach_request(id, date, state, editor_id, approach_id)
values (1, parsedatetime('22-05-2021 12:53:47.31', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
-- state = CANCELED
insert into publish_approach_request(id, date, state, editor_id, approach_id)
values (2, parsedatetime('22-05-2021 12:53:47.31', 'dd-MM-yyyy hh:mm:ss.SS'), 2, 1, 1);
-- state = APPROVED
insert into publish_approach_request(id, date, state, editor_id, approach_id)
values (3, parsedatetime('17-05-2021 12:53:47.31', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 2);
