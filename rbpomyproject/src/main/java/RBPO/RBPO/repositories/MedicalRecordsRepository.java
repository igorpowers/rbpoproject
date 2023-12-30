package RBPO.RBPO.repositories;

import RBPO.RBPO.entity.AppUser;
import RBPO.RBPO.entity.MedicalRecords;
import RBPO.RBPO.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, Long> {
    List<MedicalRecords> findByTitle(String title);
    List<MedicalRecords> findByCategory(Category category);
    List<MedicalRecords> findByPatient(AppUser patient);

}
