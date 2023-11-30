package miwm.job4me.services.users;

import miwm.job4me.exceptions.AuthException;
import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.users.OrganizerMapper;
import miwm.job4me.web.model.users.OrganizerDto;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static miwm.job4me.messages.AppMessages.ROLE_ORGANIZER_ENABLED;

@Service
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerMapper organizerMapper;
    private final OrganizerRepository organizerRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdValidator idValidator;
    private final String ENTITY_NAME = "Organizer";

    public OrganizerServiceImpl(OrganizerMapper organizerMapper, OrganizerRepository organizerRepository, PasswordEncoder passwordEncoder, IdValidator idValidator) {
        this.organizerMapper = organizerMapper;
        this.organizerRepository = organizerRepository;
        this.passwordEncoder = passwordEncoder;
        this.idValidator = idValidator;
    }

    @Override
    public OrganizerDto findOrganizerById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        Optional<Organizer> organizer = organizerRepository.findById(id);
        if (organizer.isPresent())
            return organizerMapper.organizerToOrganizerDto(organizer.get());
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return organizerRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public String getContactEmail(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        Organizer organizer = findById(id);
        if(organizer != null) {
            if (organizer.getContactEmail() == null) {
                return organizer.getEmail();
            } else {
                return organizer.getContactEmail();
            }
        } else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
    }

    @Override
    public Set<Organizer> findAll() {
        return new HashSet<>(organizerRepository.findAll());
    }

    @Override
    public Organizer findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return organizerRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public Organizer save(Organizer organizer) {
        if(organizer != null)
            return organizerRepository.save(organizer);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public void delete(Organizer organizer) {
        if(organizer != null)
            organizerRepository.delete(organizer);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        if(findById(id) != null)
            organizerRepository.deleteById(id);
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
    }

    @Override
    public OrganizerDto getOrganizerDetails() {
        Organizer organizer = getAuthOrganizer();
        OrganizerDto organizerDto = organizerMapper.organizerToOrganizerDto(organizer);
        return organizerDto;
    }

    @Override
    @Transactional
    public OrganizerDto saveOrganizerDetails(OrganizerDto organizerDto) {
        if(organizerDto != null) {
            Organizer organizer = organizerMapper.organizerDtoToOrganizer(organizerDto, getAuthOrganizer());
            organizerRepository.save(organizer);
            return organizerDto;
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public Optional<Organizer> getOrganizerByToken(String token) {
        if(token != null) {
            Optional<Organizer> organizer = organizerRepository.getOrganizerByToken(token);
            if (organizer.isPresent())
                return organizer;
            else
                throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, "token", token));
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument("Token"));
    }

    @Override
    @Transactional
    public void updatePassword(Organizer organizer, @Length(min = 5, max = 15) String password) {
        if(organizer != null) {
            organizer.setPassword(passwordEncoder.encode(password));
            save(organizer);
        } else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
    }

    @Override
    public Organizer getAuthOrganizer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Organizer organizer = organizerRepository.selectOrganizerByUsername(authentication.getName());

        if (organizer == null) {
            throw new AuthException(ExceptionMessages.unauthorized(ENTITY_NAME));
        }

        return organizer;
    }

}
