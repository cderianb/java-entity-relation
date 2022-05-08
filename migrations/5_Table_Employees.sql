--EMPLOYEES
create table employees(
    id bigserial,
    organization_id bigint not null,
    "name" varchar(255) not null,
    department_id bigint not null,
    constraint pk_employees primary key(id, organization_id),
    constraint fk_employee_department foreign key (department_id, organization_id) references departments(id, organization_id)
    constraint fk_employees_organization foreign key(organization_id) REFERENCES organizations(id)
) partition by hash(organization_id);
CREATE TABLE employees_0 PARTITION OF employees FOR VALUES WITH (modulus 3, remainder 0);
CREATE TABLE employees_1 PARTITION OF employees FOR VALUES WITH (modulus 3, remainder 1);
CREATE TABLE employees_2 PARTITION OF employees FOR VALUES WITH (modulus 3, remainder 2);