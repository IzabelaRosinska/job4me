package miwm.job4me.services.cv;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.cv.Skill;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.cv.SkillRepository;
import miwm.job4me.validators.entity.IdValidator;
import miwm.job4me.validators.entity.cv.SkillValidator;
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
    private SkillServiceImpl skillServiceImpl;

    private final String ENTITY_NAME = "Skill";
    private final int MAX_DESCRIPTION_LENGTH = 50;

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
    @DisplayName("Test findAll - return all Project objects")
    public void testFindAll() {
        when(skillRepository.findAll()).thenReturn(List.of(skill1, skill2));
        when(skillMapper.toDto(skill1)).thenReturn(skillDto1);
        when(skillMapper.toDto(skill2)).thenReturn(skillDto2);

        Set<SkillDto> result = skillServiceImpl.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(skillDto1));
        assertTrue(result.contains(skillDto2));
    }

    @Test
    @DisplayName("Test findAll - return empty set")
    public void testFindAllEmpty() {
        when(skillRepository.findAll()).thenReturn(List.of());

        Set<SkillDto> result = skillServiceImpl.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test findById - return Project object")
    public void testFindById() {
        when(skillRepository.findById(skill1.getId())).thenReturn(java.util.Optional.of(skill1));
        when(skillMapper.toDto(skill1)).thenReturn(skillDto1);

        SkillDto result = skillServiceImpl.findById(skill1.getId());

        assertEquals(skillDto1, result);
    }

    @Test
    @DisplayName("Test findById - throw NoSuchElementFoundException")
    public void testFindByIdNullId() {
        when(skillRepository.findById(skill1.getId())).thenReturn(java.util.Optional.empty());

        try {
            skillServiceImpl.findById(skill1.getId());
            fail();
        } catch (NoSuchElementFoundException e) {
            assertEquals(ExceptionMessages.elementNotFound("Skill", skill1.getId()), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test findById - id is null")
    public void testFindByIdNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.idCannotBeNull(ENTITY_NAME))).when(idValidator).validateLongId(null, ENTITY_NAME);

        try {
            skillServiceImpl.findById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - return Project object when it is valid")
    public void testSaveValid() {
        when(skillRepository.save(skill1)).thenReturn(skill1);
        when(skillMapper.toDto(skill1)).thenReturn(skillDto1);
        Mockito.doNothing().when(skillValidator).validate(skill1);

        SkillDto result = skillServiceImpl.save(skill1);

        assertEquals(skillDto1, result);
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException when Project object is null")
    public void testSaveThrowExceptionNull() {
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME))).when(skillValidator).validate(null);

        try {
            skillServiceImpl.save(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test save - throw InvalidArgumentException, description too long")
    public void testSaveThrowExceptionDescriptionTooLong() {
        skill1.setDescription("a".repeat(MAX_DESCRIPTION_LENGTH + 1));
        Mockito.doThrow(new InvalidArgumentException(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH))).when(skillValidator).validate(skill1);

        try {
            skillServiceImpl.save(skill1);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.textTooLong(ENTITY_NAME, "description", MAX_DESCRIPTION_LENGTH), e.getMessage());
        }
    }

    @Test
    @DisplayName("Test delete by id - Project object exists")
    public void testDeleteByIdExists() {
        when(skillRepository.findById(skill1.getId())).thenReturn(java.util.Optional.of(skill1));
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);
        Mockito.doNothing().when(skillRepository).deleteById(skill1.getId());

        assertDoesNotThrow(() -> skillServiceImpl.deleteById(skill1.getId()));
    }

    @Test
    @DisplayName("Test delete by id - Project object does not exist")
    public void testDeleteByIdDoesNotExist() {
        when(skillRepository.findById(skill1.getId())).thenReturn(java.util.Optional.empty());
        Mockito.doNothing().when(idValidator).validateLongId(skill1.getId(), ENTITY_NAME);

        try {
            skillServiceImpl.deleteById(skill1.getId());
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
            skillServiceImpl.deleteById(null);
            fail();
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.idCannotBeNull(ENTITY_NAME), e.getMessage());
        }
    }
}