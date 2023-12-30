package RBPO.RBPO.controllers;

import RBPO.RBPO.entity.Roles;
import RBPO.RBPO.services.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import RBPO.RBPO.entity.AppUser;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Objects;


@Controller
@AllArgsConstructor
public class AuthorizationController {
    AppUserService userService;
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        if (!(model.containsAttribute("name") || model.containsAttribute("email") || model.containsAttribute("password")))
            model.addAttribute("AppUser", new AppUser());
        return "RegistrationPage";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute("AppUser") AppUser appuser, Model model, HttpSession session,
//                                   @RequestParam(name = "role", required = false) boolean checkboxValue,
                                   HttpServletRequest request) {
        String response = request.getParameter("g-recaptcha-response");
        String errors = new String();

        if (Objects.equals(response, "")) {
            errors += "Капча обязательна!";
            model.addAttribute("errors", errors);
            return "RegistrationPage";
        }

//        if (checkboxValue)
//            appuser.setRoles(Collections.singleton(Roles.ADMIN));
//        else
          appuser.setRoles(Collections.singleton(Roles.USER));

        if (userService.saveAppUser(appuser)){

            String email = appuser.getEmail();
            String Name = appuser.getUsername();

            session.setAttribute("appuser", appuser);
            return "/login";
    }
        errors += "Невозможно создать пользователя!\n";
        String link = new String ("");
        if (!userService.testEmail(appuser.getEmail())) {
            errors += "Пользователь с такой почтой уже зарегестрирован.\n" +
                    "Используйте другую почту;\n" +
                    "Перейдите по ссылке для сброса пароля от привязанного аккаунта: ";
            link += String.format(
                    "https://localhost/reset/%s", appuser.getEmail());
        }
        if (!userService.TestPassword(appuser.getPasswordHash()))
            errors += "Введённый Вами парль недостаточно сложен.\n"+
                     "Убедитесь, что он содержит минимум:\n" +
                     "1. 2 или более цифр;" +
                     "2. 3 или более Заглавных латинских символа;\n" +
                     "3. 3 или более строчных латинских символа;\n" +
                     "4. 2 или более спец. символов;\n";
        model.addAttribute("errors", errors);
        model.addAttribute("link", link);


        return "RegistrationPage";
    }
}
