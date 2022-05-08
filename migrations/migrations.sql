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

    insert into organizations (code, "name")
    values('ORG-1', 'ORGANIZATION-1'),
    ('ORG-2', 'ORGANIZATION-2'),
    ('ORG-3', 'ORGANIZATION-3');

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

    insert into departments (organization_id, "name")
    values(1, 'DEPT-1'),
    (2, 'DEPT-2'),
    (3, 'DEPT-3'),
    (1, 'DEPT-4'),
    (2, 'DEPT-5'),
    (3, 'DEPT-6'),
    (1, 'DEPT-7'),
    (2, 'DEPT-8'),
    (3, 'DEPT-9');

--EMPLOYEES
    create table employees(
        id bigserial,
        organization_id bigint not null,
        "name" varchar(255) not null,
        department_id bigint not null,
        constraint pk_employees primary key(id, organization_id),
        constraint fk_employee_department foreign key (department_id, organization_id) references departments(id, organization_id)
    ) partition by hash(organization_id);
    CREATE TABLE employees_0 PARTITION OF employees FOR VALUES WITH (modulus 3, remainder 0);
    CREATE TABLE employees_1 PARTITION OF employees FOR VALUES WITH (modulus 3, remainder 1);
    CREATE TABLE employees_2 PARTITION OF employees FOR VALUES WITH (modulus 3, remainder 2);

    insert into employees (organization_id, "name", department_id)
    values(1, 'EMP-1', 1),
    (1, 'EMP-2', 4),
    (1, 'EMP-3', 7),
    (1, 'EMP-4', 1),
    (1, 'EMP-5', 4),
    (1, 'EMP-6', 7),
    (1, 'EMP-7', 1),
    (1, 'EMP-8', 4),
    (1, 'EMP-9', 7),
    (1, 'EMP-10', 1),
    (1, 'EMP-11', 4),
    (1, 'EMP-12', 7),
    (1, 'EMP-13', 1),
    (1, 'EMP-14', 4),
    (1, 'EMP-15', 7),
    (2, 'EMP-16', 2),
    (2, 'EMP-17', 5),
    (2, 'EMP-18', 8),
    (2, 'EMP-19', 2),
    (2, 'EMP-20', 5),
    (2, 'EMP-21', 8),
    (2, 'EMP-22', 2),
    (2, 'EMP-23', 5),
    (2, 'EMP-24', 8),
    (3, 'EMP-25', 3),
    (3, 'EMP-26', 6),
    (3, 'EMP-27', 9),
    (3, 'EMP-28', 3),
    (3, 'EMP-29', 6),
    (3, 'EMP-30', 9),
    (3, 'EMP-31', 3),
    (3, 'EMP-32', 6),
    (3, 'EMP-33', 9),
    (3, 'EMP-34', 3),
    (3, 'EMP-35', 6),
    (3, 'EMP-36', 9),
    (3, 'EMP-37', 3),
    (3, 'EMP-38', 6),
    (3, 'EMP-39', 9),
    (3, 'EMP-40', 3),
    (3, 'EMP-41', 6),
    (3, 'EMP-42', 9);
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

    insert into employee_details(organization_id, employee_id, salary, address)
    values
    (1, 1, floor(random()*(3000000)), 'address-1'),
    (1, 2, floor(random()*(3000000)), 'address-1'),
    (1, 3, floor(random()*(3000000)), 'address-1'),
    (1, 4, floor(random()*(3000000)), 'address-1'),
    (1, 5, floor(random()*(3000000)), 'address-1'),
    (1, 6, floor(random()*(3000000)), 'address-1'),
    (1, 7, floor(random()*(3000000)), 'address-1'),
    (1, 8, floor(random()*(3000000)), 'address-1'),
    (1, 9, floor(random()*(3000000)), 'address-1'),
    (1, 10, floor(random()*(3000000)), 'address-1'),
    (1, 11, floor(random()*(3000000)), 'address-1'),
    (1, 12, floor(random()*(3000000)), 'address-1'),
    (1, 13, floor(random()*(3000000)), 'address-1'),
    (1, 14, floor(random()*(3000000)), 'address-1'),
    (1, 15, floor(random()*(3000000)), 'address-2'),
    (2, 16, floor(random()*(3000000)), 'address-2'),
    (2, 17, floor(random()*(3000000)), 'address-2'),
    (2, 18, floor(random()*(3000000)), 'address-2'),
    (2, 19, floor(random()*(3000000)), 'address-2'),
    (2, 20, floor(random()*(3000000)), 'address-2'),
    (2, 21, floor(random()*(3000000)), 'address-2'),
    (2, 22, floor(random()*(3000000)), 'address-2'),
    (2, 23, floor(random()*(3000000)), 'address-2'),
    (2, 24, floor(random()*(3000000)), 'address-2'),
    (3, 25, floor(random()*(3000000)), 'address-2'),
    (3, 26, floor(random()*(3000000)), 'address-2'),
    (3, 27, floor(random()*(3000000)), 'address-2'),
    (3, 28, floor(random()*(3000000)), 'address-2'),
    (3, 29, floor(random()*(3000000)), 'address-2'),
    (3, 30, floor(random()*(3000000)), 'address-2'),
    (3, 31, floor(random()*(3000000)), 'address-2'),
    (3, 32, floor(random()*(3000000)), 'address-2'),
    (3, 33, floor(random()*(3000000)), 'address-2'),
    (3, 34, floor(random()*(3000000)), 'address-2'),
    (3, 35, floor(random()*(3000000)), 'address-2'),
    (3, 36, floor(random()*(3000000)), 'address-2'),
    (3, 37, floor(random()*(3000000)), 'address-2'),
    (3, 38, floor(random()*(3000000)), 'address-2'),
    (3, 39, floor(random()*(3000000)), 'address-2'),
    (3, 40, floor(random()*(3000000)), 'address-2'),
    (3, 41, floor(random()*(3000000)), 'address-2'),
    (3, 42, floor(random()*(3000000)), 'address-2');