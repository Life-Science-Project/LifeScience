insert into public_approach_co_authors (public_approach_id, co_authors_id)
values (1, 1);

insert into draft_approach (id, name, owner_id, creation_date)
values (1, 'first approach', 1, parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'));

insert into draft_approach_categories (draft_approach_id, categories_id)
values (1, 1);
insert into draft_approach_participants (draft_approach_id, participants_id)
values (1, 1);

insert into public_approach (id, name, creation_date, owner_id)
values (2, 'approach 2', parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into public_approach (id, name, creation_date, owner_id)
values (3, 'approach 3', parsedatetime('17-12-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
insert into category_approaches (categories_id, approaches_id)
values (1, 2);
insert into category_approaches (categories_id, approaches_id)
values (1, 3);

insert into public_protocol (id, name, rating, approach_id, owner_id)
values (1, 'first published', 0, 1, 1);
insert into public_protocol (id, name, rating, approach_id, owner_id)
values (2, 'second published', 0, 2, 1);

insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (1, 1);
insert into public_protocol_co_authors (public_protocol_id, co_authors_id)
values (2, 1);

insert into draft_protocol (id, name, approach_id, owner_id)
values (1, 'second protocol', 1, 1);

insert into draft_protocol_participants (draft_protocol_id, participants_id)
values (1, 1);
