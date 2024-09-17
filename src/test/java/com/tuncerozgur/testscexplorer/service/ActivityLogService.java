package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.ActivityLog;
import com.tuncerozgur.testscexplorer.repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    @Autowired
    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public ActivityLog getActivityLog(Long id) {
        return activityLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ActivityLog not found"));
    }

    public ActivityLog createActivityLog(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    public ActivityLog updateActivityLog(Long id, ActivityLog activityLog) {
        if (!activityLogRepository.existsById(id)) {
            throw new RuntimeException("ActivityLog not found");
        }
        activityLog.setId(id);
        return activityLogRepository.save(activityLog);
    }

    public Optional<ActivityLog> findById(Long id) {
        return activityLogRepository.findById(id);
    }

    public ActivityLog save(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    public void deleteActivityLog(Long id) {
        if (!activityLogRepository.existsById(id)) {
            throw new RuntimeException("ActivityLog not found");
        }
        activityLogRepository.deleteById(id);
    }
}
