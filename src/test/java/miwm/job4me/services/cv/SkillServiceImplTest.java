package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.cv.SkillRepository;
import miwm.job4me.validators.entity.cv.SkillValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.cv.SkillMapper;
import miwm.job4me.web.model.cv.SkillDto;
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
class SkillServiceImplTest {
    @Mock
    private SkillRepository skillRepository;
    @Mock
    private SkillMapper skillMapper;
    @Mock
    private SkillValidator skillValidator;
    @Mock
    private IdValidator idValidator;
    @InjectMocks
    private SkillServiceImpl skillService;

    private final String ENTITY_NAME = "Skill";
    private final int MAX_DESCRIPTION_LENGTH = 100;

    private Employee employee;
    private Skill skill1;
    private Skill skill2;
    private SkillDto skillDto1;
    private SkillDto skillDto2;

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

        skill1 = Skill
                .builder()
                .id(1L)
                .description("description1")
                .employee(null)
                .build();

        skillDto1 = new SkillDto();
        skillDto1.setId(skill1.getId());
        skillDto1.setDescription(skill1.getDescription());
        skillDto1.setEmployeeId(employee.getId());

        skill2 = Skill
                .builder()
                .id(2L)
                .description("description2")
                .employee(employee)
                .build();

        skillDto2 = new SkillDto();
        skillDto2.setId(skill2.getId());
        skillDto2.setDescription(skill2.getDescription());
        skillDto2.setEmployeeId(employee.getId());
    }

    @Test
    @DisplayName("Test existsById - return true when Skill object exists")
    public void testExistsByIdExists() {
        when(skillRepository.existsById(skill1.getId())).thenReturn(true);
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);

        assertTrue(skillService.existsById(skill1.getId()));
    }

    @Test
    @DisplayName("Test existsById - return false when Skill object does not exist")
    public void testExistsByIdDoesNotExist() {
        when(skillRepository.existsById(skill1.getId())).thenReturn(false);
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);

        assertFalse(skillService.existsById(skill1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - do nothing when Skill object exists")
    public void testStrictExistsByIdExists() {
        when(skillRepository.existsById(skill1.getId())).thenReturn(true);
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);

        assertDoesNotThrow(() -> skillService.strictExistsById(skill1.getId()));
    }

    @Test
    @DisplayName("Test strictExistsById - throw NoSuchElementFoundException when Skill object does not exist")
    public void testStrictExistsByIdDoesNotExist() {
        when(skillRepository.existsById(skill1.getId())).thenReturn(false);
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);

        try {
            skillService.strictExistsById(skill1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, skill1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test strictExistsById - throw InvalidArgumentException when id is null")
    public void testStrictExistsByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            skillService.strictExistsById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAll - return all existing Skill objects")
    public void testFindAll() {
        when(skillRepository.findAll()).thenReturn(List.of(skill1, skill2));
        when(skillMapper.toDto(skill1)).thenReturn(skillDto1);
        when(skillMapper.toDto(skill2)).thenReturn(skillDto2);

        Set<SkillDto> result = skillService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(skillDto1));
        assertTrue(result.contains(skillDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set when there are no Skill objects")
    public void testFindAllEmpty() {
        when(skillRepository.findAll()).thenReturn(List.of());

        Set<SkillDto> result = skillService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Skill object when it exists")
    public void testFindById() {
        when(skillRepository.findById(skill1.getId())).thenReturn(java.util.Optional.of(skill1));
        when(skillMapper.toDto(skill1)).thenReturn(skillDto1);

        SkillDto result = skillService.findById(skill1.getId());

        assertEquals(skillDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException when Skill object does not exist")
    public void testFindByIdNullId() {
        when(skillRepository.findById(skill1.getId())).thenReturn(java.util.Optional.empty());

        try {
            skillService.findById(skill1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound("Skill", skill1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findById - throw InvalidArgumentException when id is null")
    public void testFindByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            skillService.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return Skill object when it is valid")
    public void testSaveValid() {
        when(skillRepository.save(skill1)).thenReturn(skill1);
        when(skillMapper.toDto(skill1)).thenReturn(skillDto1);
        Mockito.doNothing().when(skillValidator).validate(skill1);

        SkillDto result = skillService.save(skill1);

        assertEquals(skillDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Skill object is null")
    public void testSaveThrowExceptionNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(skillValidator).validate(null);

        try {
            skillService.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when SkillValidator fails")
    public void testSaveThrowExceptionSkillValidatorFails() {
        skill1.setDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(skillValidator).validate(skill1);

        try {
            skillService.save(skill1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Skill object exists")
    public void testDeleteSkillExists() {
        when(skillRepository.existsById(skill1.getId())).thenReturn(true);
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);
        Mockito.doNothing().when(skillRepository).delete(skill1);

        assertDoesNotThrow(() -> skillService.delete(skill1));
    }

    @Test
    @DisplayName("Test delete - Skill object does not exist")
    public void testDeleteSkillDoesNotExist() {
        when(skillRepository.existsById(skill1.getId())).thenReturn(false);
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);

        try {
            skillService.delete(skill1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, skill1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Skill object is null")
    public void testDeleteSkillNull() {
        try {
            skillService.delete(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete - Skill id is null")
    public void testDeleteSkillIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);
        skill1.setId(null);

        try {
            skillService.delete(skill1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Skill object exists")
    public void testDeleteByIdSkillExists() {
        when(skillRepository.existsById(skill1.getId())).thenReturn(true);
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);
        Mockito.doNothing().when(skillRepository).deleteById(skill1.getId());

        assertDoesNotThrow(() -> skillService.deleteById(skill1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Skill object does not exist")
    public void testDeleteByIdDoesNotExist() {
        when(skillRepository.existsById(skill1.getId())).thenReturn(false);
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);

        try {
            skillService.deleteById(skill1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, skill1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - id is null")
    public void testDeleteByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            skillService.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - update and return SkillDto object when it is valid")
    public void testUpdateSkillExists() {
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);
        when(skillRepository.existsById(skill1.getId())).thenReturn(true);
        Mockito.doNothing().when(skillValidator).validateDto(skillDto1);
        when(skillMapper.toEntity(skillDto1)).thenReturn(skill1);
        when(skillRepository.save(skill1)).thenReturn(skill1);
        when(skillMapper.toDto(skill1)).thenReturn(skillDto1);

        SkillDto result = skillService.update(skillDto1);

        assertEquals(skillDto1, result);
    }

    @Test
    @DisplayName("Test update - throw NoSuchElementFoundException when IdValidator fails")
    public void testUpdateSkillDoesNotExist() {
        Mockito.doThrow(new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, skill1.getId()))).when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);

        try {
            skillService.update(skillDto1);
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound(ENTITY_NAME, skill1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test update - throw InvalidArgumentException when SkillDto object is null and SkillValidator fails")
    public void testUpdateSkillDtoNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(skillValidator).validateDto(null);

        try {
            skillService.update(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findAllByEmployeeId - return all existing Skill objects for given employee")
    public void testFindAllByEmployeeId() {
        when(skillRepository.findAllByEmployeeId(employee.getId())).thenReturn(List.of(skill1, skill2));
        when(skillMapper.toDto(skill1)).thenReturn(skillDto1);
        when(skillMapper.toDto(skill2)).thenReturn(skillDto2);

        List<SkillDto> result = skillService.findAllByEmployeeId(employee.getId());

        assertEquals(2, result.size());
        assertTrue(result.contains(skillDto1));
        assertTrue(result.contains(skillDto2));
    }

    @Test
    @DisplayName("Test findAllByEmployeeId - return empty set when there are no Skill objects for given employee")
    public void testFindAllByEmployeeIdEmpty() {
        when(skillRepository.findAllByEmployeeId(employee.getId())).thenReturn(List.of());

        List<SkillDto> result = skillService.findAllByEmployeeId(employee.getId());

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findAllByEmployeeId - throw InvalidArgumentException when id is null")
    public void testFindAllByEmployeeIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            skillService.findAllByEmployeeId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test deleteAllByEmployeeId - delete all Skill objects for given employee")
    public void testDeleteAllByEmployeeId() {
        Mockito.doNothing().when(idValidator).validateLongId(employee.getId(), ENTITY_NAME);
        Mockito.doNothing().when(skillRepository).deleteAllByEmployeeId(employee.getId());

        assertDoesNotThrow(() -> skillService.deleteAllByEmployeeId(employee.getId()));
    }

    @Test
    @DisplayName("Test deleteAllByEmployeeId - throw InvalidArgumentException when id is null")
    public void testDeleteAllByEmployeeIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            skillService.deleteAllByEmployeeId(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }
}
