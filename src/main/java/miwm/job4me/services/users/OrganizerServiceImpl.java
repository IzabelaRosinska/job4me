package miwm.job4me.services.users;

import miwm.job4me.model.users.Organizer;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.web.mappers.users.OrganizerMapper;
import miwm.job4me.web.model.users.OrganizerDto;
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
        Organizer organizer = getAuthOrganizer();
        organizer.setOrganizerName(organizerDto.getOrganizerName());
        organizer.setDescription(organizerDto.getDescription());
        organizer.setTelephone(organizerDto.getTelephone());
        organizer.setEmail(organizerDto.getEmail());
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
            return null;
    }

    @Override
    @Transactional
    public void updatePassword(Organizer organizer, String password) {
        organizer.setPassword(passwordEncoder.encode(password));
        save(organizer);
    }

    private Organizer getAuthOrganizer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Organizer organizer = organizerRepository.selectOrganizerByUsername(authentication.getName());
        return organizer;
    }
}
