insert into section (id, name, order_num, published, visible)
values (12, 'general 12', 1, false, true);
insert into section (id, name, order_num, published, visible)
values (13, 'general 13', 2, false, false);
insert into section (id, name, order_num, published, visible)
values (14, 'general 13', 2, true, false);
-- nextId = 15
alter sequence section_seq restart with 15;

insert into approach_edit_record (id, approach_id)
values (1, 1);

insert into approach_edit_record_created_sections (approach_edit_record_id, created_sections_id)
values (1, 12);
insert into approach_edit_record_created_sections (approach_edit_record_id, created_sections_id)
values (1, 13);

insert into public_approach_sections (public_approach_id, sections_id)
values (1, 14);

insert into approach_edit_record_deleted_sections (approach_edit_record_id, deleted_sections_id)
values (1, 14);
