package miwm.job4me.services.users;

import miwm.job4me.model.users.Organizer;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.users.OrganizerDto;
import java.util.Optional;

public interface OrganizerService extends BaseService<Organizer, Long> {
    OrganizerDto getOrganizerDetails();

    OrganizerDto saveOrganizerDetails(OrganizerDto organizerDto);

    Optional<Organizer> getOrganizerByToken(String token);

    void updatePassword(Organizer organizer, String password);

    OrganizerDto findOrganizerById(Long id);

    boolean existsById(Long id);

    void strictExistsById(Long id);

    String getContactEmail(Long id);

    Organizer getAuthOrganizer();
}
