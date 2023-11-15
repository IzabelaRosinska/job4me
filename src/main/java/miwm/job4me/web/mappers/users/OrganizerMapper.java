package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Organizer;
import miwm.job4me.web.model.users.OrganizerDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizerMapper {

    public OrganizerDto organizerToOrganizerDto(Organizer organizer) {
        OrganizerDto organizerDto = new OrganizerDto();
        organizerDto.setId(organizer.getId());
        organizerDto.setOrganizerName(organizer.getOrganizerName());
        organizerDto.setDescription(organizer.getDescription());
        organizerDto.setTelephone(organizer.getTelephone());
        organizerDto.setEmail(organizer.getEmail());
        return organizerDto;
    }
}
