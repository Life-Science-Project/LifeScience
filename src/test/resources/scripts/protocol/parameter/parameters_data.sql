insert into protocol_parameter (id, name, value)
values (1, 'Mass', '12 * x + 3');
insert into protocol_parameter (id, name, value)
values (2, 'Multiplier', '666');
insert into protocol_parameter (id, name, value)
values (3, 'Rarity', 'Epic');
alter sequence parameter_seq restart with 4;
