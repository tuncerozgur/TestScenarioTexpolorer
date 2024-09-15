package com.tuncerozgur.testscexplorer.repository;

import com.tuncerozgur.testscexplorer.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}
