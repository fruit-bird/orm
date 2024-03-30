package org.example.orm.web;

import org.example.orm.entities.Patient;
import org.example.orm.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private final PatientRepository patientRepository;

//    dependency injection using constructor rather than field injection is recommended
//    though this is done using the @AllArgsConstructor annotation from Lombok
//    public PatientController(PatientRepository patientRepository) {
//        this.patientRepository = patientRepository;
//    }

    @GetMapping("/")
    public String test() {
        return "hello";
    }

    @GetMapping("/patients")
    public String showPatients(
            Model model,
            @RequestParam(name = "p", defaultValue = "0") int page,
            @RequestParam(name = "s", defaultValue = "5") int size,
            @RequestParam(name = "kw", defaultValue = "") String keyword
    ) {
        Page<Patient> patientPage = patientRepository.findByNameContains(keyword, PageRequest.of(page, size));

        model.addAttribute("patientPage", patientPage);
        model.addAttribute("pages", new int[patientPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "patients";
    }

    @GetMapping("/patients/delete")
    public String deletePatient(
            @RequestParam @NotNull Long id,
            @RequestParam(name = "p", defaultValue = "0") int page,
            @RequestParam(name = "kw", defaultValue = "") String keyword
    ) {
        patientRepository.deleteById(id);
        return "redirect:/patients?page=" + page + "&kw=" + keyword;
    }
}
