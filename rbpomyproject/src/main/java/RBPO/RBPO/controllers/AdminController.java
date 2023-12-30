package RBPO.RBPO.controllers;

import RBPO.RBPO.entity.AppUser;
import RBPO.RBPO.entity.Roles;
import RBPO.RBPO.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AppUserService userService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String getAdmin(Model model){
        //model.addAttribute("email", new String());
        return "admin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin")
    public String admin(@RequestParam("email") String email, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser admin = (AppUser) this.userService.getAppUserByEmail(authentication.getName());
        if (Objects.equals(email, "")) return "redirect:/profile";
        if (admin.getRoles().toString().equals("[ADMIN]")) {
        AppUser user = userService.getAppUserByEmail(email);
        userService.deleteAppUser(user.getId());
        user.setRoles(Collections.singleton(Roles.DOCTOR));
        userService.updateAppUser(user);
        return "admin";
        }
        return "redirect:/profile";
    }
}
