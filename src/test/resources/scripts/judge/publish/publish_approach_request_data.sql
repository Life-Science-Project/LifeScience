/* APPROACHES */
insert into draft_approach (id, creation_date, name, owner_id)
values (1, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'first approach', 1);
insert into draft_approach (id, creation_date, name, owner_id)
values (2, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'bradford', 1);
insert into draft_approach (id, creation_date, name, owner_id)
values (3, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'third', 1);
insert into draft_approach (id, creation_date, name, owner_id)
values (4, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 'fourth', 1);
-- nextId = 5
alter sequence draft_approach_seq restart with 5;

insert into draft_approach_categories (draft_approach_id, categories_id)
values (1, 1);
insert into draft_approach_categories (draft_approach_id, categories_id)
values (2, 1);
insert into draft_approach_categories (draft_approach_id, categories_id)
values (3, 1);
insert into draft_approach_categories (draft_approach_id, categories_id)
values (4, 1);

insert into draft_approach_participants (draft_approach_id, participants_id)
values (2, 1);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (2, 2);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (1, 1);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (1, 2);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (3, 1);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (4, 1);

/* REQUESTS & REVIEWS */
insert into publish_approach_request (id, date, state, editor_id, approach_id)
values (1, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 1);
insert into publish_approach_request (id, date, state, editor_id, approach_id)
values (2, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 2);
insert into publish_approach_request (id, date, state, editor_id, approach_id)
values (3, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1, 3);
insert into publish_approach_request (id, date, state, editor_id, approach_id)
values (4, parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1, 1);
-- nextId = 5
alter sequence publish_approach_request_seq restart with 5;

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

insert into publish_approach_request_reviews (publish_approach_request_id, reviews_id)
values (1, 1);
insert into publish_approach_request_reviews (publish_approach_request_id, reviews_id)
values (1, 2);
insert into publish_approach_request_reviews (publish_approach_request_id, reviews_id)
values (2, 3);
insert into publish_approach_request_reviews (publish_approach_request_id, reviews_id)
values (2, 4);
insert into publish_approach_request_reviews (publish_approach_request_id, reviews_id)
values (3, 5);
insert into publish_approach_request_reviews (publish_approach_request_id, reviews_id)
values (4, 6);
insert into publish_approach_request_reviews (publish_approach_request_id, reviews_id)
values (4, 7);
