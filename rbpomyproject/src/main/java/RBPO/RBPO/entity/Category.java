package RBPO.RBPO.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category", uniqueConstraints = @UniqueConstraint(columnNames={"name"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="category", fetch=FetchType.EAGER)
    private List<MedicalRecords> medicalRecordss;



    //привязка статей к категории (добавить в medicalRecordsController)
    public void addmedicalRecordsToCategory(MedicalRecords medicalRecords) {

        medicalRecordss.add(medicalRecords);

    }

    //реализовать метод toString  @Override
}
