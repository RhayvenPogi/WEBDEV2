package com.rhayven.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
