package RBPO.RBPO.services;

import RBPO.RBPO.entity.AppUser;
import RBPO.RBPO.entity.Roles;
import RBPO.RBPO.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j

public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUser getAppUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }



    public boolean testEmail (String email) {
        if (appUserRepository.findByEmail(email) != null)
            return false;
        return true;
    }

    public boolean TestPassword (String password) {

        if (password.length() < 8)
        {
            return false;
        }
        if (!password.matches(".*[0-9].*")) {
            return false;
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"))
        {
            return false;
        }
        if (!password.matches(".*[a-z].*"))
        {
            return false;
        }
        if (!password.matches(".*[A-Z].*"))
        {
            return false;
        }
        // Проверка отсутствия пробелов
        return !password.contains(" ");
    }

    @Autowired
    private BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}

    public boolean saveAppUser(AppUser appUser)
    {
        if (testEmail(appUser.getEmail())  && TestPassword(appUser.getPasswordHash())) {
            //ищем юзера в базе
            AppUser userFromDb = appUserRepository.findByEmail(appUser.getEmail());
            if (userFromDb != null) {
                return false;
            }

            appUser.setPasswordHash(encoder().encode(appUser.getPasswordHash()));
            log.info("Saving new {}", appUser);
            appUserRepository.save(appUser);

            return true;
        }
        return false;
    }

    public void deleteAppUser(Long id) {
        appUserRepository.deleteById(id);
    }

    public void updateAppUser(AppUser appUser) {
        log.info("Updating new {}", appUser);
        appUserRepository.save(appUser);
    }
}
