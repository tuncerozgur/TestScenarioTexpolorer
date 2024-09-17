package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Configuration;
import com.tuncerozgur.testscexplorer.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {

    @Autowired
    private ConfigurationRepository configurationRepository;

    // GET: Tüm configuration kayıtlarını getirir
    @GetMapping
    public List<Configuration> getAllConfigurations() {
        return configurationRepository.findAll();
    }

    // GET: ID'ye göre tek bir configuration kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Configuration> getConfigurationById(@PathVariable Long id) {
        Optional<Configuration> configuration = configurationRepository.findById(id);
        if (configuration.isPresent()) {
            return ResponseEntity.ok(configuration.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir configuration ekler
    @PostMapping
    public Configuration createConfiguration(@RequestBody Configuration configuration) {
        return configurationRepository.save(configuration);
    }

    // PUT: Var olan bir configuration'ı günceller
    @PutMapping("/{id}")
    public ResponseEntity<Configuration> updateConfiguration(@PathVariable Long id, @RequestBody Configuration updatedConfiguration) {
        Optional<Configuration> configurationOptional = configurationRepository.findById(id);
        if (configurationOptional.isPresent()) {
            Configuration existingConfiguration = configurationOptional.get();
            existingConfiguration.setKey(updatedConfiguration.getKey());
            existingConfiguration.setValue(updatedConfiguration.getValue());
            return ResponseEntity.ok(configurationRepository.save(existingConfiguration));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir configuration kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfiguration(@PathVariable Long id) {
        if (configurationRepository.existsById(id)) {
            configurationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
