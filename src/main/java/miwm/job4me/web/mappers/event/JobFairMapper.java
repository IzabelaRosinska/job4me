package miwm.job4me.web.mappers.event;

import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.web.model.event.JobFairDto;
import org.springframework.stereotype.Component;

@Component
public class JobFairMapper {
    public JobFairDto toDto(JobFair jobFair) {
        JobFairDto jobFairDto = new JobFairDto();
        jobFairDto.setId(jobFair.getId());
        jobFairDto.setName(jobFair.getName());
        jobFairDto.setOrganizerId(jobFair.getOrganizer().getId());
        jobFairDto.setDateStart(jobFair.getDateStart());
        jobFairDto.setDateEnd(jobFair.getDateEnd());
        jobFairDto.setAddress(jobFair.getAddress());
        jobFairDto.setDescription(jobFair.getDescription());
        jobFairDto.setDisplayDescription(jobFair.getDisplayDescription());
        jobFairDto.setPhoto(jobFair.getPhoto());
        return jobFairDto;
    }

    public JobFair toEntity(JobFairDto jobFairDto) {
        JobFair jobFair = new JobFair();
        jobFair.setId(jobFairDto.getId());
        jobFair.setName(jobFairDto.getName());
        jobFair.setOrganizer(Organizer.builder().id(jobFairDto.getOrganizerId()).build());
        jobFair.setDateStart(jobFairDto.getDateStart());
        jobFair.setDateEnd(jobFairDto.getDateEnd());
        jobFair.setAddress(jobFairDto.getAddress());
        jobFair.setDescription(jobFairDto.getDescription());
        jobFair.setDisplayDescription(jobFairDto.getDisplayDescription());
        jobFair.setPhoto(jobFairDto.getPhoto());
        return jobFair;
    }
}
