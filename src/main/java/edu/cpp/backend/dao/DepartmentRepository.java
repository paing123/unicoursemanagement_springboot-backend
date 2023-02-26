package edu.cpp.backend.dao;

import edu.cpp.backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
