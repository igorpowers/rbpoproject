package RBPO.RBPO.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Set;

import java.util.List;


@Getter
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AppUser {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)//возможно изза этой строки у нас проблема с айдишниками
    @SequenceGenerator(name = "user_seq",
            sequenceName = "user_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "passwordHash")
    private String passwordHash;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<MedicalRecords> medicalRecordss;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setmedicalRecordss(List<MedicalRecords> medicalRecordss) {
        this.medicalRecordss = medicalRecordss;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addmedicalRecordsToUser(MedicalRecords medicalRecords) {
        this.medicalRecordss.add(medicalRecords);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
