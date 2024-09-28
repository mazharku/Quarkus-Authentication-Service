CREATE TABLE roles
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_roles PRIMARY KEY (id)
);
insert into roles (id, name) values(1, 'Admin');
insert into roles (id, name) values(2, 'User');
insert into roles (id, name) values(3, 'Manager');
alter sequence myentity_seq restart with 4;