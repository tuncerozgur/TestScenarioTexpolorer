package com.tuncerozgur.testscexplorer.repository;

import com.tuncerozgur.testscexplorer.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
