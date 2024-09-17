package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Notification;
import com.tuncerozgur.testscexplorer.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    // GET: Tüm notification kayıtlarını getirir
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // GET: ID'ye göre tek bir notification kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            return ResponseEntity.ok(notification.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir notification ekler
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationRepository.save(notification);
    }

    // PUT: Var olan bir notification kaydını günceller
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification updatedNotification) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            Notification existingNotification = notificationOptional.get();
            existingNotification.setMessage(updatedNotification.getMessage());
            existingNotification.setRead(updatedNotification.isRead());
            existingNotification.setUser(updatedNotification.getUser());
            existingNotification.setProject(updatedNotification.getProject());
            existingNotification.setScenario(updatedNotification.getScenario());
            return ResponseEntity.ok(notificationRepository.save(existingNotification));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir notification kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
