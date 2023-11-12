package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Person;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.web.mappers.users.EmployerMapper;
import miwm.job4me.web.model.users.EmployeeDto;
import miwm.job4me.web.model.users.EmployerDto;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public class EmployerServiceImpl implements EmployerService {

    private final UserAuthenticationService userAuthService;
    private final EmployerMapper employerMapper;
    private final EmployerRepository employerRepository;

    public EmployerServiceImpl(UserAuthenticationService userAuthService, EmployerMapper employerMapper, EmployerRepository employerRepository) {
        this.userAuthService = userAuthService;
        this.employerMapper = employerMapper;
        this.employerRepository = employerRepository;
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
        employerRepository.save(employerMapper.employerDtoToEmployer(employerDto, employer));
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
}
