package com.senai.vila.model.repository;

import com.senai.vila.model.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

     Optional<Resident> findByEmail(String email);
     Optional<Resident> findById(Long id);
}
