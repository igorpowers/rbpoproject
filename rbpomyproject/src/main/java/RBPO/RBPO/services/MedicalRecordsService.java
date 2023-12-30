package RBPO.RBPO.services;

import RBPO.RBPO.entity.AppUser;
import RBPO.RBPO.entity.MedicalRecords;
import RBPO.RBPO.entity.Category;
import RBPO.RBPO.repositories.MedicalRecordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MedicalRecordsService {


    //Кирилл
    private final MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    public MedicalRecordsService(MedicalRecordsRepository medicalRecordsRepository) {
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    public List<MedicalRecords> getAllmedicalRecordss() {
        return medicalRecordsRepository.findAll();
    }

    public MedicalRecords getmedicalRecordsById(Long id) {
        return medicalRecordsRepository.findById(id).orElse(null);
    }

    public void savemedicalRecords(MedicalRecords medicalRecords) {
        medicalRecordsRepository.save(medicalRecords);
    }

    public void deletemedicalRecords(Long id) {
        medicalRecordsRepository.deleteById(id);
    }

    public List<MedicalRecords> gemedicalRecordssByCategory(Category category) {return medicalRecordsRepository.findByCategory(category);}
    public List<MedicalRecords> gemedicalRecordssByPatient(AppUser patient) {return medicalRecordsRepository.findByPatient(patient);}
}
