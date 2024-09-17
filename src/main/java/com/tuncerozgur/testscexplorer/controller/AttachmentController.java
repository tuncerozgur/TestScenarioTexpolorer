package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Attachment;
import com.tuncerozgur.testscexplorer.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentRepository attachmentRepository;

    // GET: Tüm attachment kayıtlarını getirir
    @GetMapping
    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    // GET: ID'ye göre tek bir attachment kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getAttachmentById(@PathVariable Long id) {
        Optional<Attachment> attachment = attachmentRepository.findById(id);
        if (attachment.isPresent()) {
            return ResponseEntity.ok(attachment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir attachment ekler
    @PostMapping
    public Attachment createAttachment(@RequestBody Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    // PUT: Var olan bir attachment'i günceller
    @PutMapping("/{id}")
    public ResponseEntity<Attachment> updateAttachment(@PathVariable Long id, @RequestBody Attachment updatedAttachment) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        if (attachmentOptional.isPresent()) {
            Attachment existingAttachment = attachmentOptional.get();
            existingAttachment.setFileName(updatedAttachment.getFileName());
            existingAttachment.setFileType(updatedAttachment.getFileType());
            existingAttachment.setData(updatedAttachment.getData());
            existingAttachment.setScenario(updatedAttachment.getScenario());
            existingAttachment.setComment(updatedAttachment.getComment());
            return ResponseEntity.ok(attachmentRepository.save(existingAttachment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir attachment kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        if (attachmentRepository.existsById(id)) {
            attachmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
