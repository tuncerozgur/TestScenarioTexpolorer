package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Configuration;
import com.tuncerozgur.testscexplorer.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public Configuration getConfiguration(Long id) {
        return configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration not found"));
    }

    public Configuration createConfiguration(Configuration configuration) {
        return configurationRepository.save(configuration);
    }

    public Configuration updateConfiguration(Long id, Configuration configuration) {
        if (!configurationRepository.existsById(id)) {
            throw new RuntimeException("Configuration not found");
        }
        configuration.setId(id);
        return configurationRepository.save(configuration);
    }

    public void deleteConfiguration(Long id) {
        if (!configurationRepository.existsById(id)) {
            throw new RuntimeException("Configuration not found");
        }
        configurationRepository.deleteById(id);
    }
}
