package com.senai.vila.model.repository;

import com.senai.vila.model.dto.ResidentDto;
import com.senai.vila.model.dto.RolesDto;
import com.senai.vila.model.entity.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ResidentRepositoryTest {

    @Autowired
    private ResidentRepository residentRepository;

    @Test
    public void shouldReturnOptionalListOfResidentWhenFindByBirthMonth() {
        ResidentDto residentDto = new ResidentDto("Joao", "Gomes", "09355872925", LocalDate.of(1996, 12, 4), 2000.0,
                "joao@email.com", List.of(new RolesDto("ADMIN")));
        Resident resident = new Resident(residentDto);
        residentRepository.save(resident);

        Optional<List<Resident>> residents = residentRepository.findByBirthMonth(12);
        assert residents.isPresent();
        if (residents.isPresent()) {
            assert residents.get().size() == 1;
            assert residents.get().get(0).getFirsName().equals("Joao");
        }
    }
}
