package com.senai.vila.model.repository;

import com.senai.vila.model.entity.Habitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitanteRepository extends JpaRepository<Habitante, Long> {

     Optional<Habitante> findByEmail(String email);
     Optional<Habitante> findById(Long id);
}
