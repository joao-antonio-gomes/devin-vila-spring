package com.senai.vila.model.repository;

import com.senai.vila.model.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    Optional<Resident> findByEmail(String email);

    Optional<Resident> findById(Long id);

    Optional<Resident> findByCpf(String cpf);

    @Query(value = "SELECT * FROM residents r WHERE month(r.birth_date) = ?1", nativeQuery = true)
    Optional<List<Resident>> findByBirthMonth(Integer month);


    @Query(value = "SELECT * FROM residents r WHERE r.first_name = ?1", nativeQuery = true)
    Optional<List<Resident>> findByFirstName(String firstName);


    @Query(value = "SELECT * FROM residents r WHERE r.last_name = ?1", nativeQuery = true)
    Optional<List<Resident>> findByLastName(String lastName);


    @Query(value = "SELECT * FROM residents r WHERE r.birth_date >= ?1", nativeQuery = true)
    Optional<List<Resident>> findByAge(LocalDate dateBirth);


    @Query(value = "SELECT sum(rent) FROM residents r", nativeQuery = true)
    Double getTotalRentResidents();

    @Query(value = "SELECT * FROM residents r ORDER BY rent DESC LIMIT 1", nativeQuery = true)
    Resident getMostValuableResident();
}
