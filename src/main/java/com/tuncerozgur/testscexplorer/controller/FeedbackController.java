package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Feedback;
import com.tuncerozgur.testscexplorer.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // GET: Tüm feedback kayıtlarını getirir
    @GetMapping
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // GET: ID'ye göre tek bir feedback kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        if (feedback.isPresent()) {
            return ResponseEntity.ok(feedback.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir feedback ekler
    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // PUT: Var olan bir feedback kaydını günceller
    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback updatedFeedback) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isPresent()) {
            Feedback existingFeedback = feedbackOptional.get();
            existingFeedback.setFeedbackText(updatedFeedback.getFeedbackText());
            existingFeedback.setRating(updatedFeedback.getRating());
            return ResponseEntity.ok(feedbackRepository.save(existingFeedback));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir feedback kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
