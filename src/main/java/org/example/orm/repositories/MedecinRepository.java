package org.example.orm.repositories;

import org.example.orm.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {}
