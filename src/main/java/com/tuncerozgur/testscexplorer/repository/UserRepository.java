package com.tuncerozgur.testscexplorer.repository;

import com.tuncerozgur.testscexplorer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
