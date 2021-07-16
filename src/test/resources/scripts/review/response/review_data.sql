insert into review (id, comment, date, resolution, reviewer_id)
values (1, 'my first review', parsedatetime('17-11-2020 00:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 0, 1);
-- nextId = 2
alter sequence review_seq restart with 2;
