package miwm.job4me.services.users;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.SavedEmployee;
import miwm.job4me.repositories.users.SavedEmployeeRepository;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.users.EmployeeReviewMapper;
import miwm.job4me.web.model.users.EmployeeReviewDto;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SavedEmployeeServiceImpl implements SavedEmployeeService {

    private final SavedEmployeeRepository savedEmployeeRepository;
    private final EmployeeReviewMapper employeeReviewMapper;
    private final EmployeeService employeeService;
    private final EmployerService employerService;
    private final IdValidator idValidator;
    private final String ENTITY_EMPLOYEE = "Employee";
    private final String ENTITY_EMPLOYER = "Employer";
    private final String ENTITY_SAVED_EMPLOYEE = "SavedEmployee";

    public SavedEmployeeServiceImpl(SavedEmployeeRepository savedEmployeeRepository, EmployeeService employeeService, EmployerService employerService, EmployeeReviewMapper employeeReviewMapper, IdValidator idValidator) {
        this.savedEmployeeRepository = savedEmployeeRepository;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.employeeReviewMapper = employeeReviewMapper;
        this.idValidator = idValidator;
    }

    @Override
    public Set<SavedEmployee> findAll() {
        return (Set<SavedEmployee>) savedEmployeeRepository.findAll();
    }

    @Override
    public SavedEmployee findById(Long id) {
        idValidator.validateLongId(id, ENTITY_SAVED_EMPLOYEE);
        Optional<SavedEmployee> savedEmployee = savedEmployeeRepository.findById(id);
        if(savedEmployee.isPresent())
            return savedEmployee.get();
        throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_SAVED_EMPLOYEE, id));
    }

    @Override
    @Transactional
    public SavedEmployee save(SavedEmployee savedEmployee) {
        if(savedEmployee != null)
            return savedEmployeeRepository.save(savedEmployee);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_SAVED_EMPLOYEE));
    }

    @Override
    @Transactional
    public void delete(SavedEmployee savedEmployee) {
        if(savedEmployee != null && savedEmployeeRepository.findById(savedEmployee.getId()).isPresent())
            savedEmployeeRepository.delete(savedEmployee);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_SAVED_EMPLOYEE));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_SAVED_EMPLOYEE);
        if(savedEmployeeRepository.findById(id).isPresent())
            savedEmployeeRepository.deleteById(id);
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_SAVED_EMPLOYEE, id));
    }

    @Override
    public boolean checkIfSavedForEmployerWithId(Long employerId, Long employeeId) {
        idValidator.validateLongId(employerId, ENTITY_EMPLOYER);
        idValidator.validateLongId(employeeId, ENTITY_EMPLOYEE);
        Optional<SavedEmployee> savedEmployee = savedEmployeeRepository.findByIds(employerId, employeeId);
        if(!savedEmployee.isPresent())
            return false;
        return true;
    }

    @Override
    public List<SavedEmployee> getSavedForEmployerWithId(Long employerId) {
        idValidator.validateLongId(employerId, ENTITY_EMPLOYER);
        return savedEmployeeRepository.getSavedForEmployer(employerId);
    }

    @Override
    public Optional<SavedEmployee> findByIds(Long employerId, Long employeeId) {
        idValidator.validateLongId(employerId, ENTITY_EMPLOYER);
        idValidator.validateLongId(employeeId, ENTITY_EMPLOYEE);
        if(employeeService.findById(employeeId) != null && employerService.findById(employerId) != null)
            return savedEmployeeRepository.findByIds(employerId, employeeId);
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_EMPLOYEE, employeeId));
    }

    @Override
    public Boolean checkIfSaved(Long id) {
        idValidator.validateLongId(id, ENTITY_EMPLOYER);
        Long employerId = employerService.getAuthEmployer().getId();
        Optional<SavedEmployee> savedEmployee = findByIds(employerId, id);
        if(savedEmployee.isPresent())
            return true;
        return false;
    }

    @Override
    public EmployeeReviewDto findEmployeeWithIdByUser(Long id) {
        idValidator.validateLongId(id, ENTITY_EMPLOYEE);
        Employee employee = employeeService.findById(id);
        if(employee != null)
            return employeeReviewMapper.toDto(employee, checkIfSaved(id));
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_EMPLOYEE, id));
    }

    @Override
    @Transactional
    public void addEmployeeToSaved(Long id) {
        idValidator.validateLongId(id, ENTITY_EMPLOYER);
        Employer employer = employerService.getAuthEmployer();
        Employee employee = employeeService.findById(id);
        if(employee != null && employer != null) {
            SavedEmployee savedEmployee = SavedEmployee.builder().employee(employee).employer(employer).build();
            save(savedEmployee);
        } else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_EMPLOYEE, id));
    }

    @Override
    @Transactional
    public void deleteEmployeeFromSaved(Long id) {
        idValidator.validateLongId(id, ENTITY_EMPLOYER);
        Employer employer = employerService.getAuthEmployer();
        if(employer != null) {
            Optional<SavedEmployee> saved = findByIds(employer.getId(), id);
            if (saved.isPresent()) {
                delete(saved.get());
            } else
                throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_SAVED_EMPLOYEE, id));
        }
    }

    @Override
    public List<EmployeeReviewDto> getSavedEmployees() {
        Employer employer = employerService.getAuthEmployer();
        return getSavedForEmployerWithId(employer.getId()).stream()
                .map(employee -> employeeReviewMapper.toDto(employee.getEmployee(), checkIfSaved(employee.getId())))
                .collect(Collectors.toList());
    }

}
