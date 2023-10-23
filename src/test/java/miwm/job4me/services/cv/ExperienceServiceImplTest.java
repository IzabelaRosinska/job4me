package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Experience;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.cv.ExperienceRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.cv.ExperienceValidator;
import miwm.job4me.web.mappers.cv.ExperienceMapper;
import miwm.job4me.web.model.cv.ExperienceDto;
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
class ExperienceServiceImplTest {
    @Mock
    private ExperienceRepository experienceRepository;
    @Mock
    private ExperienceMapper experienceMapper;
    @Mock
    private ExperienceValidator experienceValidator;
    @Mock
    private IdValidator idValidator;
    @InjectMocks
    private ExperienceServiceImpl experienceServiceImpl;
    private final String entityName = "Experience";
    private final int maxDescriptionLength = 255;
    private Employee employee;
    private Experience experience1;
    private Experience experience2;
    private ExperienceDto experienceDto1;
    private ExperienceDto experienceDto2;

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

        experience1 = Experience
                .builder()
                .id(1L)
                .description("description1")
                .employee(employee)
                .build();

        experienceDto1 = new ExperienceDto();
        experienceDto1.setId(experience1.getId());
        experienceDto1.setDescription(experience1.getDescription());
        experienceDto1.setEmployeeId(experience1.getEmployee().getId());

        experience2 = Experience
                .builder()
                .id(2L)
                .description("description2")
                .employee(employee)
                .build();

        experienceDto2 = new ExperienceDto();
        experienceDto2.setId(experience2.getId());
        experienceDto2.setDescription(experience2.getDescription());
        experienceDto2.setEmployeeId(experience2.getEmployee().getId());
    }

    @Test
    @DisplayName("Test findAll - return all Experience objects")
    public void testFindAll() {
        when(experienceRepository.findAll()).thenReturn(List.of(experience1, experience2));
        when(experienceMapper.toDto(experience1)).thenReturn(experienceDto1);
        when(experienceMapper.toDto(experience2)).thenReturn(experienceDto2);

        Set<ExperienceDto> result = experienceServiceImpl.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(experienceDto1));
        assertTrue(result.contains(experienceDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set")
    public void testFindAllEmpty() {
        when(experienceRepository.findAll()).thenReturn(List.of());

        Set<ExperienceDto> result = experienceServiceImpl.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Experience object")
    public void testFindById() {
        when(experienceRepository.findById(experience1.getId())).thenReturn(java.util.Optional.of(experience1));
        when(experienceMapper.toDto(experience1)).thenReturn(experienceDto1);

        ExperienceDto result = experienceServiceImpl.findById(experience1.getId());

        assertEquals(experienceDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException")
    public void testFindByIdNotExists() {
        when(experienceRepository.findById(experience1.getId())).thenReturn(java.util.Optional.empty());

        try {
            experienceServiceImpl.findById(experience1.getId());
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.elementNotFound(entityName, experience1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findById - id is null")
    public void testFindByIdNullId() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(entityName))).when(idValidator).validateLongId(null, entityName);

        try {
            experienceServiceImpl.findById(null);
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.idCannotBeNull(entityName), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - save Experience object when it is valid")
    public void testSaveValid() {
        when(experienceRepository.save(experience1)).thenReturn(experience1);
        when(experienceMapper.toDto(experience1)).thenReturn(experienceDto1);
        Mockito.doNothing().when(experienceValidator).validate(experience1);

        ExperienceDto result = experienceServiceImpl.save(experience1);

        assertEquals(experienceDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Experience object is null")
    public void testSaveNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(entityName))).when(experienceValidator).validate(null);

        try {
            experienceServiceImpl.save(null);
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.nullArgument(entityName), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Experience object is not valid")
    public void testSaveThrowExceptionDescriptionTooLong() {
        experience1.setDescription("a".repeat(maxDescriptionLength + 1));
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(entityName, "description", maxDescriptionLength))).when(experienceValidator).validate(experience1);

        try {
            experienceServiceImpl.save(experience1);
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.textTooLong(entityName, "description", maxDescriptionLength), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Education object exists")
    public void testDeleteByIdExists() {
        when(experienceRepository.findById(experience1.getId())).thenReturn(java.util.Optional.of(experience1));
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), entityName);
        Mockito.doNothing().when(experienceRepository).deleteById(experience1.getId());

        assertDoesNotThrow(() -> experienceServiceImpl.deleteById(experience1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Education object does not exist")
    public void testDeleteByIdDoesNotExist() {
        when(experienceRepository.findById(experience1.getId())).thenReturn(java.util.Optional.empty());
        Mockito.doNothing().when(idValidator).validateLongId(experience1.getId(), entityName);

        try {
            experienceServiceImpl.deleteById(experience1.getId());
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.elementNotFound(entityName, experience1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - id is null")
    public void testDeleteByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(entityName))).when(idValidator).validateLongId(null, entityName);

        try {
            experienceServiceImpl.deleteById(null);
            fail();
        } catch (Exception e) {
            assertEquals(ExceptionMessages.idCannotBeNull(entityName), e.getMessage());
        }
    }
}
