package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Scenario;
import com.tuncerozgur.testscexplorer.repository.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

    @Autowired
    public ScenarioService(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    public Scenario getScenario(Long id) {
        return scenarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));
    }

    public Scenario createScenario(Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    public Scenario updateScenario(Long id, Scenario scenario) {
        if (!scenarioRepository.existsById(id)) {
            throw new RuntimeException("Scenario not found");
        }
        scenario.setId(id);
        return scenarioRepository.save(scenario);
    }

    public void deleteScenario(Long id) {
        if (!scenarioRepository.existsById(id)) {
            throw new RuntimeException("Scenario not found");
        }
        scenarioRepository.deleteById(id);
    }
}
