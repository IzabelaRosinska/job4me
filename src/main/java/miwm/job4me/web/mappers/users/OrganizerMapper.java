package miwm.job4me.web.mappers.users;

import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.web.mappers.event.JobFairMapper;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.users.OrganizerDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class OrganizerMapper {

    private final JobFairMapper fairMapper;

    public OrganizerMapper(JobFairMapper fairMapper) {
        this.fairMapper = fairMapper;
    }

    public OrganizerDto organizerToOrganizerDto(Organizer organizer) {
        OrganizerDto organizerDto = new OrganizerDto();
        organizerDto.setId(organizer.getId());
        organizerDto.setName(organizer.getName());
        organizerDto.setDescription(organizer.getDescription());
        organizerDto.setTelephone(organizer.getTelephone());
        organizerDto.setContactEmail(organizer.getContactEmail());
        organizerDto.setFairs(getJobFairList(organizer.getFairs()));
        return organizerDto;
    }

    public Organizer organizerDtoToOrganizer(OrganizerDto organizerDto, Organizer organizer) {
        organizer.setName(organizerDto.getName());
        organizer.setDescription(organizerDto.getDescription());
        organizer.setTelephone(organizerDto.getTelephone());
        organizer.setContactEmail(organizerDto.getContactEmail());
        organizer.setFairs(setJobFairList(organizerDto.getFairs()));
        return organizer;
    }

    private ArrayList<JobFairDto> getJobFairList(Set<JobFair> fairs) {
        ArrayList<JobFairDto> fairList = new ArrayList<>();
        if (fairs != null)
            for (JobFair fair : fairs)
                fairList.add(fairMapper.toDto(fair));
        return fairList;
    }

    private Set<JobFair> setJobFairList(ArrayList<JobFairDto> fairs) {
        Set<JobFair> fairList = new HashSet<>();
        if (fairs != null)
            for (JobFairDto fair : fairs)
                fairList.add(fairMapper.toEntity(fair));
        return fairList;
    }
}
