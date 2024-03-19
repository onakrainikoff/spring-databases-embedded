create table element_group_types(
    id INTEGER IDENTITY PRIMARY KEY,
    code varchar(50) not null
);
insert into element_group_types(id, code) values (1, 'TYPE1');
insert into element_group_types(id, code) values (2, 'TYPE2');

create table element_groups(
    id INTEGER IDENTITY PRIMARY KEY,
    date_created TIMESTAMP not null,
    group_type integer not null,
    name varchar(500) not null,
    constraint element_groups_to_element_group_types foreign key (group_type) references element_group_types(id)
);
create unique index udx_element_groups_name on element_groups (name);

create table elements(
    id INTEGER IDENTITY PRIMARY KEY,
    date_created TIMESTAMP not null,
    element_group integer not null,
    name varchar(500) not null,
    constraint elements_to_element_groups foreign key (element_group) references element_groups(id)
);
create index idx_elements_name on elements (name);