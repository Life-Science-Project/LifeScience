/* PROTOCOLS */

insert into public_protocol (id, name, rating, approach_id, owner_id)
values (1, 'first protocol', 0, 1, 1);
insert into public_protocol (id, name, rating, approach_id, owner_id)
values (2, 'second protocol', 0, 1, 1);
insert into public_protocol (id, name, rating, approach_id, owner_id)
values (3, 'third protocol', 0, 1, 1);
insert into public_protocol (id, name, rating, approach_id, owner_id)
values (4, 'fourth protocol', 0, 1, 1);
-- nextId = 5
alter sequence public_protocol_seq restart with 56;


insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (2, 1);
insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (2, 2);
insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (1, 1);
insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (1, 2);
insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (3, 1);
insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (4, 1);

/* EDIT RECORDS */
insert into protocol_edit_record (id, protocol_id)
values (1, 1);
insert into protocol_edit_record (id, protocol_id)
values (2, 2);
insert into protocol_edit_record (id, protocol_id)
values (3, 3);
insert into protocol_edit_record (id, protocol_id)
values (4, 4);

/* REQUESTS & REVIEWS */
insert into protocol_review_request (id, date, state, editor_id, edit_record_id)
values (1, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
insert into protocol_review_request (id, date, state, editor_id, edit_record_id)
values (2, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 2);
insert into protocol_review_request (id, date, state, editor_id, edit_record_id)
values (3, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 3);
insert into protocol_review_request (id, date, state, editor_id, edit_record_id)
values (4, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 1);
-- nextId = 5
alter sequence protocol_review_request_seq restart with 5;

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

insert into protocol_review_request_reviews (protocol_review_request_id, reviews_id)
values (1, 8);
insert into protocol_review_request_reviews (protocol_review_request_id, reviews_id)
values (1, 9);
insert into protocol_review_request_reviews (protocol_review_request_id, reviews_id)
values (2, 10);
insert into protocol_review_request_reviews (protocol_review_request_id, reviews_id)
values (2, 11);
insert into protocol_review_request_reviews (protocol_review_request_id, reviews_id)
values (3, 12);
insert into protocol_review_request_reviews (protocol_review_request_id, reviews_id)
values (4, 13);
insert into protocol_review_request_reviews (protocol_review_request_id, reviews_id)
values (4, 14);
