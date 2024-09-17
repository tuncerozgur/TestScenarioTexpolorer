package com.tuncerozgur.testscexplorer.service;

import com.tuncerozgur.testscexplorer.entity.Attachment;
import com.tuncerozgur.testscexplorer.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment getAttachment(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));
    }

    public Attachment createAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    public Attachment updateAttachment(Long id, Attachment updatedAttachment) {
        if (!attachmentRepository.existsById(id)) {
            throw new RuntimeException("Attachment not found");
        }
        updatedAttachment.setId(id);
        return attachmentRepository.save(updatedAttachment);
    }

    public void deleteAttachment(Long id) {
        if (!attachmentRepository.existsById(id)) {
            throw new RuntimeException("Attachment not found");
        }
        attachmentRepository.deleteById(id);
    }
}
