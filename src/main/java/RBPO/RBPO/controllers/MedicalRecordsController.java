package RBPO.RBPO.controllers;

import RBPO.RBPO.entity.*;
import RBPO.RBPO.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MedicalRecordsController {
    private final AppUserService userService;

    private final MedicalRecordsService medicalRecordsService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @GetMapping("medicalRecords/{id}")
    public String detailedmedicalRecords(@PathVariable long id, Model model) {
        if (!(model.containsAttribute("Comment"))) {
            model.addAttribute("Comment", new Comment());
        }

        model.addAttribute("medicalRecords", medicalRecordsService.getmedicalRecordsById(id));
        model.addAttribute("Comments", commentService.listComments(medicalRecordsService.getmedicalRecordsById(id)));

        return "detailedmedicalRecords";
    }




    @GetMapping("medicalRecords/create")
    public  String getmedicalRecordsCreationPage(Model model) {
        if (!(model.containsAttribute("title") || model.containsAttribute("text"))) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AppUser admin = (AppUser) this.userService.getAppUserByEmail(authentication.getName());
            if (admin.getRoles().toString().equals("[DOCTOR]")) {
                model.addAttribute("medicalRecords", new MedicalRecords());
                model.addAttribute("Category", new Category());
            }
        }
        return "create";
    }

    @GetMapping("/medicalRecords/show")
    public String getmedicalRecordsShowPage(Model model){
        return "redirect:/all";
    }

    @GetMapping("/history/{patientname}")
    public String home(@PathVariable String patientname, Model model) {
        AppUser patient = (AppUser) this.userService.getAppUserByEmail(patientname);
        List<MedicalRecords> medicalRecordss = medicalRecordsService.gemedicalRecordssByPatient(patient);
        model.addAttribute("medicalRecordss", medicalRecordss);
        return "home";
    }

    @PostMapping("/medicalRecords/create")
    public String createmedicalRecords(
            @ModelAttribute("medicalRecords") MedicalRecords medicalRecords,
            @RequestParam("categoryName") String categoryName, Model model,
            @RequestParam("pName") String pName) throws IOException {
        AppUser patient = (AppUser) this.userService.getAppUserByEmail(pName);
        Category category = (Category) categoryService.getCategoryByName(categoryName);
        if (patient != null) {
            medicalRecords.setPatient(patient);
        }
        //проверяем есть ли указанная категория, если ее нет - создаем
        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            categoryService.saveCategory(category);
        }


       // String uploadImage = ImageService.uploadImage(files);


        medicalRecords.setCategory(category);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // берем данные пользователя создавшего статью
        String currentPrincipalName = authentication.getName();

        // System.out.println(currentPrincipalName);

        AppUser user = (AppUser) this.userService.getAppUserByEmail(currentPrincipalName);

        medicalRecords.setAuthor(user);


        medicalRecordsService.savemedicalRecords(medicalRecords);

        //       Никакого сегодня СТРИНГА НЕ БУДЕТ.  👈(ﾟヮﾟ👈)

        return "home";
    }


    @PostMapping("/medicalRecords/delete/{id}")
    public String deletemedicalRecords(@PathVariable Long id) {
        medicalRecordsService.deletemedicalRecords(id);
        return "redirect:/all";
    }
}
