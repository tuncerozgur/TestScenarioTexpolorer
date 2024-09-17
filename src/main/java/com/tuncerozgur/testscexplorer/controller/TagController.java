package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Tag;
import com.tuncerozgur.testscexplorer.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    // GET: Tüm tag kayıtlarını getirir
    @GetMapping
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    // GET: ID'ye göre tek bir tag kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            return ResponseEntity.ok(tag.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir tag ekler
    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagRepository.save(tag);
    }

    // PUT: Var olan bir tag kaydını günceller
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag updatedTag) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isPresent()) {
            Tag existingTag = tagOptional.get();
            existingTag.setName(updatedTag.getName());
            existingTag.setScenarios(updatedTag.getScenarios());
            existingTag.setProjects(updatedTag.getProjects());
            return ResponseEntity.ok(tagRepository.save(existingTag));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir tag kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
