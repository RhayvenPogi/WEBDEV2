package com.rhayven.prelim.entity;

import jakarta.persistence.*;

/**
 * Entity representing an employee.
 */
@Entity
@Table(
        name = "employee",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class Employee {

    /** Employee ID (auto-generated). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Employee name. */
    private String name;

    /** Employee email (unique). */
    @Column(unique = true)
    private String email;

    /** @return employee ID */
    public int getId() {
        return id;
    }

    /** @param id employee ID */
    public void setId(int id) {
        this.id = id;
    }

    /** @return employee name */
    public String getName() {
        return name;
    }

    /** @param name employee name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return employee email */
    public String getEmail() {
        return email;
    }

    /** @param email employee email */
    public void setEmail(String email) {
        this.email = email;
    }
}
