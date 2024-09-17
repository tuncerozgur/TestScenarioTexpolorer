package com.tuncerozgur.testscexplorer.controller;

import com.tuncerozgur.testscexplorer.entity.Comment;
import com.tuncerozgur.testscexplorer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // GET: Tüm comment kayıtlarını getirir
    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // GET: ID'ye göre tek bir comment kaydını getirir
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return ResponseEntity.ok(comment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Yeni bir comment ekler
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    // PUT: Var olan bir comment'i günceller
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment existingComment = commentOptional.get();
            existingComment.setContent(updatedComment.getContent());
            existingComment.setUser(updatedComment.getUser());
            existingComment.setScenario(updatedComment.getScenario());
            existingComment.setProject(updatedComment.getProject());
            return ResponseEntity.ok(commentRepository.save(existingComment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Bir comment kaydını siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
