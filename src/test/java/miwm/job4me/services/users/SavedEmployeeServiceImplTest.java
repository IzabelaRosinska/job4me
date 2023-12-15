package miwm.job4me.services.users;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.SavedEmployee;
import miwm.job4me.repositories.users.SavedEmployeeRepository;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.mappers.listDisplay.ListDisplayMapper;
import miwm.job4me.web.mappers.users.EmployeeReviewMapper;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.users.EmployeeReviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;


public class SavedEmployeeServiceImplTest {

    @Mock
    private SavedEmployeeRepository savedEmployeeRepository;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private EmployerService employerService;
    @Mock
    private EmployeeReviewMapper employeeReviewMapper;
    @Mock
    private ListDisplayMapper listDisplayMapper;
    @Mock
    private IdValidator idValidator;
    @Mock
    private PaginationValidator paginationValidator;

    @InjectMocks
    private SavedEmployeeServiceImpl savedEmployeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Existing code...

    @Test
    public void testSaveWhenSavedEmployeeIsValidThenReturnSavedEmployee() {
        // Arrange
        SavedEmployee savedEmployee = new SavedEmployee();
        savedEmployee.setId(1L);
        when(savedEmployeeRepository.save(savedEmployee)).thenReturn(savedEmployee);

        // Act
        SavedEmployee result = savedEmployeeService.save(savedEmployee);

        // Assert
        assertEquals(savedEmployee, result);
        verify(savedEmployeeRepository, times(1)).save(savedEmployee);
    }

    @Test
    public void testSaveWhenSavedEmployeeIsNullThenThrowException() {
        // Arrange
        SavedEmployee savedEmployee = null;

        // Act and Assert
        assertThrows(InvalidArgumentException.class, () -> savedEmployeeService.save(savedEmployee));
    }

    @Test
    public void testDeleteWhenSavedEmployeeExistsThenDeleteSavedEmployee() {
        // Arrange
        SavedEmployee savedEmployee = new SavedEmployee();
        savedEmployee.setId(1L);
        when(savedEmployeeRepository.findById(savedEmployee.getId())).thenReturn(Optional.of(savedEmployee));

        // Act
        savedEmployeeService.delete(savedEmployee);

        // Assert
        verify(savedEmployeeRepository, times(1)).delete(savedEmployee);
    }

    @Test
    public void testDeleteWhenSavedEmployeeDoesNotExistThenThrowException() {
        // Arrange
        SavedEmployee savedEmployee = new SavedEmployee();
        savedEmployee.setId(1L);
        when(savedEmployeeRepository.findById(savedEmployee.getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(InvalidArgumentException.class, () -> savedEmployeeService.delete(savedEmployee));
    }

    @Test
    public void testDeleteByIdWhenSavedEmployeeExistsThenDeleteSavedEmployee() {
        // Arrange
        Long id = 1L;
        when(savedEmployeeRepository.findById(id)).thenReturn(Optional.of(new SavedEmployee()));

        // Act
        savedEmployeeService.deleteById(id);

        // Assert
        verify(savedEmployeeRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteByIdWhenSavedEmployeeDoesNotExistThenThrowException() {
        // Arrange
        Long id = 1L;
        when(savedEmployeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementFoundException.class, () -> savedEmployeeService.deleteById(id));
    }


    @Test
    public void testFindByIdWhenSavedEmployeeExistsThenReturnSavedEmployee() {
        // Arrange
        Long id = 1L;
        SavedEmployee savedEmployee = new SavedEmployee();
        savedEmployee.setId(id);
        when(savedEmployeeRepository.findById(id)).thenReturn(Optional.of(savedEmployee));

        // Act
        SavedEmployee result = savedEmployeeService.findById(id);

        // Assert
        assertEquals(savedEmployee, result);
        verify(savedEmployeeRepository, times(1)).findById(id);
        verify(idValidator, times(1)).validateLongId(id, "SavedEmployee");
    }

    @Test
    public void testFindByIdWhenSavedEmployeeDoesNotExistThenThrowException() {
        // Arrange
        Long id = 1L;
        when(savedEmployeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementFoundException.class, () -> savedEmployeeService.findById(id));
        verify(idValidator, times(1)).validateLongId(id, "SavedEmployee");
    }

    @Test
    public void testCheckIfSavedForEmployerWithId() {
        // Arrange
        Long employerId = 1L;
        Long employeeId = 2L;
        when(savedEmployeeRepository.findByIds(employerId, employeeId)).thenReturn(Optional.of(new SavedEmployee()));

        // Act
        boolean result = savedEmployeeService.checkIfSavedForEmployerWithId(employerId, employeeId);

        // Assert
        assertTrue(result);
        verify(savedEmployeeRepository, times(1)).findByIds(employerId, employeeId);
        verify(idValidator, times(2)).validateLongId(anyLong(), anyString());
    }

    @Test
    public void testGetSavedForEmployerWithId() {
        // Arrange
        Long employerId = 1L;
        List<SavedEmployee> savedEmployees = new ArrayList<>();
        when(savedEmployeeRepository.getSavedForEmployer(employerId)).thenReturn(savedEmployees);

        // Act
        List<SavedEmployee> result = savedEmployeeService.getSavedForEmployerWithId(employerId);

        // Assert
        assertEquals(savedEmployees, result);
        verify(savedEmployeeRepository, times(1)).getSavedForEmployer(employerId);
        verify(idValidator, times(1)).validateLongId(employerId, "Employer");
    }

    @Test
    public void testGetSavedEmployeesForEmployerWithId() {
        // Arrange
        int page = 0;
        int size = 10;
        Long employerId = 1L;
        Page<SavedEmployee> savedEmployees = new PageImpl<>(new ArrayList<>());
        when(savedEmployeeRepository.findAllByEmployerIdOrderByIdDesc(PageRequest.of(page, size), employerId)).thenReturn(savedEmployees);

        // Act
        Page<SavedEmployee> result = savedEmployeeService.getSavedEmployeesForEmployerWithId(page, size, employerId);

        // Assert
        assertEquals(savedEmployees, result);
        verify(savedEmployeeRepository, times(1)).findAllByEmployerIdOrderByIdDesc(PageRequest.of(page, size), employerId);
        verify(idValidator, times(1)).validateLongId(employerId, "Employer");
        verify(paginationValidator, times(1)).validatePagination(page, size);
    }

    @Test
    public void testGetSavedEmployeesForEmployerWithIdListDisplay() {
        // Arrange
        int page = 0;
        int size = 10;
        Employer employer = new Employer();
        employer.setId(1L);
        when(employerService.getAuthEmployer()).thenReturn(employer);
        Page<SavedEmployee> savedEmployees = new PageImpl<>(new ArrayList<>());
        when(savedEmployeeRepository.findAllByEmployerIdOrderByIdDesc(PageRequest.of(page, size), employer.getId())).thenReturn(savedEmployees);
        when(listDisplayMapper.toDtoFromEmployee(any())).thenReturn(new ListDisplayDto());

        // Act
        Page<ListDisplayDto> result = savedEmployeeService.getSavedEmployeesForEmployerWithIdListDisplay(page, size);

        // Assert
        assertEquals(savedEmployees.getTotalElements(), result.getTotalElements());
        verify(employerService, times(1)).getAuthEmployer();
        verify(savedEmployeeRepository, times(1)).findAllByEmployerIdOrderByIdDesc(PageRequest.of(page, size), employer.getId());
        verify(listDisplayMapper, times((int) savedEmployees.getTotalElements())).toDtoFromEmployee(any());
    }

    @Test
    public void testFindByIds() {
        // Arrange
        Long employerId = 1L;
        Long employeeId = 2L;
        Optional<SavedEmployee> savedEmployee = Optional.of(new SavedEmployee());
        when(savedEmployeeRepository.findByIds(employerId, employeeId)).thenReturn(savedEmployee);
        when(employeeService.findById(employeeId)).thenReturn(new Employee());
        when(employerService.findById(employerId)).thenReturn(new Employer());

        // Act
        Optional<SavedEmployee> result = savedEmployeeService.findByIds(employerId, employeeId);

        // Assert
        assertEquals(savedEmployee, result);
        verify(savedEmployeeRepository, times(1)).findByIds(employerId, employeeId);
        verify(idValidator, times(2)).validateLongId(anyLong(), anyString());
        verify(employeeService, times(1)).findById(employeeId);
        verify(employerService, times(1)).findById(employerId);
    }

}