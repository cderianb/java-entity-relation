--DEPARTMENTS
create table departments(
    id bigserial,
    organization_id bigint not null,
    "name" varchar(255),
    constraint pk_departments primary key (id, organization_id),
    constraint fk_department_organization foreign key (organization_id) references organizations(id)
) partition by hash(organization_id);
CREATE TABLE departments_0 PARTITION OF departments FOR VALUES WITH (modulus 3, remainder 0);
CREATE TABLE departments_1 PARTITION OF departments FOR VALUES WITH (modulus 3, remainder 1);
CREATE TABLE departments_2 PARTITION OF departments FOR VALUES WITH (modulus 3, remainder 2);