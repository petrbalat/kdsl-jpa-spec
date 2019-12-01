create table products
(
    id   INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(30) not null,
    weight numeric,
    dto object
);

insert into PRODUCTS(name, weight) values ('p1', 1.2);
insert into PRODUCTS(name, weight) values ('p2', null);
