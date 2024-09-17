package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.AuditLog;
import com.tuncerozgur.testscexplorer.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auditlogs")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // GET: Tüm audit log kayıtlarını getirir
    @GetMapping
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    // GET: ID'ye göre tek bir audit log kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<AuditLog> getAuditLogById(@PathVariable Long id) {
        Optional<AuditLog> auditLog = auditLogRepository.findById(id);
        if (auditLog.isPresent()) {
            return ResponseEntity.ok(auditLog.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir audit log ekler
    @PostMapping
    public AuditLog createAuditLog(@RequestBody AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    // PUT: Var olan bir audit log'u günceller
    @PutMapping("/{id}")
    public ResponseEntity<AuditLog> updateAuditLog(@PathVariable Long id, @RequestBody AuditLog updatedAuditLog) {
        Optional<AuditLog> auditLogOptional = auditLogRepository.findById(id);
        if (auditLogOptional.isPresent()) {
            AuditLog existingAuditLog = auditLogOptional.get();
            existingAuditLog.setAction(updatedAuditLog.getAction());
            existingAuditLog.setDetails(updatedAuditLog.getDetails());
            existingAuditLog.setTimestamp(updatedAuditLog.getTimestamp());
            existingAuditLog.setUser(updatedAuditLog.getUser());
            existingAuditLog.setProject(updatedAuditLog.getProject());
            return ResponseEntity.ok(auditLogRepository.save(existingAuditLog));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir audit log kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditLog(@PathVariable Long id) {
        if (auditLogRepository.existsById(id)) {
            auditLogRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
