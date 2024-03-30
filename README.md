## Introduction
Toute application moderne est complexe et nécessite une architecture logicielle bien pensée pour être maintenable et évolutive. La logique métier de l'application doit être le focus plutôt que la logique de l'implémentation.

## Énoncé
Ce TP consiste à mettre en œuvre une application de gestion d'un hopital. On utilisera des libraries tierces pour la gestion de la persistance des données et pour l'abstraction du code d'implémentation. On appliquera le pattern MVC. On utilisera:
- Hibernate pour la gestion de la persistance des données
- Spring Data JPA pour l'abstraction du code d'implémentation
- JPA pour la gestion des entités
- H2 Database pour la base de données
- Spring MVC pour l'implémentation du pattern MVC
- Thymeleaf pour la gestion des vues

## Code Source
### Entités
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date birthDay;
    private String name;
    private boolean sick;
    @OneToMany (mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;
}
```

### Repositories
```java
public interface PatientRepository extends JpaRepository<Consultation, Long> {}
```

### Controlleurs
```java
@Controller
@AllArgsConstructor
public class PatientController {
    private final PatientRepository patientRepository;
}
```
```java
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
```

```java
@GetMapping("/patients/delete")
public String deletePatient(
        @RequestParam @NotNull Long id,
        @RequestParam(name = "p", defaultValue = "0") int page,
        @RequestParam(name = "kw", defaultValue = "") String keyword
) {
    patientRepository.deleteById(id);
    return "redirect:/patients?page=" + page + "&kw=" + keyword;
}
```

### Vues
#### Barre de Recherche
```html
<form method="get" th:action="@{patients}">
    <label>Keyword</label>
    <label>
        <input type="text" name="kw" th:value="${keyword}">
    </label>
    <button type="submit" class="btn btn-info">
        <i class="bi bi-search"></i>
    </button>
</form>
```

#### Affichage des Patients
```html
<table class="table table-striped">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>BirthDay</th>
            <th>Sick</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="patient : ${patientPage.content}">
            <td th:text="${patient.id}"></td>
            <td th:text="${patient.name}"></td>
            <td th:text="${patient.birthDay}"></td>
            <td th:text="${patient.sick}"></td>
            <td>
                <a th:href="@{/patients/delete(id=${patient.id}, p=${currentPage}, kw=${keyword})}" class="btn btn-danger">
                    <i class="bi bi-trash"></i>
                </a>
            </td>
        </tr>
    </tbody>
</table>
```

#### Pagination
```html
<ul class="nav nav-pills">
    <li th:each="value,item:${pages}">
        <a th:class="${(currentPage == item.index) ? 'btn btn-info ms-1' : 'btn btn-outline-info ms-1'}"
           th:href="@{/patients(p=${item.index}, kw=${keyword})}"
           th:text="${item.index + 1}"></a>
    </li>
</ul>
```
