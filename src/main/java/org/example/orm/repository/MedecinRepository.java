package org.example.orm.repository;

import org.example.orm.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {}
