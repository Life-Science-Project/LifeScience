insert into public_approach (id, name, creation_date, owner_id)
values (2, 'approach 2', parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into category_approaches (approaches_id, categories_id)
values (1, 2);
-- nextPublicApproachId = 3
alter sequence public_approach_seq restart with 3;

insert into approach_edit_record (id, approach_id)
values (1, 1);
insert into approach_edit_record (id, approach_id)
values (2, 2);
alter sequence approach_edit_record_seq restart with 3;

-- state = PENDING
insert into approach_review_request (id, date, state, editor_id, edit_record_id)
values (1, parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
-- state = CANCELED
insert into approach_review_request (id, date, state, editor_id, edit_record_id)
values (2, parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 1);
-- state = CANCELED
insert into approach_review_request (id, date, state, editor_id, edit_record_id)
values (3, parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 2);
-- nextId = 4
alter sequence approach_review_request_seq restart with 4;

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
