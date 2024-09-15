package com.tuncerozgur.testscexplorer.repository;

import com.tuncerozgur.testscexplorer.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
