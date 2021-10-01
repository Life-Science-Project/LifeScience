insert into draft_approach (id, name, owner_id, creation_date)
values (1, 'first approach', 1, parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'));
insert into draft_approach (id, name, owner_id, creation_date)
values (2, 'second approach', 1, parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'));
-- nextId = 3
ALTER SEQUENCE draft_approach_seq RESTART WITH 3;

-- state = PENDING
insert into publish_approach_request(id, date, state, editor_id, approach_id)
values (1, parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
-- state = CANCELED
insert into publish_approach_request(id, date, state, editor_id, approach_id)
values (2, parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 1);
-- state = CANCELED
insert into publish_approach_request(id, date, state, editor_id, approach_id)
values (3, parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 2);
-- nextId = 4
ALTER SEQUENCE publish_approach_request_seq RESTART WITH 4;

merge into review(id, comment, date, resolution, reviewer_id)
values (1, 'first review', parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 3);
merge into review(id, comment, date, resolution, reviewer_id)
values (2, 'second review', parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 3);
-- nextId = 3
ALTER SEQUENCE review_seq RESTART WITH 3;

insert into publish_approach_request_reviews(publish_approach_request_id, reviews_id)
values (1, 1);
insert into publish_approach_request_reviews(publish_approach_request_id, reviews_id)
values (1, 2);
