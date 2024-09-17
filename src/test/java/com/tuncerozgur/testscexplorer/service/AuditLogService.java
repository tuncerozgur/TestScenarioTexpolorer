package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.AuditLog;
import com.tuncerozgur.testscexplorer.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public AuditLog getAuditLog(Long id) {
        return auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AuditLog not found"));
    }

    public AuditLog createAuditLog(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    public AuditLog updateAuditLog(Long id, AuditLog auditLog) {
        if (!auditLogRepository.existsById(id)) {
            throw new RuntimeException("AuditLog not found");
        }
        auditLog.setId(id);
        return auditLogRepository.save(auditLog);
    }

    public void deleteAuditLog(Long id) {
        if (!auditLogRepository.existsById(id)) {
            throw new RuntimeException("AuditLog not found");
        }
        auditLogRepository.deleteById(id);
    }
}
