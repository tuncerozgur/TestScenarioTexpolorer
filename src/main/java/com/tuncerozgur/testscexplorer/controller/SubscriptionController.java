package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Subscription;
import com.tuncerozgur.testscexplorer.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // GET: Tüm abonelik kayıtlarını getirir
    @GetMapping
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    // GET: ID'ye göre tek bir abonelik kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if (subscription.isPresent()) {
            return ResponseEntity.ok(subscription.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir abonelik ekler
    @PostMapping
    public Subscription createSubscription(@RequestBody Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    // PUT: Var olan bir abonelik kaydını günceller
    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id, @RequestBody Subscription updatedSubscription) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(id);
        if (subscriptionOptional.isPresent()) {
            Subscription existingSubscription = subscriptionOptional.get();
            existingSubscription.setPlanName(updatedSubscription.getPlanName());
            existingSubscription.setPrice(updatedSubscription.getPrice());
            existingSubscription.setDuration(updatedSubscription.getDuration());
            existingSubscription.setUser(updatedSubscription.getUser());
            return ResponseEntity.ok(subscriptionRepository.save(existingSubscription));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir abonelik kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        if (subscriptionRepository.existsById(id)) {
            subscriptionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
