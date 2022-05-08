create table map_employees_tasks(
    id bigserial,
    organization_id bigint not null,
    employee_id bigint not null,
    task_id bigint not null,
    constraint pk_map_employees_tasks primary key(id, organization_id),
    constraint fk_map_employee_task_organization foreign key(organization_id) REFERENCES organizations(id),
    constraint fk_map_employee_task_employee foreign key(employee_id, organization_id) REFERENCES employees(id, organization_id),
    constraint fk_map_employee_task_task foreign key(task_id, organization_id) REFERENCES tasks(id, organization_id)
) partition by hash(organization_id);
CREATE TABLE map_employees_tasks_0 PARTITION OF map_employees_tasks FOR VALUES WITH (modulus 3, remainder 0);
CREATE TABLE map_employees_tasks_1 PARTITION OF map_employees_tasks FOR VALUES WITH (modulus 3, remainder 1);
CREATE TABLE map_employees_tasks_2 PARTITION OF map_employees_tasks FOR VALUES WITH (modulus 3, remainder 2);