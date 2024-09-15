package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Scenario;
import com.tuncerozgur.testscexplorer.repository.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scenarios")
public class ScenarioController {

    @Autowired
    private ScenarioRepository scenarioRepository;

    @PostMapping("/create")
    public ResponseEntity<Scenario> createScenario(@RequestBody Scenario scenario) {
        // Senaryo nesnesini veritabanına kaydet
        Scenario savedScenario = scenarioRepository.save(scenario);

        // Kayıtlı senaryoyu döndür
        return ResponseEntity.ok(savedScenario);
    }
}
