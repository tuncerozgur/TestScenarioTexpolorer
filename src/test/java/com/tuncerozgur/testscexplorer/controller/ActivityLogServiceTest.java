package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.ActivityLog;
import com.tuncerozgur.testscexplorer.entity.User;
import com.tuncerozgur.testscexplorer.repository.ActivityLogRepository;
import com.tuncerozgur.testscexplorer.service.ActivityLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ActivityLogServiceTest {

    @Autowired
    private ActivityLogService activityLogService;

    @MockBean
    private ActivityLogRepository activityLogRepository;

    @MockBean
    private User mockUser;  // Mock User nesnesi

    @BeforeEach
    void setUp() {
        // Initialize or reset mocks if necessary
    }

    @Test
    void testFindById() {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setId(1L);
        activityLog.setAction("Test Action");
        activityLog.setTimestamp(LocalDateTime.now());
        activityLog.setUser(mockUser);  // Mock User ile ilişkilendir

        when(activityLogRepository.findById(1L)).thenReturn(Optional.of(activityLog));

        Optional<ActivityLog> foundLog = activityLogService.findById(1L);
        assertEquals("Test Action", foundLog.get().getAction());
        assertEquals(mockUser, foundLog.get().getUser());  // User kontrolü
    }

    @Test
    void testSave() {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setAction("New Testing1");
        activityLog.setTimestamp(LocalDateTime.now());
        activityLog.setUser(mockUser);  // Mock User ile ilişkilendir

        when(activityLogRepository.save(any(ActivityLog.class))).thenReturn(activityLog);

        ActivityLog savedLog = activityLogService.save(activityLog);
        assertEquals("New Testing1", savedLog.getAction());
        assertEquals(mockUser, savedLog.getUser());  // User kontrolü
    }
}
