package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Scenario;
import com.tuncerozgur.testscexplorer.repository.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/scenarios")
public class ScenarioController {

    @Autowired
    private ScenarioRepository scenarioRepository;

    // GET: Tüm senaryo kayıtlarını getirir
    @GetMapping
    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    // GET: ID'ye göre tek bir senaryo kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Scenario> getScenarioById(@PathVariable Long id) {
        Optional<Scenario> scenario = scenarioRepository.findById(id);
        if (scenario.isPresent()) {
            return ResponseEntity.ok(scenario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir senaryo ekler
    @PostMapping
    public Scenario createScenario(@RequestBody Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    // PUT: Var olan bir senaryo kaydını günceller
    @PutMapping("/{id}")
    public ResponseEntity<Scenario> updateScenario(@PathVariable Long id, @RequestBody Scenario updatedScenario) {
        Optional<Scenario> scenarioOptional = scenarioRepository.findById(id);
        if (scenarioOptional.isPresent()) {
            Scenario existingScenario = scenarioOptional.get();
            existingScenario.setName(updatedScenario.getName());
            existingScenario.setDescription(updatedScenario.getDescription());
            existingScenario.setProject(updatedScenario.getProject());
            existingScenario.setTags(updatedScenario.getTags());
            return ResponseEntity.ok(scenarioRepository.save(existingScenario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir senaryo kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScenario(@PathVariable Long id) {
        if (scenarioRepository.existsById(id)) {
            scenarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
