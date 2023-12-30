package RBPO.RBPO.services;

import RBPO.RBPO.entity.MedicalRecords;
import RBPO.RBPO.entity.Comment;
import RBPO.RBPO.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    public List<Comment> listComments(MedicalRecords medicalRecords) {
        if (medicalRecords != null) return commentRepository.findByMedicalRecords(medicalRecords);
        return null;
    }
    public void saveComment(Comment comment) {
        log.info("Saving new {}", comment);
        commentRepository.save(comment);
    }
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

}
