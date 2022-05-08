--ORGANIZATIONS
create table organizations(
        id bigserial,
        code varchar(255) not null,
        "name" varchar(255) not null,
        CONSTRAINT pk_organization PRIMARY KEY (id)
    ) partition by hash (id);
CREATE TABLE organizations_0 PARTITION OF organizations FOR VALUES WITH (modulus 3, remainder 0);
CREATE TABLE organizations_1 PARTITION OF organizations FOR VALUES WITH (modulus 3, remainder 1);
CREATE TABLE organizations_2 PARTITION OF organizations FOR VALUES WITH (modulus 3, remainder 2);