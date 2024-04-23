package org.example.orm.web;

import jakarta.validation.Valid;
import org.example.orm.entities.Patient;
import org.example.orm.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "redirect:/patients";
    }

    @GetMapping("/user/patients")
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

    @GetMapping("admin/patients/delete")
    public String deletePatient(
            @RequestParam @NotNull Long id,
            @RequestParam(name = "p", defaultValue = "0") int page,
            @RequestParam(name = "kw", defaultValue = "") String keyword
    ) {
        patientRepository.deleteById(id);
        return "redirect:/user/patients?p=" + page + "&kw=" + keyword;
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    @PostMapping("/admin/savePatient")
    public String savePatient(
            Model model,
            @Valid Patient patient,
            BindingResult bindingResult,
            @RequestParam(name = "p", defaultValue = "0") int page,
            @RequestParam(name = "kw", defaultValue = "") String keyword
    ) {
        if (bindingResult.hasErrors()) {
            return "formPatients";
        }

        patientRepository.save(patient);
        return "redirect:/user/patients?p=" + page + "&kw=" + keyword;
    }

    @GetMapping("/admin/editPatient")
    public String editPatient(
            Model model,
            @RequestParam @NotNull Long id,
            @RequestParam(name = "p", defaultValue = "0") int page,
            @RequestParam(name = "kw", defaultValue = "") String keyword
    ) throws RuntimeException {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            throw new RuntimeException("Patient with ID " + id + " not found");
        }

        model.addAttribute("patient", patient);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "editPatient";
    }
}
