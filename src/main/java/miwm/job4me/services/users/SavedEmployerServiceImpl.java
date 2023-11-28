package miwm.job4me.services.users;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.SavedEmployer;
import miwm.job4me.repositories.users.SavedEmployerRepository;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.mappers.listDisplay.ListDisplayMapper;
import miwm.job4me.web.mappers.users.EmployerReviewMapper;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.users.EmployerReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final ListDisplayMapper listDisplayMapper;
    private final EmployeeService employeeService;
    private final EmployerService employerService;
    private final IdValidator idValidator;
    private final PaginationValidator paginationValidator;
    private final String ENTITY_EMPLOYEE = "Employee";
    private final String ENTITY_EMPLOYER = "Employer";
    private final String ENTITY_SAVED_EMPLOYER = "SavedEmployer";

    public SavedEmployerServiceImpl(SavedEmployerRepository savedEmployerRepository, EmployerReviewMapper employerReviewMapper, ListDisplayMapper listDisplayMapper, EmployeeService employeeService, EmployerService employerService, IdValidator idValidator, PaginationValidator paginationValidator) {
        this.savedEmployerRepository = savedEmployerRepository;
        this.employerReviewMapper = employerReviewMapper;
        this.listDisplayMapper = listDisplayMapper;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.idValidator = idValidator;
        this.paginationValidator = paginationValidator;
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
        throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_SAVED_EMPLOYER, id));
    }

    @Override
    @Transactional
    public SavedEmployer save(SavedEmployer savedEmployer) {
        if(savedEmployer != null)
            return savedEmployerRepository.save(savedEmployer);
        throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_SAVED_EMPLOYER));
    }

    @Override
    @Transactional
    public void delete(SavedEmployer savedEmployer) {
        if(savedEmployer != null && savedEmployerRepository.findById(savedEmployer.getId()).isPresent())
            savedEmployerRepository.delete(savedEmployer);
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_SAVED_EMPLOYER, savedEmployer.getId()));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_SAVED_EMPLOYER);
        if(savedEmployerRepository.findById(id).isPresent())
            savedEmployerRepository.deleteById(id);
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_SAVED_EMPLOYER, id));
    }

    @Override
    public List<SavedEmployer> getSavedForEmployeeWithId(Long employeeId) {
        idValidator.validateLongId(employeeId, ENTITY_EMPLOYEE);
        return savedEmployerRepository.getSavedForEmployee(employeeId);
    }

    @Override
    public Page<SavedEmployer> getSavedEmployersForEmployeeWithId(int page, int size, Long employeeId) {
        idValidator.validateLongId(employeeId, ENTITY_EMPLOYEE);
        paginationValidator.validatePagination(page, size);

        return savedEmployerRepository.findAllByEmployeeIdOrderByIdDesc(PageRequest.of(page, size), employeeId);
    }

    @Override
    public Page<ListDisplayDto> getSavedEmployersForEmployeeWithIdListDisplay(int page, int size) {
        Employee employee = employeeService.getAuthEmployee();

        return getSavedEmployersForEmployeeWithId(page, size, employee.getId())
                .map(savedEmployer -> listDisplayMapper.toDtoFromEmployer(savedEmployer.getEmployer()));
    }

    @Override
    public Optional<SavedEmployer> findByIds(Long employeeId, Long employerId) {
        idValidator.validateLongId(employerId, ENTITY_EMPLOYER);
        idValidator.validateLongId(employeeId, ENTITY_EMPLOYEE);
        if(employeeService.findById(employeeId) != null && employerService.findById(employerId) != null)
            return savedEmployerRepository.findByIds(employeeId, employerId);
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_EMPLOYER, employerId));
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
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_EMPLOYER, id));
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
                throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_SAVED_EMPLOYER, id));
        }
    }

    @Override
    public EmployerReviewDto findEmployerWithIdByUser(Long id) {
        idValidator.validateLongId(id, ENTITY_EMPLOYER);
        Employer employer = employerService.findById(id);
        if(employer != null)
            return employerReviewMapper.employerToEmployerReviewDto(employer, checkIfSaved(id));
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_EMPLOYER, id));
    }
}
