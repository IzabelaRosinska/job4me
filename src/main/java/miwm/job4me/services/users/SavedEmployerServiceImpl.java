package miwm.job4me.services.users;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.SavedEmployer;
import miwm.job4me.repositories.users.SavedEmployerRepository;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.users.EmployerReviewMapper;
import miwm.job4me.web.model.users.EmployerReviewDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SavedEmployerServiceImpl implements SavedEmployerService {

    private final SavedEmployerRepository savedEmployerRepository;
    private final EmployerReviewMapper employerReviewMapper;
    private final EmployeeService employeeService;
    private final EmployerService employerService;
    private final IdValidator idValidator;
    private final String ENTITY_EMPLOYEE = "Employee";
    private final String ENTITY_EMPLOYER = "Employer";
    private final String ENTITY_SAVED_EMPLOYER = "SavedEmployer";

    public SavedEmployerServiceImpl(SavedEmployerRepository savedEmployerRepository, EmployerReviewMapper employerReviewMapper, EmployeeService employeeService, EmployerService employerService, IdValidator idValidator) {
        this.savedEmployerRepository = savedEmployerRepository;
        this.employerReviewMapper = employerReviewMapper;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.idValidator = idValidator;
    }

    @Override
    public Set<SavedEmployer> findAll() {
        return (Set<SavedEmployer>) savedEmployerRepository.findAll();
    }

    @Override
    public SavedEmployer findById(Long id) {
        idValidator.validateLongId(id, ENTITY_SAVED_EMPLOYER);
        Optional<SavedEmployer> savedEmployer = savedEmployerRepository.findById(id);
        if(savedEmployer.isPresent())
            return savedEmployer.get();
        throw new NoSuchElementFoundException("Saved employer with that id does not exist");
    }

    @Override
    @Transactional
    public SavedEmployer save(SavedEmployer savedEmployer) {
        if(savedEmployer != null)
            return savedEmployerRepository.save(savedEmployer);
        throw new InvalidArgumentException("Given employer is null");
    }

    @Override
    @Transactional
    public void delete(SavedEmployer savedEmployer) {
        if(savedEmployerRepository.findById(savedEmployer.getId()).isPresent())
            savedEmployerRepository.delete(savedEmployer);
        else
            throw new NoSuchElementFoundException("There is no such employer in database");
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_SAVED_EMPLOYER);
        if(savedEmployerRepository.findById(id).isPresent())
            savedEmployerRepository.deleteById(id);
        else
            throw new NoSuchElementFoundException("Employer with given id does not exist");
    }

    @Override
    public List<SavedEmployer> getSavedForEmployeeWithId(Long employeeId) {
        idValidator.validateLongId(employeeId, ENTITY_EMPLOYEE);
        return savedEmployerRepository.getSavedForEmployee(employeeId);
    }

    @Override
    public Optional<SavedEmployer> findByIds(Long employeeId, Long employerId) {
        idValidator.validateLongId(employerId, ENTITY_EMPLOYER);
        idValidator.validateLongId(employeeId, ENTITY_EMPLOYEE);
        if(employeeService.findById(employeeId) != null && employerService.findById(employerId) != null)
            return savedEmployerRepository.findByIds(employeeId, employerId);
        else
            throw new NoSuchElementFoundException("User with given id does not exist");
    }

    @Override
    public Boolean checkIfSaved(Long id) {
        idValidator.validateLongId(id, ENTITY_EMPLOYER);
        Long employeeId = employeeService.getAuthEmployee().getId();
        Optional<SavedEmployer> savedEmployer = findByIds(employeeId, id);
        if(savedEmployer.isPresent())
            return true;
        return false;
    }

    @Override
    public List<EmployerReviewDto> getSavedEmployers() {
        Employee employee = employeeService.getAuthEmployee();
        return getSavedForEmployeeWithId(employee.getId()).stream()
                .map(employer -> employerReviewMapper.employerToEmployerReviewDto(employer.getEmployer(), checkIfSaved(employer.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addEmployerToSaved(Long id) {
        idValidator.validateLongId(id, ENTITY_EMPLOYER);
        Employee employee = employeeService.getAuthEmployee();
        Employer employer = employerService.findById(id);
        if(employee != null && employer != null) {
            SavedEmployer savedEmployer = SavedEmployer.builder().employee(employee).employer(employer).build();
            save(savedEmployer);
        } else
            throw new NoSuchElementFoundException("User does not exist");
    }

    @Override
    @Transactional
    public void deleteEmployerFromSaved(Long id) {
        idValidator.validateLongId(id, ENTITY_EMPLOYER);
        Employee employee = employeeService.getAuthEmployee();
        if(employee != null) {
            Optional<SavedEmployer> saved = findByIds(employee.getId(), id);
            if (saved.isPresent()) {
                delete(saved.get());
            } else
                throw new NoSuchElementFoundException("Employee with given id does not exist");
        }
    }

    @Override
    public EmployerReviewDto findEmployerWithIdByUser(Long id) {
        idValidator.validateLongId(id, ENTITY_EMPLOYER);
        Employer employer = employerService.findById(id);
        if(employer != null)
            return employerReviewMapper.employerToEmployerReviewDto(employer, checkIfSaved(id));
        else
            throw new NoSuchElementFoundException("Employer with given id does not exist");
    }
}
