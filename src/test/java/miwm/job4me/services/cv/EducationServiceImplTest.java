package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Education;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.cv.EducationRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.cv.EducationValidator;
import miwm.job4me.web.mappers.cv.EducationMapper;
import miwm.job4me.web.model.cv.EducationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EducationServiceImplTest {
    @Mock
    private EducationRepository educationRepository;
    @Mock
    private EducationMapper educationMapper;
    @Mock
    private EducationValidator educationValidator;
    @Mock
    private IdValidator idValidator;
    @InjectMocks
    private EducationServiceImpl educationServiceImpl;

    private final String ENTITY_NAME = "Education";
    private final int MAX_DESCRIPTION_LENGTH = 100;

    private Employee employee;
    private Education education1;
    private Education education2;
    private EducationDto educationDto1;
    private EducationDto educationDto2;

    @BeforeEach
    public void setUp() {
        employee = Employee
                .builder()
                .id(1L)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("unitTest@gmail.com")
                .password("password")
                .build();

        education1 = Education
                .builder()
                .id(1L)
                .description("description1")
                .employee(null)
                .build();

        educationDto1 = new EducationDto();
        educationDto1.setId(education1.getId());
        educationDto1.setDescription(education1.getDescription());
        educationDto1.setEmployeeId(employee.getId());

        education2 = Education
                .builder()
                .id(2L)
                .description("description2")
                .employee(employee)
                .build();

        educationDto2 = new EducationDto();
        educationDto2.setId(education2.getId());
        educationDto2.setDescription(education2.getDescription());
        educationDto2.setEmployeeId(employee.getId());
    }

    @Test
    @DisplayName("Test findAll - return all Education objects")
    public void testFindAll() {
        when(educationRepository.findAll()).thenReturn(List.of(education1, education2));
        when(educationMapper.toDto(education1)).thenReturn(educationDto1);
        when(educationMapper.toDto(education2)).thenReturn(educationDto2);

        Set<EducationDto> result = educationServiceImpl.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(educationDto1));
        assertTrue(result.contains(educationDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set")
    public void testFindAllEmpty() {
        when(educationRepository.findAll()).thenReturn(List.of());

        Set<EducationDto> result = educationServiceImpl.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Education object")
    public void testFindById() {
        when(educationRepository.findById(education1.getId())).thenReturn(java.util.Optional.of(education1));
        when(educationMapper.toDto(education1)).thenReturn(educationDto1);

        EducationDto result = educationServiceImpl.findById(education1.getId());

        assertEquals(educationDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException")
    public void testFindByIdNullId() {
        when(educationRepository.findById(education1.getId())).thenReturn(java.util.Optional.empty());

        try {
            educationServiceImpl.findById(education1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound("Education", education1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findById - id is null")
    public void testFindByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            educationServiceImpl.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return Education object when it is valid")
    public void testSaveValid() {
        when(educationRepository.save(education1)).thenReturn(education1);
        when(educationMapper.toDto(education1)).thenReturn(educationDto1);
        Mockito.doNothing().when(educationValidator).validate(education1);

        EducationDto result = educationServiceImpl.save(education1);

        assertEquals(educationDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Education object is null")
    public void testSaveThrowExceptionNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(educationValidator).validate(null);

        try {
            educationServiceImpl.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException, description too long")
    public void testSaveThrowExceptionDescriptionTooLong() {
        education1.setDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(educationValidator).validate(education1);

        try {
            educationServiceImpl.save(education1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Education object exists")
    public void testDeleteByIdExists() {
        when(educationRepository.findById(education1.getId())).thenReturn(java.util.Optional.of(education1));
        Mockito.doNothing().when(idValidator).validateLongId(education1.getId(), ENTITY_NAME);
        Mockito.doNothing().when(educationRepository).deleteById(education1.getId());

        assertDoesNotThrow(() -> educationServiceImpl.deleteById(education1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Education object does not exist")
    public void testDeleteByIdDoesNotExist() {
        when(educationRepository.findById(education1.getId())).thenReturn(java.util.Optional.empty());
        Mockito.doNothing().when(idValidator).validateLongId(education1.getId(), ENTITY_NAME);

        try {
            educationServiceImpl.deleteById(education1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, education1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - id is null")
    public void testDeleteByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            educationServiceImpl.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }
}
