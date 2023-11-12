package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Person;
import miwm.job4me.model.users.SavedEmployee;
import miwm.job4me.repositories.users.EmployeeRepository;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.repositories.users.SavedEmployeeRepository;
import miwm.job4me.web.mappers.users.EmployerMapper;
import miwm.job4me.web.model.users.EmployerDto;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public class EmployerServiceImpl implements EmployerService {

    private final UserAuthenticationService userAuthService;
    private final EmployerMapper employerMapper;
    private final EmployerRepository employerRepository;
    private final EmployeeRepository employeeRepository;
    private final SavedEmployeeRepository savedEmployeeRepository;

    public EmployerServiceImpl(UserAuthenticationService userAuthService, EmployerMapper employerMapper, EmployerRepository employerRepository, EmployeeRepository employeeRepository, SavedEmployeeRepository savedEmployeeRepository) {
        this.userAuthService = userAuthService;
        this.employerMapper = employerMapper;
        this.employerRepository = employerRepository;
        this.employeeRepository = employeeRepository;
        this.savedEmployeeRepository = savedEmployeeRepository;
    }

    @Override
    public EmployerDto getEmployerDetails() {
        Employer employer = userAuthService.getAuthenticatedEmployer();
        EmployerDto employerDto = employerMapper.employerToEmployerDto(employer);
        return employerDto;
    }

    @Override
    @Transactional
    public EmployerDto saveEmployerDetails(EmployerDto employerDto) {
        Employer employer = userAuthService.getAuthenticatedEmployer();
        employer.setCompanyName(employerDto.getCompanyName());
        employer.setContactEmail(employer.getContactEmail());
        employer.setDescription(employerDto.getDescription());
        employer.setDisplayDescription(employerDto.getDisplayDescription());
        employer.setTelephone(employerDto.getTelephone());
        employer.setEmail(employerDto.getEmail());
        if(employerDto.getPhoto() != null)
            employer.setPhoto(employerDto.getPhoto());
        if(employerDto.getAddress() != null)
        employer.setAddress(employerDto.getAddress());
        employerRepository.save(employer);
        return employerDto;
    }

    @Override
    @Transactional
    public void saveEmployerDataFromLinkedin(Person user, JsonNode jsonNode) {
        Employer employer = employerRepository.selectEmployerByUsername(user.getUsername());
        String name = jsonNode.get("name").asText();
        String photo = jsonNode.get("picture").asText();
        employer.setCompanyName(name);
        employer.setPhoto(photo);
        employerRepository.save(employer);
    }


    @Override
    public Set<Employer> findAll() {
        return null;
    }

    @Override
    public Employer findById(Long aLong) {
        return null;
    }

    @Override
    public Employer save(Employer object) {
        return null;
    }

    @Override
    public void delete(Employer object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public EmployerDto findEmployerById(Long id) {
        Optional<Employer> employer = employerRepository.findById(id);
        if(employer.isPresent())
            return employerMapper.employerToEmployerDto(employer.get());
        else
            throw new NoSuchElementFoundException();
    }

    @Override
    @Transactional
    public void addEmployeeToSaved(Long id) {
        Employer employer = userAuthService.getAuthenticatedEmployer();
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent() && employer != null) {
            SavedEmployee savedEmployee = SavedEmployee.builder().employee(employee.get()).employer(employer).build();
            savedEmployeeRepository.save(savedEmployee);
        } else
            throw new NoSuchElementFoundException();
    }

    @Override
    @Transactional
    public void deleteEmployeeFromSaved(Long id) {
        Employer employer = userAuthService.getAuthenticatedEmployer();
        if(employer != null) {
            Optional<SavedEmployee> saved = savedEmployeeRepository.findByIds(employer.getId(), id);
            if (saved.isPresent()) {
                savedEmployeeRepository.delete(saved.get());
            } else
                throw new NoSuchElementFoundException();
        }
    }
}
