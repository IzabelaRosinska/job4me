package miwm.job4me.services.users;

import com.fasterxml.jackson.databind.JsonNode;
import miwm.job4me.exceptions.AuthException;
import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Account;
import miwm.job4me.repositories.users.EmployerRepository;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.users.EmployerMapper;
import miwm.job4me.web.model.users.EmployerDto;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployerServiceImpl implements EmployerService {
    private final EmployerMapper employerMapper;
    private final EmployerRepository employerRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdValidator idValidator;
    private final String ENTITY_NAME = "Employer";

    public EmployerServiceImpl(EmployerMapper employerMapper, EmployerRepository employerRepository, PasswordEncoder passwordEncoder, IdValidator idValidator) {
        this.employerMapper = employerMapper;
        this.employerRepository = employerRepository;
        this.passwordEncoder = passwordEncoder;
        this.idValidator = idValidator;
    }

    @Override
    public EmployerDto getEmployerDetails() {
        Employer employer = getAuthEmployer();
        EmployerDto employerDto = employerMapper.employerToEmployerDto(employer);
        return employerDto;
    }

    @Override
    @Transactional
    public EmployerDto saveEmployerDetails(EmployerDto employerDto) {
        if(employerDto != null) {
            Employer employer = getAuthEmployer();
            employerRepository.save(employerMapper.employerDtoToEmployer(employerDto, employer));
            return employerDto;
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    @Transactional
    public void saveEmployerDataFromLinkedin(Account user, JsonNode jsonNode) {
        if(user != null && jsonNode != null) {
            Employer employer = employerRepository.selectEmployerByUsername(user.getUsername());
            String name = jsonNode.get("name").asText();
            String photo = jsonNode.get("picture").asText();
            employer.setCompanyName(name);
            employer.setPhoto(photo);
            employerRepository.save(employer);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public Optional<Employer> getEmployerByToken(String token) {
        if(token != null) {
            Optional<Employer> employer = employerRepository.getEmployerByToken(token);
            return employer;

        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public void updatePassword(Employer employer, @Length(min = 5, max = 15) String password) {
        if(employer != null) {
            Employer updatedEmployer = employerRepository.selectEmployerByUsername(employer.getEmail());
            updatedEmployer.setPassword(passwordEncoder.encode(password));
            save(updatedEmployer);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }


    @Override
    public Set<Employer> findAll() {
        return (Set<Employer>) employerRepository.findAll();
    }

    @Override
    public Employer findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        Optional<Employer> employer = employerRepository.findById(id);
        if(employer.isPresent())
            return employer.get();
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
    }

    @Override
    public Employer save(Employer employer) {
        if(employer != null)
            return employerRepository.save(employer);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public void delete(Employer employer) {
        if(employer != null)
            employerRepository.delete(employer);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public void deleteById(Long id) {
        if(findById(id) != null)
            employerRepository.deleteById(id);
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
    }

    @Override
    public EmployerDto findEmployerById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        Optional<Employer> employer = employerRepository.findById(id);
        if(employer.isPresent())
            return employerMapper.employerToEmployerDto(employer.get());
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
    }

    @Override
    public Employer getAuthEmployer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employer employer = employerRepository.selectEmployerByUsername(authentication.getName());

        if (employer == null) {
            throw new AuthException(ExceptionMessages.unauthorized(ENTITY_NAME));
        }

        return employer;
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return employerRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }
}
