package com.tuncerozgur.testscexplorer.repository;

import com.tuncerozgur.testscexplorer.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
