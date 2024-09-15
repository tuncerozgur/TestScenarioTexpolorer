package com.tuncerozgur.testscexplorer.repository;

import com.tuncerozgur.testscexplorer.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
