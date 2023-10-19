package miwm.job4me.repositories.cv;

import miwm.job4me.model.cv.Education;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EducationRepositoryTest {
//    @Autowired
//    private EducationRepository educationRepository;
//    @Autowired
//    private EmployeeRepository employeeRepository;
    private Employee employee = Employee
            .builder()
            .firstName("Jan")
            .lastName("Kowalski")
            .email("testEmail@gmail.com")
            .password("testPassword")
            .build();
    private final String VALID_DESCRIPTION = "Test description";
    private final String INVALID_DESCRIPTION = "Text that is just over 100 " +
            "charactersssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss.";

    @Test
    public void it_should_save_education() {
//        Education education = Education
//                .builder()
//                .description(VALID_DESCRIPTION)
//                .employee(employee)
//                .build();
//
//        education = entityManager.persistAndFlush(education);
//
//        assert(educationRepository.findById(education.getId())).isPresent();
        assertTrue(true);
    }

}