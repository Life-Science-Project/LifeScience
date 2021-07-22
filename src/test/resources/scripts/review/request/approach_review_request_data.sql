-- state = PENDING
insert into approach_review_request (id, date, state, editor_id, edit_record_id)
values (1, parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
-- state = CANCELED
insert into approach_review_request (id, date, state, editor_id, edit_record_id)
values (2, parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 1);
-- nextId = 3
ALTER SEQUENCE publish_approach_request_seq RESTART WITH 3;

insert into review(id, comment, date, resolution, reviewer_id)
values (1, 'first review', parsedatetime('22-05-2021 12:54:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 3);
insert into review(id, comment, date, resolution, reviewer_id)
values (2, 'second review', parsedatetime('22-05-2021 12:55:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 3);
-- nextId = 3
ALTER SEQUENCE review_seq RESTART WITH 3;

insert into approach_review_request_reviews (approach_review_request_id, reviews_id)
values (1, 1);
insert into approach_review_request_reviews (approach_review_request_id, reviews_id)
values (1, 2);
