package miwm.job4me.services.users;

import miwm.job4me.model.users.Organizer;
import miwm.job4me.services.BaseService;
import miwm.job4me.web.model.users.OrganizerDto;

public interface OrganizerService extends BaseService<Organizer, Long> {
    OrganizerDto getOrganizerDetails();
    OrganizerDto saveOrganizerDetails(OrganizerDto organizerDto);
}
