/* PROTOCOLS */
insert into draft_protocol (id, name, approach_id, owner_id)
values (1, 'first protocol', 1, 1);
insert into draft_protocol (id, name, approach_id, owner_id)
values (2, 'second protocol', 1, 1);
insert into draft_protocol (id, name, approach_id, owner_id)
values (3, 'third protocol', 1, 1);
insert into draft_protocol (id, name, approach_id, owner_id)
values (4, 'fourth protocol', 1, 1);
-- nextId = 5
alter sequence draft_protocol_seq restart with 5;

insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (2, 1);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (2, 2);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (1, 1);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (1, 2);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (3, 1);
insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (4, 1);

/* REQUESTS & REVIEWS */
insert into publish_protocol_request (id, date, state, editor_id, protocol_id)
values (5, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
insert into publish_protocol_request (id, date, state, editor_id, protocol_id)
values (6, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 2);
insert into publish_protocol_request (id, date, state, editor_id, protocol_id)
values (7, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 3);
insert into publish_protocol_request (id, date, state, editor_id, protocol_id)
values (8, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 1);
-- nextId = 9
alter sequence publish_approach_request_seq restart with 9;

insert into review (id, comment, date, resolution, reviewer_id)
values (8, 'comment 8', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1);
insert into review (id, comment, date, resolution, reviewer_id)
values (9, 'comment 9', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 2);
insert into review (id, comment, date, resolution, reviewer_id)
values (10, 'comment 10', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1);
insert into review (id, comment, date, resolution, reviewer_id)
values (11, 'comment 11', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 2);
insert into review (id, comment, date, resolution, reviewer_id)
values (12, 'comment 12', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1);
insert into review (id, comment, date, resolution, reviewer_id)
values (13, 'comment 13', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1);
insert into review (id, comment, date, resolution, reviewer_id)
values (14, 'comment 14', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 2);
-- nextId = 15
alter sequence review_seq restart with 15;

insert into publish_protocol_request_reviews (publish_protocol_request_id, reviews_id)
values (5, 8);
insert into publish_protocol_request_reviews (publish_protocol_request_id, reviews_id)
values (5, 9);
insert into publish_protocol_request_reviews (publish_protocol_request_id, reviews_id)
values (6, 10);
insert into publish_protocol_request_reviews (publish_protocol_request_id, reviews_id)
values (6, 11);
insert into publish_protocol_request_reviews (publish_protocol_request_id, reviews_id)
values (7, 12);
insert into publish_protocol_request_reviews (publish_protocol_request_id, reviews_id)
values (8, 13);
insert into publish_protocol_request_reviews (publish_protocol_request_id, reviews_id)
values (8, 14);
