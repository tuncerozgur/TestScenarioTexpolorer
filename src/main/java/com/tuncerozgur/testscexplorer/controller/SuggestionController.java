package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Suggestion;
import com.tuncerozgur.testscexplorer.repository.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    @Autowired
    private SuggestionRepository suggestionRepository;

    // GET: Tüm öneri kayıtlarını getirir
    @GetMapping
    public List<Suggestion> getAllSuggestions() {
        return suggestionRepository.findAll();
    }

    // GET: ID'ye göre tek bir öneri kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Suggestion> getSuggestionById(@PathVariable Long id) {
        Optional<Suggestion> suggestion = suggestionRepository.findById(id);
        if (suggestion.isPresent()) {
            return ResponseEntity.ok(suggestion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir öneri ekler
    @PostMapping
    public Suggestion createSuggestion(@RequestBody Suggestion suggestion) {
        return suggestionRepository.save(suggestion);
    }

    // PUT: Var olan bir öneri kaydını günceller
    @PutMapping("/{id}")
    public ResponseEntity<Suggestion> updateSuggestion(@PathVariable Long id, @RequestBody Suggestion updatedSuggestion) {
        Optional<Suggestion> suggestionOptional = suggestionRepository.findById(id);
        if (suggestionOptional.isPresent()) {
            Suggestion existingSuggestion = suggestionOptional.get();
            existingSuggestion.setSuggestion(updatedSuggestion.getSuggestion());
            existingSuggestion.setScenario(updatedSuggestion.getScenario());
            return ResponseEntity.ok(suggestionRepository.save(existingSuggestion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir öneri kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuggestion(@PathVariable Long id) {
        if (suggestionRepository.existsById(id)) {
            suggestionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
