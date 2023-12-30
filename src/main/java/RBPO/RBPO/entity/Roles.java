package RBPO.RBPO.entity;


import org.springframework.security.core.GrantedAuthority;


public enum Roles implements GrantedAuthority {
    USER, ADMIN, DOCTOR;

    @Override
    public String getAuthority() {
        return name();
    }
}