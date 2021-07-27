/* APPROACHES */
insert into public_approach (id, creation_date, name, owner_id)
values (2, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'first approach', 1);
insert into public_approach (id, creation_date, name, owner_id)
values (3, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'bradford', 1);
insert into public_approach (id, creation_date, name, owner_id)
values (4, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'third', 1);
insert into public_approach (id, creation_date, name, owner_id)
values (5, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'fourth', 1);
-- nextId = 6
alter sequence public_approach_seq restart with 6;

insert into category_approaches (categories_id, approaches_id)
values (1, 2);
insert into category_approaches (categories_id, approaches_id)
values (1, 3);
insert into category_approaches (categories_id, approaches_id)
values (1, 4);
insert into category_approaches (categories_id, approaches_id)
values (1, 5);

insert into public_approach_co_authors (public_approach_id, co_authors_id)
values (3, 1);
insert into public_approach_co_authors (public_approach_id, co_authors_id)
values (3, 2);
insert into public_approach_co_authors (public_approach_id, co_authors_id)
values (2, 1);
insert into public_approach_co_authors (public_approach_id, co_authors_id)
values (2, 2);
insert into public_approach_co_authors (public_approach_id, co_authors_id)
values (4, 1);
insert into public_approach_co_authors (public_approach_id, co_authors_id)
values (5, 1);

/* EDIT RECORDS */
insert into approach_edit_record (id, approach_id)
values (2, 2);
insert into approach_edit_record (id, approach_id)
values (3, 3);
insert into approach_edit_record (id, approach_id)
values (4, 4);
insert into approach_edit_record (id, approach_id)
values (5, 5);

/* REQUESTS & REVIEWS */
insert into approach_review_request (id, date, state, editor_id, edit_record_id)
values (2, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 2);
insert into approach_review_request (id, date, state, editor_id, edit_record_id)
values (3, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 3);
insert into approach_review_request (id, date, state, editor_id, edit_record_id)
values (4, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 4);
insert into approach_review_request (id, date, state, editor_id, edit_record_id)
values (5, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 2);
-- nextId = 6
alter sequence approach_review_request_seq restart with 6;

insert into review (id, comment, date, resolution, reviewer_id)
values (1, 'comment 1', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1);
insert into review (id, comment, date, resolution, reviewer_id)
values (2, 'comment 2', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 2);
insert into review (id, comment, date, resolution, reviewer_id)
values (3, 'comment 3', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1);
insert into review (id, comment, date, resolution, reviewer_id)
values (4, 'comment 4', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 2);
insert into review (id, comment, date, resolution, reviewer_id)
values (5, 'comment 5', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1);
insert into review (id, comment, date, resolution, reviewer_id)
values (6, 'comment 6', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1);
insert into review (id, comment, date, resolution, reviewer_id)
values (7, 'comment 7', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 2);
-- nextId = 8
alter sequence review_seq restart with 8;

insert into approach_review_request_reviews (approach_review_request_id, reviews_id)
values (2, 1);
insert into approach_review_request_reviews (approach_review_request_id, reviews_id)
values (2, 2);
insert into approach_review_request_reviews (approach_review_request_id, reviews_id)
values (3, 3);
insert into approach_review_request_reviews (approach_review_request_id, reviews_id)
values (3, 4);
insert into approach_review_request_reviews (approach_review_request_id, reviews_id)
values (4, 5);
insert into approach_review_request_reviews (approach_review_request_id, reviews_id)
values (5, 6);
insert into approach_review_request_reviews (approach_review_request_id, reviews_id)
values (5, 7);
