package com.example.sandbox.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = Employee.TABLE_NAME)
public class Employee {
    public static final String TABLE_NAME = "employees";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @ToString.Exclude
    private Department department;

    @ManyToOne
    @ToString.Exclude
    private Organization organization;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private EmployeeDetail employeeDetails;

    public void setEmployeeDetails(EmployeeDetail employeeDetail){
        this.employeeDetails = employeeDetail;
        employeeDetail.setEmployee(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee )) return false;
        return id != null && id.equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
