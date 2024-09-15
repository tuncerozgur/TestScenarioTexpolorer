package com.tuncerozgur.testscexplorer.repository;

import com.tuncerozgur.testscexplorer.entity.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}
