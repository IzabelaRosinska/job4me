package miwm.job4me.services.users;

import miwm.job4me.model.users.Employer;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.web.mappers.users.EmployerMapper;
import miwm.job4me.web.model.users.EmployerDto;

import javax.transaction.Transactional;
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
        employer.setCompanyName(employerDto.getCompanyName());
        employer.setDescription(employerDto.getDescription());
        employer.setDisplayDescription(employerDto.getDisplayDescription());
        employer.setTelephone(employerDto.getTelephone());
        employer.setEmail(employerDto.getEmail());
        employer.setContactEmail(employer.getContactEmail());
        if(employerDto.getPhoto() != null)
            employer.setPhoto(employerDto.getPhoto());
        if(employerDto.getAddress() != null)
        employer.setAddress(employerDto.getAddress());
        employerRepository.save(employer);
        return employerDto;
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
}
