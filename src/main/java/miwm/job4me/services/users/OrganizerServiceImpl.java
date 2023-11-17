package miwm.job4me.services.users;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.web.mappers.users.OrganizerMapper;
import miwm.job4me.web.model.users.OrganizerDto;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerMapper organizerMapper;
    private final OrganizerRepository organizerRepository;
    private final PasswordEncoder passwordEncoder;

    public OrganizerServiceImpl(OrganizerMapper organizerMapper, OrganizerRepository organizerRepository, PasswordEncoder passwordEncoder) {
        this.organizerMapper = organizerMapper;
        this.organizerRepository = organizerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OrganizerDto findOrganizerById(Long id) {
        Optional<Organizer> organizer = organizerRepository.findById(id);
        if(organizer.isPresent())
            return organizerMapper.organizerToOrganizerDto(organizer.get());
        else
            throw new NoSuchElementFoundException();
    }

    @Override
    public Set<Organizer> findAll() {
        return null;
    }

    @Override
    public Organizer findById(Long aLong) {
        return null;
    }

    @Override
    public Organizer save(Organizer object) {
        return null;
    }

    @Override
    public void delete(Organizer object) {

    }

    @Override
    public void deleteById(Long aLong) {

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
        Organizer organizer = organizerMapper.organizerDtoToOrganizer(organizerDto, getAuthOrganizer());
        organizerRepository.save(organizer);
        return organizerDto;
    }

    @Override
    @Transactional
    public void updateAccountStatusAfterPayment() {
        Organizer organizer = getAuthOrganizer();
        organizer.setUserRole(new SimpleGrantedAuthority("ROLE_ORGANIZER_ENABLED"));
        organizer.setLocked(false);
        organizerRepository.save(organizer);
    }

    @Override
    public Optional<Organizer> getOrganizerByToken(String token) {
        Optional<Organizer> organizer = organizerRepository.getOrganizerByToken(token);
        if(organizer.isPresent())
            return organizer;
        else
            throw new NoSuchElementFoundException("Organizer with given token id not found");
    }

    @Override
    @Transactional
    public void updatePassword(Organizer organizer, @Length(min = 5, max = 15) String password) {
        organizer.setPassword(passwordEncoder.encode(password));
        save(organizer);
    }

    private Organizer getAuthOrganizer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Organizer organizer = organizerRepository.selectOrganizerByUsername(authentication.getName());
        return organizer;
    }

}
