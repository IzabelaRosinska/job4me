package miwm.job4me.services.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.SavedEmployer;
import miwm.job4me.repositories.users.SavedEmployerRepository;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import static java.util.Objects.deepEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class SavedEmployerServiceImplTest {

    @Test
    @DisplayName("addEmployerToSaved: employee != null && employer != null -> ThrowNoSuchElementFoundException")
    public void testAddEmployerToSaved_EmployerEqualsNull() {
        EmployeeService employeeServiceMock = mock(EmployeeService.class);
        Employee employeeMock = mock(Employee.class);
        (when(employeeServiceMock.getAuthEmployee())).thenReturn(employeeMock);
        EmployerService employerServiceMock = mock(EmployerService.class);
        (when(employerServiceMock.findById(any()))).thenReturn(null);
        IdValidator idValidatorMock = mock(IdValidator.class);
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(null, null, null, employeeServiceMock, employerServiceMock, idValidatorMock, null);

        assertThrows(miwm.job4me.exceptions.NoSuchElementFoundException.class, () -> savedEmployerServiceImpl.addEmployerToSaved(null));
    }

    @Test
    @DisplayName("addEmployerToSaved: employee != null && employer != null -> ThrowNoSuchElementFoundException")
    public void testAddEmployerToSaved_EmployeeEqualsNull() {
        EmployeeService employeeServiceMock = mock(EmployeeService.class);
        (when(employeeServiceMock.getAuthEmployee())).thenReturn(((Employee) null));
        EmployerService employerServiceMock = mock(EmployerService.class);
        (when(employerServiceMock.findById(any()))).thenReturn(null);
        IdValidator idValidatorMock = mock(IdValidator.class);
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(null, null, null, employeeServiceMock, employerServiceMock, idValidatorMock, null);

        assertThrows(miwm.job4me.exceptions.NoSuchElementFoundException.class, () -> savedEmployerServiceImpl.addEmployerToSaved(null));
    }

    @Test
    @DisplayName("deleteEmployerFromSaved: IdValidatorValidateLongId -> employee != null : False")
    public void testDeleteEmployerFromSaved_EmployeeEqualsNull() {
        EmployeeService employeeServiceMock = mock(EmployeeService.class);
        (when(employeeServiceMock.getAuthEmployee())).thenReturn(((Employee) null));
        IdValidator idValidatorMock = mock(IdValidator.class);
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(null, null, null, employeeServiceMock, null, idValidatorMock, null);

        savedEmployerServiceImpl.deleteEmployerFromSaved(null);
    }

    @Test
    @DisplayName("deleteEmployerFromSaved: saved = findByIds(employee.getId(), id) : True -> ThrowNoSuchElementFoundException")
    public void testDeleteEmployerFromSaved_EmployeeNotEqualsNull_1() {
        EmployeeService employeeServiceMock = mock(EmployeeService.class);
        Employee employeeMock = mock(Employee.class);
        (when(employeeMock.getId())).thenReturn(((Long) null));
        (when(employeeServiceMock.getAuthEmployee())).thenReturn(employeeMock);
        (when(employeeServiceMock.findById(any()))).thenReturn(null);
        IdValidator idValidatorMock = mock(IdValidator.class);
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(null, null, null, employeeServiceMock, null, idValidatorMock, null);

        assertThrows(miwm.job4me.exceptions.NoSuchElementFoundException.class, () -> savedEmployerServiceImpl.deleteEmployerFromSaved(null));
    }

    @Test
    @DisplayName("findAll: SavedEmployerRepositoryFindAll -> return (Set<SavedEmployer>) savedEmployerRepository.findAll()")
    public void testFindAll_SavedEmployerRepositoryFindAll() {
        SavedEmployerRepository savedEmployerRepositoryMock = mock(SavedEmployerRepository.class);
        (when(savedEmployerRepositoryMock.findAll())).thenReturn(((List) null));
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(savedEmployerRepositoryMock, null, null, null, null, null, null);

        Set actual = savedEmployerServiceImpl.findAll();

        assertNull(actual);
    }

    @Test
    @DisplayName("findByIds: employeeService.findById(employeeId) != null && employerService.findById(employerId) != null -> ThrowNoSuchElementFoundException")
    public void testFindByIds_EmployeeServiceFindByIdEqualsNull() {
        EmployeeService employeeServiceMock = mock(EmployeeService.class);
        (when(employeeServiceMock.findById(any()))).thenReturn(null);
        IdValidator idValidatorMock = mock(IdValidator.class);
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(null, null, null, employeeServiceMock, null, idValidatorMock, null);

        assertThrows(miwm.job4me.exceptions.NoSuchElementFoundException.class, () -> savedEmployerServiceImpl.findByIds(null, null));
    }

    @Test
    @DisplayName("findEmployerWithIdByUser: employer != null -> ThrowNoSuchElementFoundException")
    public void testFindEmployerWithIdByUser_EmployerEqualsNull() {
        EmployerService employerServiceMock = mock(EmployerService.class);
        (when(employerServiceMock.findById(any()))).thenReturn(null);
        IdValidator idValidatorMock = mock(IdValidator.class);
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(null, null, null, null, employerServiceMock, idValidatorMock, null);

        assertThrows(miwm.job4me.exceptions.NoSuchElementFoundException.class, () -> savedEmployerServiceImpl.findEmployerWithIdByUser(null));
    }

    @Test
    public void testGetSavedEmployers1() {
        SavedEmployerRepository savedEmployerRepositoryMock = mock(SavedEmployerRepository.class);
        ArrayList arrayList = new ArrayList();
        (when(savedEmployerRepositoryMock.getSavedForEmployee(any()))).thenReturn(arrayList);
        EmployeeService employeeServiceMock = mock(EmployeeService.class);
        Employee employeeMock = mock(Employee.class);
        (when(employeeMock.getId())).thenReturn(((Long) null));
        (when(employeeServiceMock.getAuthEmployee())).thenReturn(employeeMock);
        IdValidator idValidatorMock = mock(IdValidator.class);
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(savedEmployerRepositoryMock, null, null, employeeServiceMock, null, idValidatorMock, null);

        ArrayList actual = ((ArrayList) savedEmployerServiceImpl.getSavedEmployers());

        ArrayList expected = new ArrayList();
        assertTrue(deepEquals(expected, actual));
    }

    @Test
    @DisplayName("getSavedForEmployeeWithId: IdValidatorValidateLongId -> return savedEmployerRepository.getSavedForEmployee(employeeId)")
    public void testGetSavedForEmployeeWithId_SavedEmployerRepositoryGetSavedForEmployee() {
        SavedEmployerRepository savedEmployerRepositoryMock = mock(SavedEmployerRepository.class);
        (when(savedEmployerRepositoryMock.getSavedForEmployee(any()))).thenReturn(((List) null));
        IdValidator idValidatorMock = mock(IdValidator.class);
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(savedEmployerRepositoryMock, null, null, null, null, idValidatorMock, null);

        List actual = savedEmployerServiceImpl.getSavedForEmployeeWithId(null);

        assertNull(actual);
    }

    @Test
    @DisplayName("save: savedEmployer != null : True -> return savedEmployerRepository.save(savedEmployer)")
    public void testSave_SavedEmployerNotEqualsNull() {
        SavedEmployerRepository savedEmployerRepositoryMock = mock(SavedEmployerRepository.class);
        (when(savedEmployerRepositoryMock.save(any()))).thenReturn(null);
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(savedEmployerRepositoryMock, null, null, null, null, null, null);
        SavedEmployer savedEmployerMock = mock(SavedEmployer.class);

        SavedEmployer actual = savedEmployerServiceImpl.save(savedEmployerMock);

        assertNull(actual);
    }

    @Test
    @DisplayName("save: @Override @Transactional public SavedEmployer save(SavedEmployer savedEmployer) { if (savedEmployer != null) return savedEmployerRepository.save(savedEmployer) throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_SAVED_EMPLOYER)) } -> ThrowInvalidArgumentException")
    public void testSave_SavedEmployerEqualsNull() {
        SavedEmployerServiceImpl savedEmployerServiceImpl = new SavedEmployerServiceImpl(null, null, null, null, null, null, null);

        assertThrows(miwm.job4me.exceptions.InvalidArgumentException.class, () -> savedEmployerServiceImpl.save(((SavedEmployer) null)));
    }

}
