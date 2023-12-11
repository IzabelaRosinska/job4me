package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.users.EmployerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashSet;

public class EmployerMapperTest {

    private EmployerMapper employerMapper;

    @BeforeEach
    public void setUp() {
        employerMapper = new EmployerMapper();
    }

    @Test
    public void testEmployerToEmployerDtoWhenValidEmployerThenEmployerDto() {
        // Arrange
        Employer employer = Employer.builder()
                .id(1L)
                .companyName("P&G")
                .contactEmail("p&g@gmail.com")
                .description("One of the biggest sellers of household products")
                .displayDescription("Deliver household products")
                .telephone("123456789")
                .photo("https://picsum.photos/100/100")
                .address("Zabraniecka 20, Warszawa")
                .savedEmployees(new HashSet<>())
                .jobFairEmployerParticipation(new HashSet<>())
                .build();

        // Act
        EmployerDto employerDto = employerMapper.employerToEmployerDto(employer);

        // Assert
        Assertions.assertNotNull(employerDto);
        Assertions.assertEquals(employer.getId(), employerDto.getId());
        Assertions.assertEquals(employer.getCompanyName(), employerDto.getCompanyName());
        Assertions.assertEquals(employer.getContactEmail(), employerDto.getEmail());
        Assertions.assertEquals(employer.getDescription(), employerDto.getDescription());
        Assertions.assertEquals(employer.getDisplayDescription(), employerDto.getDisplayDescription());
        Assertions.assertEquals(employer.getTelephone(), employerDto.getTelephone());
        Assertions.assertEquals(employer.getPhoto(), employerDto.getPhoto());
        Assertions.assertEquals(employer.getAddress(), employerDto.getAddress());
    }

    @Test
    public void testEmployerDtoToEmployerWhenValidEmployerDtoThenEmployer() {
        // Arrange
        EmployerDto employerDto = new EmployerDto();
        employerDto.setId(1L);
        employerDto.setCompanyName("P&G");
        employerDto.setEmail("p&g@gmail.com");
        employerDto.setDescription("One of the biggest sellers of household products");
        employerDto.setDisplayDescription("Deliver household products");
        employerDto.setTelephone("123456789");
        employerDto.setPhoto("https://picsum.photos/100/100");
        employerDto.setAddress("Zabraniecka 20, Warszawa");

        Employer employer = new Employer();

        // Act
        Employer updatedEmployer = employerMapper.employerDtoToEmployer(employerDto, employer);

        // Assert
        Assertions.assertNotNull(updatedEmployer);
        Assertions.assertEquals(employerDto.getCompanyName(), updatedEmployer.getCompanyName());
        Assertions.assertEquals(employerDto.getEmail(), updatedEmployer.getContactEmail());
        Assertions.assertEquals(employerDto.getDescription(), updatedEmployer.getDescription());
        Assertions.assertEquals(employerDto.getDisplayDescription(), updatedEmployer.getDisplayDescription());
        Assertions.assertEquals(employerDto.getTelephone(), updatedEmployer.getTelephone());
        Assertions.assertEquals(employerDto.getPhoto(), updatedEmployer.getPhoto());
        Assertions.assertEquals(employerDto.getAddress(), updatedEmployer.getAddress());
    }
}
