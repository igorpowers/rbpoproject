package RBPO.RBPO.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "medicalRecords", uniqueConstraints = @UniqueConstraint(columnNames={"record_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor

//              не работает код? Купи рабочий 👈(ﾟヮﾟ👈)

public class MedicalRecords {
    @Id
    @SequenceGenerator(name = "record_seq",
            sequenceName = "record_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq")
    @Column(name = "record_id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "text", columnDefinition = "text")
    private String text;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private AppUser author;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id_1")
    private AppUser patient;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "medicalRecords", fetch = FetchType.EAGER)
    private List<Comment> comments;

    public void addCommentTomedicalRecords(Comment comment) {
        comments.add(comment);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "medicalRecords{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", comments='" + comments + '\'' +
                ", text='" + text + '\'' +

                '}';
    }
}
