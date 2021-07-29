insert into public_protocol (id, name, rating, approach_id, owner_id)
values (1, 'a', 0, 1, 1);
insert into public_protocol (id, name, rating, approach_id, owner_id)
values (2, 'b', 0, 1, 1);
alter sequence public_protocol_seq restart with 3;

insert into protocol_edit_record (id, protocol_id)
values (1, 1);
insert into protocol_edit_record (id, protocol_id)
values (2, 2);
alter sequence protocol_edit_record_seq restart with 3;

-- state = PENDING
insert into protocol_review_request (id, date, state, editor_id, edit_record_id)
values (1, parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
-- state = CANCELED
insert into protocol_review_request (id, date, state, editor_id, edit_record_id)
values (2, parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 1);
-- state = CANCELED
insert into protocol_review_request (id, date, state, editor_id, edit_record_id)
values (3, parsedatetime('22-05-2021 12:53:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 2);
-- nextId = 4
ALTER SEQUENCE protocol_review_request_seq RESTART WITH 4;

insert into review(id, comment, date, resolution, reviewer_id)
values (3, 'first review', parsedatetime('22-05-2021 12:54:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 3);
insert into review(id, comment, date, resolution, reviewer_id)
values (4, 'second review', parsedatetime('22-05-2021 12:55:47.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 3);
-- nextId = 5
ALTER SEQUENCE review_seq RESTART WITH 5;

insert into protocol_review_request_reviews (protocol_review_request_id, reviews_id)
values (1, 3);
insert into protocol_review_request_reviews (protocol_review_request_id, reviews_id)
values (1, 4);
