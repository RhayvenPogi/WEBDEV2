package com.rhayven.prelim.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for employee data with validation.
 */
public class EmployeeDTO {

    /** Employee name (required). */
    @NotBlank(message = "This is required")
    private String name;

    /** Employee email (required, unique, valid format). */
    @NotBlank(message = "This is required")
    @Email(message = "Email format is required")
    @Column(unique = true)
    private String email;

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
