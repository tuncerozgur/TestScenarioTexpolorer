package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.ActivityLog;
import com.tuncerozgur.testscexplorer.repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    // GET: Tüm activity log kayıtlarını getirir
    @GetMapping
    public List<ActivityLog> getAllActivityLogs() {
        return activityLogRepository.findAll();
    }

    // GET: ID'ye göre tek bir activity log kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityLogById(@PathVariable Long id) {
        Optional<ActivityLog> activityLog = activityLogRepository.findById(id);
        if (activityLog.isPresent()) {
            return ResponseEntity.ok(activityLog.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir activity log ekler
    @PostMapping
    public ActivityLog createActivityLog(@RequestBody ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    // PUT: Var olan bir activity log'u günceller
    @PutMapping("/{id}")
    public ResponseEntity<ActivityLog> updateActivityLog(@PathVariable Long id, @RequestBody ActivityLog updatedActivityLog) {
        Optional<ActivityLog> activityLogOptional = activityLogRepository.findById(id);
        if (activityLogOptional.isPresent()) {
            ActivityLog existingActivityLog = activityLogOptional.get();
            existingActivityLog.setAction(updatedActivityLog.getAction());
            existingActivityLog.setTimestamp(updatedActivityLog.getTimestamp());
            existingActivityLog.setUser(updatedActivityLog.getUser());
            return ResponseEntity.ok(activityLogRepository.save(existingActivityLog));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir activity log kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityLog(@PathVariable Long id) {
        if (activityLogRepository.existsById(id)) {
            activityLogRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
