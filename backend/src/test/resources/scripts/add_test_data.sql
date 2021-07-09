insert into roles (id, name)
values (1, 'ROLE_USER');
insert into roles (id, name)
values (2, 'ROLE_ADMIN');
insert into roles (id, name)
values (3, 'ROLE_MODERATOR');

-- login=email, password=password
INSERT INTO credentials (id, email, password)
VALUES (1, 'email', '$2a$10$qL3JuO4sEC7h9bw1Me9Kn.cnJGmK5dp68MI3B0ynKrJXvDy/iRG86');

insert into section (id, name, order_num, published, visible)
values (1, 'general 1', 1, false, true);
insert into section (id, name, order_num, published, visible)
values (2, 'general 2', 2, true, false);
insert into section (id, name, order_num, published, visible)
values (10, 'last', 2, false, false);

insert into category (id, name, order_num)
values (0, 'HEAD', 1);

insert into public_approach(id, name, owner_id)
values (1, 'public_approach', 1);
insert into public_approach(id, name, owner_id)
values (2, 'second_public_approach', 1);

insert into draft_approach (id, name, owner_id)
values (1, 'first approach', 1);
insert into draft_approach (id, name, owner_id)
values (2, 'bradford', 1);

insert into draft_approach_categories (draft_approach_id, categories_id)
values (1, 0);
insert into draft_approach_categories (draft_approach_id, categories_id)
values (2, 0);

insert into draft_approach_participants (draft_approach_id, participants_id)
values (2, 1);

insert into draft_protocol (id, name, owner_id)
values (1, 'draft_protocol_test', 1);
