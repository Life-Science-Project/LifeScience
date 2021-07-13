insert into draft_approach (name, owner_id)
values ('first approach', 1);
insert into draft_approach (name, owner_id)
values ('second approach', 1);

-- id = 1, state = PENDING
insert into publish_approach_request(date, state, editor_id, approach_id)
values (parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
-- id = 2, state = CANCELED
insert into publish_approach_request(date, state, editor_id, approach_id)
values (parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 2, 1, 1);
-- id = 3, state = APPROVED
insert into publish_approach_request(date, state, editor_id, approach_id)
values (parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 2);

insert into review(comment, date, resolution, reviewer_id)
values ('first review', parsedatetime('22-05-2021 12:54:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 3);
insert into review(comment, date, resolution, reviewer_id)
values ('second review', parsedatetime('22-05-2021 12:55:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 3);

insert into publish_approach_request_reviews(publish_approach_request_id, reviews_id)
values (1, 1);
insert into publish_approach_request_reviews(publish_approach_request_id, reviews_id)
values (1, 2);
