package org.example.orm;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.example.orm.repositories.*;
import org.example.orm.entities.*;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class OrmApplication implements CommandLineRunner {
    private PatientRepository patientRepository;
    private MedecinRepository medecinRepository;
    private RendezVousRepository rendezVousRepository;
    private ConsultationRepository consultationRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrmApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null, new Date(), "Teach", false, null));
        patientRepository.save(new Patient(null, new Date(), "Edward", true, null));
        patientRepository.save(new Patient(null, new Date(), "Roger", true, null));
        patientRepository.save(new Patient(null, new Date(), "Shanks", true, null));
        patientRepository.save(new Patient(null, new Date(), "Mihawk", false, null));
        patientRepository.save(new Patient(null, new Date(), "Rayleigh", true, null));
        patientRepository.save(new Patient(null, new Date(), "Luffy", false, null));
        patientRepository.save(new Patient(null, new Date(), "Zoro", false, null));
        patientRepository.save(new Patient(null, new Date(), "Sanji", false, null));
        patientRepository.save(new Patient(null, new Date(), "Vegapunk", false, null));

//        medecinRepository.save(new Medecin(null, "Dr. House", "house@vet.com", "Vet", null));
//        medecinRepository.save(new Medecin(null, "Dr. Strange", "strange@comic.com", "Magic", null));
//        medecinRepository.save(new Medecin(null, "Dr. Who", "unknown@who.com", "Time", null));
//
//        List<Patient> patients = patientRepository.findAll();
//        List<Medecin> doctors = medecinRepository.findAll();
//
//        rendezVousRepository.save(new RendezVous(null, new Date(), false, patients.get(0), doctors.get(0), null));
//        rendezVousRepository.save(new RendezVous(null, new Date(), false, patients.get(1), doctors.get(1), null));
//        rendezVousRepository.save(new RendezVous(null, new Date(), false, patients.get(2), doctors.get(2), null));
//
//        List<RendezVous> rendezVous = rendezVousRepository.findAll();
//
//        consultationRepository.save(new Consultation(null, new Date(), "Consultation 1", rendezVous.get(0)));
//        consultationRepository.save(new Consultation(null, new Date(), "Consultation 2", rendezVous.get(1)));
//        consultationRepository.save(new Consultation(null, new Date(), "Consultation 3", rendezVous.get(2)));
    }
}
