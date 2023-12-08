package miwm.job4me.services.users;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.users.Employer;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.users.EmployerMapper;
import miwm.job4me.web.model.users.EmployerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployerServiceImplTest {
    @Mock
    private UserAuthenticationService userAuthenticationService;
    @Mock
    private EmployerRepository employerRepository;
    @Mock
    private IdValidator idValidator;
    @Mock
    private EmployerMapper employerMapper;
    @InjectMocks
    private EmployerServiceImpl employerService;

    private final String ENTITY_NAME = "Employer";
    private final Long ID = 1L;
    private Employer employer;
    private EmployerDto employerDto;

    @BeforeEach
    public void setUp() {
        employer = Employer
                .builder()
                .id(ID)
                .companyName("TestCompany")
                .email("company@gmail.com")
                .password("password")
                .userRole(new SimpleGrantedAuthority("ROLE_EMPLOYER"))
                .description("Test description")
                .displayDescription("Short test description")
                .contactEmail("test@gmail.com")
                .telephone("123456789")
                .address("ul. Testowa 5, 50-123 Testowo")
                .photo("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.creativefabrica.com%2Fpl%2Fproduct%2Fperson-icon-13%2F&psig=AOvVaw2D_r8bNfGFAUAv6AI0QXjD&ust=1701606266910000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOi9prrf8IIDFQAAAAAdAAAAABAE")
                .build();

        employerDto = new EmployerDto();
        employerDto.setId(employer.getId());
        employerDto.setCompanyName(employer.getCompanyName());
        employerDto.setEmail(employer.getContactEmail());
        employerDto.setTelephone(employer.getTelephone());
        employerDto.setDescription(employer.getDescription());
        employerDto.setDisplayDescription(employer.getDisplayDescription());
        employerDto.setAddress(employer.getAddress());
        employerDto.setPhoto(employer.getPhoto());
    }

    @Test
    @DisplayName("Validate if employer id not null and employer exists in database")
    public void validateIfEmployerExistsByIdSuccess() {
        //given
        doNothing().when(idValidator).validateLongId(ID, ENTITY_NAME);
        //when
        when(employerRepository.existsById(ID)).thenReturn(true);
        //then
        assertTrue(employerService.existsById(ID));
    }

    @Test
    @DisplayName("Validate error when employer id is null")
    public void validateEmployerExistsByIdNullIdFailed() {
        //given
        Long id = null;
        String message = ExceptionMessages.idCannotBeNull(ENTITY_NAME);
        //when
        doThrow(new InvalidArgumentException(message)).when(idValidator).validateLongId(id, ENTITY_NAME);
        try {
            employerService.existsById(id);
            fail();
        } catch (InvalidArgumentException e) {
            //then
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate error when employer does not exist in database")
    public void validateEmployerExistsByIdEmployerDoesNotExist() {
        //given
        doNothing().when(idValidator).validateLongId(ID, ENTITY_NAME);
        //when
        when(employerRepository.existsById(ID)).thenReturn(false);
        //then
        assertFalse(employerService.existsById(ID));
    }

    @Test
    @DisplayName("Validate if employer id not null and employer exists in database")
    void findEmployerById() {
        //given
        doNothing().when(idValidator).validateLongId(ID, ENTITY_NAME);
        //when
        when(employerRepository.findById(ID)).thenReturn(Optional.of(employer));
        //then
        assertNotNull(employerService.findById(ID));
        verify(employerRepository).findById(anyLong());
        verify(employerRepository, never()).findAll();
    }

    @Test
    @DisplayName("Validate error when employer id is null")
    void findEmployerByIdNullIdFailed() {
        //given
        Long id = null;
        String message = ExceptionMessages.idCannotBeNull(ENTITY_NAME);
        //when
        doThrow(new InvalidArgumentException(message)).when(idValidator).validateLongId(id, ENTITY_NAME);
        try {
            employerService.findEmployerById(id);
            fail();
        } catch (InvalidArgumentException e) {
            //then
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate error when employer does not exist in database")
    public void findEmployerByIdEmployerDoesNotExist() {
        //given
        String message = ExceptionMessages.elementNotFound(ENTITY_NAME, ID);
        doNothing().when(idValidator).validateLongId(ID, ENTITY_NAME);
        //when
        when(employerRepository.findById(ID)).thenReturn(Optional.empty());
        try {
            employerService.findEmployerById(ID);
            fail();
        } catch (NoSuchElementFoundException e) {
            //then
            assertEquals(message, e.getMessage());
        }
    }

}
