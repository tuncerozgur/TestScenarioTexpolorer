package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Project;
import com.tuncerozgur.testscexplorer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    // GET: Tüm project kayıtlarını getirir
    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // GET: ID'ye göre tek bir project kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir project ekler
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectRepository.save(project);
    }

    // PUT: Var olan bir project kaydını günceller
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project existingProject = projectOptional.get();
            existingProject.setName(updatedProject.getName());
            existingProject.setDescription(updatedProject.getDescription());
            existingProject.setUser(updatedProject.getUser());
            existingProject.setScenarios(updatedProject.getScenarios());
            existingProject.setNotifications(updatedProject.getNotifications());
            existingProject.setTags(updatedProject.getTags());
            return ResponseEntity.ok(projectRepository.save(existingProject));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir project kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
