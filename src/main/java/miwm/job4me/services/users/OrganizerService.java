package miwm.job4me.services.users;

import javassist.NotFoundException;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.users.OrganizerDto;

import java.util.Optional;

public interface OrganizerService extends BaseService<Organizer, Long> {
    OrganizerDto getOrganizerDetails();
    OrganizerDto saveOrganizerDetails(OrganizerDto organizerDto);
    void updateAccountStatusAfterPayment();

    Optional<Organizer> getOrganizerByToken(String token);

    void updatePassword(Organizer organizer, String password);
}
