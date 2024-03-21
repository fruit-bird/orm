package org.example.orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.example.orm.repositories.*;
import org.example.orm.entities.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class OrmApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private RendezVousRepository rendezVousRepository;
    @Autowired
    private ConsultationRepository consultationRepository;


    public static void main(String[] args) {
        SpringApplication.run(OrmApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> list = Arrays.asList("House", "Wilson", "Joe");
        for (String doctor : list) {
            Medecin medecin = new Medecin();
            medecin.setName(doctor);
            medecin.setEmail(doctor + "@houseMD.com");
            medecin.setSpecialization(Math.random() < 0.5 ? "Cardiologist" : "GP");
            medecinRepository.save(medecin);
        }

        // Add Patients
        list = Arrays.asList("Theo", "Edward", "Mariana");
        for(String name : list) {
            Patient patient = new Patient();
            patient.setName(name);
            patient.setSick(Math.random() > 0.5);
            patient.setBirthDay(new Date(100 + name.length(), Calendar.FEBRUARY, 13));
            patientRepository.save(patient);
        }

        for (int i = 0; i < 3; i++){
            RendezVous rendezVous = new RendezVous();
            rendezVous.setCancelled(Math.random() < 0.5);
            rendezVous.setDate(new Date(124, Calendar.NOVEMBER, 20 + (int) i));
            rendezVous.setPatient(patientRepository.findById((long) i).get());
            rendezVous.setMedecin(medecinRepository.findById((long) i).get());
            rendezVousRepository.save(rendezVous);
        }

        // Add Consultation

        for (int i = 0; i < 3; i++) {
            Consultation consultation = new Consultation();
            consultation.setDateConsultation(new Date(124, Calendar.NOVEMBER, 20 + i));
            consultation.setRapport(Math.random() < 0.5 ? "Need To Run Tests" : "Perfectly normal");
            consultation.setRendezVous(rendezVousRepository.findById((long) i).get());
            consultationRepository.save(consultation);
        }
    }
}
