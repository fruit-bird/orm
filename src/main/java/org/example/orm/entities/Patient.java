package org.example.orm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    @NotEmpty @Size(min = 2, max = 30)
    private String name;

    private boolean sick;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;

    public String toString() {
        return "ID: " + id + "\n" + "Name: " + name + "\n" + "Birth Day: " + birthDay + "\n" + (sick ? "Patient is sick" : "Patient is healthy");
    }
}