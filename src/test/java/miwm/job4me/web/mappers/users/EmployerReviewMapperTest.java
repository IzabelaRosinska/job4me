package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.users.EmployerReviewDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployerReviewMapperTest {

    private EmployerReviewMapper employerReviewMapper;

    @BeforeEach
    public void setup() {
        employerReviewMapper = new EmployerReviewMapper();
    }

    @Test
    public void testEmployerToEmployerReviewDtoWhenGivenValidEmployerAndBooleanThenReturnEmployerReviewDtoWithExpectedState() {
        // Arrange
        Employer employer = new Employer();
        employer.setId(1L);
        employer.setCompanyName("P&G");
        employer.setContactEmail("p&g@gmail.com");
        employer.setDescription("One of the biggest sellers of household products");
        employer.setDisplayDescription("Deliver household products");
        employer.setTelephone("123456789");
        employer.setPhoto("https://picsum.photos/100/100");
        employer.setAddress("Zabraniecka 20, Warszawa");
        Boolean isSaved = true;

        // Act
        EmployerReviewDto employerDto = employerReviewMapper.employerToEmployerReviewDto(employer, isSaved);

        // Assert
        Assertions.assertEquals(employer.getId(), employerDto.getId());
        Assertions.assertEquals(employer.getCompanyName(), employerDto.getCompanyName());
        Assertions.assertEquals(employer.getContactEmail(), employerDto.getEmail());
        Assertions.assertEquals(employer.getDescription(), employerDto.getDescription());
        Assertions.assertEquals(employer.getDisplayDescription(), employerDto.getDisplayDescription());
        Assertions.assertEquals(employer.getTelephone(), employerDto.getTelephone());
        Assertions.assertEquals(employer.getPhoto(), employerDto.getPhoto());
        Assertions.assertEquals(employer.getAddress(), employerDto.getAddress());
        Assertions.assertEquals(isSaved, employerDto.getIsSaved());
    }

    @Test
    public void testEmployerToEmployerReviewDtoWhenGivenNullEmployerThenThrowNullPointerException() {
        // Arrange
        Employer employer = null;
        Boolean isSaved = true;

        // Act & Assert
        Assertions.assertThrows(NullPointerException.class, () -> employerReviewMapper.employerToEmployerReviewDto(employer, isSaved));
    }
}