package com.tuncerozgur.testscexplorer.repository;

import com.tuncerozgur.testscexplorer.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
