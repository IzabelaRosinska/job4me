package miwm.job4me.services.users;

import miwm.job4me.model.users.Organizer;
import miwm.job4me.repositories.users.OrganizerRepository;
import miwm.job4me.security.ApplicationUserRole;
import miwm.job4me.web.mappers.users.OrganizerMapper;
import miwm.job4me.web.model.users.OrganizerDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Set;

public class OrganizerServiceImpl implements OrganizerService {

    private final UserAuthenticationService userAuthenticationService;
    private final OrganizerMapper organizerMapper;
    private final OrganizerRepository organizerRepository;

    public OrganizerServiceImpl(UserAuthenticationService userAuthenticationService, OrganizerMapper organizerMapper, OrganizerRepository organizerRepository) {
        this.userAuthenticationService = userAuthenticationService;
        this.organizerMapper = organizerMapper;
        this.organizerRepository = organizerRepository;
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
        Organizer organizer = userAuthenticationService.getAuthenticatedOrganizer();
        OrganizerDto organizerDto = organizerMapper.organizerToOrganizerDto(organizer);
        return organizerDto;
    }

    @Override
    @Transactional
    public OrganizerDto saveOrganizerDetails(OrganizerDto organizerDto) {
        Organizer organizer = userAuthenticationService.getAuthenticatedOrganizer();
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
        Organizer organizer = userAuthenticationService.getAuthenticatedOrganizer();
        organizer.setUserRole(new SimpleGrantedAuthority("ROLE_ORGANIZER_ENABLED"));
        organizer.setLocked(false);
        organizerRepository.save(organizer);
    }


}
