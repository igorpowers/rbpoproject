package RBPO.RBPO.controllers;

import RBPO.RBPO.entity.MedicalRecords;
import RBPO.RBPO.entity.Comment;
import RBPO.RBPO.services.AppUserService;
import RBPO.RBPO.services.MedicalRecordsService;
import RBPO.RBPO.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

import static java.lang.Long.valueOf;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final AppUserService userService;
    private final MedicalRecordsService medicalRecordsService;




    @PostMapping("/comment/create/{medicalRecords_id}")
    public ResponseEntity<String> createComment(@ModelAttribute("Comment") Comment comment,
                                                @PathVariable long medicalRecords_id, Model model) {

        if (comment.getCommentText() == null || comment.getCommentText().isEmpty()) {
            return ResponseEntity.ok("emptyComment");
        }

        Comment savingComment = new Comment();
        savingComment.setCommentText(comment.getCommentText());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // берем данные пользователя читающего статью
        String currentPrincipalName = authentication.getName();
        MedicalRecords medicalRecords = medicalRecordsService.getmedicalRecordsById(medicalRecords_id);
        savingComment.setMedicalRecords(medicalRecords);
        savingComment.setAuthor(this.userService.getAppUserByEmail(currentPrincipalName));
        commentService.saveComment(savingComment);
        medicalRecords.addCommentTomedicalRecords(savingComment);
        return ResponseEntity.ok("success");
    }



    @PostMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "redirect:/";
    }
}
