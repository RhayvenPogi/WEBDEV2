package com.rhayven.prelim.repository;

import com.rhayven.prelim.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for {@link Employee} entities.
 */
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    /**
     * Finds an employee by email.
     *
     * @param email employee email
     * @return optional containing the employee if found
     */
    Optional<Employee> findByEmail(String email);
}
