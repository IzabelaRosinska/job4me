package miwm.job4me.services.users;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.SavedEmployee;
import miwm.job4me.repositories.users.SavedEmployeeRepository;
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

    public SavedEmployeeServiceImpl(SavedEmployeeRepository savedEmployeeRepository, EmployeeService employeeService, EmployerService employerService, EmployeeReviewMapper employeeReviewMapper) {
        this.savedEmployeeRepository = savedEmployeeRepository;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.employeeReviewMapper = employeeReviewMapper;
    }

    @Override
    public Set<SavedEmployee> findAll() {
        return null;
    }

    @Override
    public SavedEmployee findById(Long aLong) {
        return null;
    }

    @Override
    @Transactional
    public SavedEmployee save(SavedEmployee savedEmployee) {
        return savedEmployeeRepository.save(savedEmployee);
    }

    @Override
    @Transactional
    public void delete(SavedEmployee savedEmployee) {
        savedEmployeeRepository.delete(savedEmployee);
    }

    @Override
    @Transactional
    public void deleteById(Long aLong) {

    }

    @Override
    public boolean checkIfSavedForEmployerWithId(Long employerId, Long employeeId) {
        Optional<SavedEmployee> savedEmployee = savedEmployeeRepository.findByIds(employerId, employeeId);
        if(!savedEmployee.isPresent())
            return false;
        return true;
    }

    @Override
    public List<SavedEmployee> getSavedForEmployerWithId(Long employerId) {
        return savedEmployeeRepository.getSavedForEmployer(employerId);
    }

    @Override
    public Optional<SavedEmployee> findByIds(Long employerId, Long employeeId) {
        return savedEmployeeRepository.findByIds(employerId, employeeId);
    }

    @Override
    public Boolean checkIfSaved(Long id) {
        Long employerId = employerService.getAuthEmployer().getId();
        Optional<SavedEmployee> savedEmployee = findByIds(employerId, id);
        if(savedEmployee.isPresent())
            return true;
        return false;
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeService.findById(id);
    }


    @Override
    public EmployeeReviewDto findEmployeeWithIdByUser(Long id) {
        Employee employee = employeeService.findById(id);
        if(employee != null)
            return employeeReviewMapper.toDto(employee, checkIfSaved(id));
        else
            throw new NoSuchElementFoundException();
    }

    @Override
    @Transactional
    public void addEmployeeToSaved(Long id) {
        Employer employer = employerService.getAuthEmployer();
        Employee employee = findEmployeeById(id);
        if(employee != null && employer != null) {
            SavedEmployee savedEmployee = SavedEmployee.builder().employee(employee).employer(employer).build();
            save(savedEmployee);
        } else
            throw new NoSuchElementFoundException();
    }

    @Override
    @Transactional
    public void deleteEmployeeFromSaved(Long id) {
        Employer employer = employerService.getAuthEmployer();
        if(employer != null) {
            Optional<SavedEmployee> saved = findByIds(employer.getId(), id);
            if (saved.isPresent()) {
                delete(saved.get());
            } else
                throw new NoSuchElementFoundException();
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
