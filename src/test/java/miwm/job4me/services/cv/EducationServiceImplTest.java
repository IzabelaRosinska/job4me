package miwm.job4me.services.cv;

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
    private final String entityName = "Education";
    private final int maxDescriptionLength = 100;
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
        } catch (Exception e) {
            assertEquals(ExceptionMessages.elementNotFound("Education", education1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findById - id is null")
    public void testFindByIdNull() {
        Mockito.doThrow(new IllegalArgumentException(ExceptionMessages.idCannotBeNull(entityName))).when(idValidator).validateLongId(null, entityName);

        try {
            educationServiceImpl.findById(null);
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.idCannotBeNull(entityName), e.getMessage());
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
    @DisplayName("Test save - throw IllegalArgumentException when Education object is null")
    public void testSaveThrowExceptionNull() {
        Mockito.doThrow(new IllegalArgumentException(ExceptionMessages.nullArgument(entityName))).when(educationValidator).validate(null);

        try {
            educationServiceImpl.save(null);
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.nullArgument(entityName), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw IllegalArgumentException, description too long")
    public void testSaveThrowExceptionDescriptionTooLong() {
        education1.setDescription("a".repeat(maxDescriptionLength + 1));
        Mockito.doThrow(new IllegalArgumentException(ExceptionMessages.textTooLong(entityName, "description", maxDescriptionLength))).when(educationValidator).validate(education1);

        try {
            educationServiceImpl.save(education1);
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.textTooLong(entityName, "description", maxDescriptionLength), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Education object exists")
    public void testDeleteByIdExists() {
        when(educationRepository.findById(education1.getId())).thenReturn(java.util.Optional.of(education1));
        Mockito.doNothing().when(idValidator).validateLongId(education1.getId(), entityName);
        Mockito.doNothing().when(educationRepository).deleteById(education1.getId());

        assertDoesNotThrow(() -> educationServiceImpl.deleteById(education1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Education object does not exist")
    public void testDeleteByIdDoesNotExist() {
        when(educationRepository.findById(education1.getId())).thenReturn(java.util.Optional.empty());
        Mockito.doNothing().when(idValidator).validateLongId(education1.getId(), entityName);

        try {
            educationServiceImpl.deleteById(education1.getId());
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.elementNotFound(entityName, education1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - id is null")
    public void testDeleteByIdNull() {
        Mockito.doThrow(new IllegalArgumentException(ExceptionMessages.idCannotBeNull(entityName))).when(idValidator).validateLongId(null, entityName);

        try {
            educationServiceImpl.deleteById(null);
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.idCannotBeNull(entityName), e.getMessage());
        }
    }

}