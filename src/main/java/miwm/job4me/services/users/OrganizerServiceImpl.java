package miwm.job4me.services.users;

import miwm.job4me.model.users.Organizer;
import miwm.job4me.web.mappers.users.OrganizerMapper;
import miwm.job4me.web.model.users.OrganizerDto;

import java.util.Set;

public class OrganizerServiceImpl implements OrganizerService {

    private final UserAuthenticationService userAuthenticationService;
    private final OrganizerMapper organizerMapper;

    public OrganizerServiceImpl(UserAuthenticationService userAuthenticationService, OrganizerMapper organizerMapper) {
        this.userAuthenticationService = userAuthenticationService;
        this.organizerMapper= organizerMapper;
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
    public OrganizerDto saveOrganizerDetails(OrganizerDto organizerDto) {
        Organizer organizer = userAuthenticationService.getAuthenticatedOrganizer();
        organizer.setOrganizerName(organizerDto.getOrganizerName());
        organizer.setDescription(organizerDto.getDescription());
        organizer.setTelephone(organizerDto.getTelephone());
        organizer.setEmail(organizerDto.getEmail());
        return organizerDto;
    }
}
