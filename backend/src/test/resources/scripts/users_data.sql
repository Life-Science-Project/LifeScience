insert into roles (id, name)
values (1, 'ROLE_USER');
insert into roles (id, name)
values (2, 'ROLE_ADMIN');
insert into roles (id, name)
values (3, 'ROLE_MODERATOR');
-- nextId = 4
ALTER SEQUENCE role_seq RESTART WITH 4;

-- login=email, password=password
INSERT INTO credentials (id, email, password)
VALUES (1, 'email', '$2a$10$qL3JuO4sEC7h9bw1Me9Kn.cnJGmK5dp68MI3B0ynKrJXvDy/iRG86');
-- login=email2, password=password
INSERT INTO credentials (id, email, password)
VALUES (2, 'email2', '$2a$10$qL3JuO4sEC7h9bw1Me9Kn.cnJGmK5dp68MI3B0ynKrJXvDy/iRG86');
-- login=email3, password=password
INSERT INTO credentials (id, email, password)
VALUES (3, 'email3', '$2a$10$qL3JuO4sEC7h9bw1Me9Kn.cnJGmK5dp68MI3B0ynKrJXvDy/iRG86');
-- nextId = 4
ALTER SEQUENCE credentials_seq RESTART WITH 4;
