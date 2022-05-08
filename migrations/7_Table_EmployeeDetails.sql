--Employee Details
create table employee_details(
    id bigserial, 
    organization_id bigint not null,
    employee_id bigint not null,
    salary int,
    address varchar(255),
    constraint pk_employee_details primary key(id, organization_id),
    constraint fk_employee_detail_employee foreign key (employee_id, organization_id) references employees(id, organization_id)
) partition by hash(organization_id);
CREATE TABLE employee_details_0 PARTITION OF employee_details FOR VALUES WITH (modulus 3, remainder 0);
CREATE TABLE employee_details_1 PARTITION OF employee_details FOR VALUES WITH (modulus 3, remainder 1);
CREATE TABLE employee_details_2 PARTITION OF employee_details FOR VALUES WITH (modulus 3, remainder 2);