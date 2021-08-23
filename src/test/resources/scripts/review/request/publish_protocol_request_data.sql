insert into draft_protocol (id, name, approach_id, owner_id)
values (1, 'first protocol', 1, 1);
insert into draft_protocol (id, name, approach_id, owner_id)
values (2, 'second protocol', 1, 1);
-- nextId = 3
alter sequence draft_protocol_seq restart with 3;

-- state = PENDING
insert into publish_protocol_request(id, date, state, editor_id, protocol_id)
values (1, parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
-- state = CANCELED
insert into publish_protocol_request(id, date, state, editor_id, protocol_id)
values (2, parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 1);
-- state = CANCELED
insert into publish_protocol_request(id, date, state, editor_id, protocol_id)
values (3, parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 2);
-- nextId = 4
alter sequence publish_protocol_request_seq restart with 4;

merge into review(id, comment, date, resolution, reviewer_id)
values (1, 'first review', parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 3);
merge into review(id, comment, date, resolution, reviewer_id)
values (2, 'second review', parsedatetime('22-05-2021 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 3);
-- nextId = 3
alter sequence review_seq restart with 3;

insert into publish_protocol_request_reviews(publish_protocol_request_id, reviews_id)
values (1, 1);
insert into publish_protocol_request_reviews(publish_protocol_request_id, reviews_id)
values (1, 2);
