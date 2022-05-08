CREATE TABLE public.tasks (
    id bigserial NOT NULL,
    organization_id int8 NOT NULL,
    department_id int8 NOT NULL,
    "name" varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    CONSTRAINT pk_tasks PRIMARY KEY (id, organization_id),
    CONSTRAINT fk_task_department FOREIGN KEY (department_id,organization_id) REFERENCES departments(id,organization_id),
    CONSTRAINT fk_task_organization FOREIGN KEY (organization_id) REFERENCES organizations(id)
)PARTITION BY HASH (organization_id);
CREATE TABLE tasks_0 PARTITION OF tasks FOR VALUES WITH (modulus 3, remainder 0);
CREATE TABLE tasks_1 PARTITION OF tasks FOR VALUES WITH (modulus 3, remainder 1);
CREATE TABLE tasks_2 PARTITION OF tasks FOR VALUES WITH (modulus 3, remainder 2);