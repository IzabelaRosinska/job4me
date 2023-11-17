package miwm.job4me.web.mappers.event;

import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.web.model.event.JobFairDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JobFairMapperTest {
    @InjectMocks
    private JobFairMapper jobFairMapper;
    private JobFair jobFair;
    private JobFairDto jobFairDto;
    private Long id = 1L;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        jobFair = JobFair.builder()
                .id(id)
                .name("name")
                .organizer(Organizer.builder().id(id).build())
                .dateStart(now)
                .dateEnd(now.plus(1, DAYS))
                .address("address")
                .description("description")
                .displayDescription("displayDescription")
                .photo("photo")
                .build();

        jobFairDto = new JobFairDto();
        jobFairDto.setId(jobFair.getId());
        jobFairDto.setName(jobFair.getName());
        jobFairDto.setOrganizerId(jobFair.getOrganizer().getId());
        jobFairDto.setDateStart(jobFair.getDateStart());
        jobFairDto.setDateEnd(jobFair.getDateEnd());
        jobFairDto.setAddress(jobFair.getAddress());
        jobFairDto.setDescription(jobFair.getDescription());
        jobFairDto.setDisplayDescription(jobFair.getDisplayDescription());
        jobFairDto.setPhoto(jobFair.getPhoto());
    }

    @Test
    @DisplayName("test toDto - should return dto with the same values as entity")
    public void testToDto() {
        JobFairDto jobFairDtoResult = jobFairMapper.toDto(jobFair);

        assertEquals(jobFairDto.getId(), jobFairDtoResult.getId());
        assertEquals(jobFairDto.getName(), jobFairDtoResult.getName());
        assertEquals(jobFairDto.getOrganizerId(), jobFairDtoResult.getOrganizerId());
        assertEquals(jobFairDto.getDateStart(), jobFairDtoResult.getDateStart());
        assertEquals(jobFairDto.getDateEnd(), jobFairDtoResult.getDateEnd());
        assertEquals(jobFairDto.getAddress(), jobFairDtoResult.getAddress());
        assertEquals(jobFairDto.getDescription(), jobFairDtoResult.getDescription());
        assertEquals(jobFairDto.getDisplayDescription(), jobFairDtoResult.getDisplayDescription());
        assertEquals(jobFairDto.getPhoto(), jobFairDtoResult.getPhoto());
    }

    @Test
    @DisplayName("test toEntity - should return entity with the same values as dto")
    public void testToEntity() {
        JobFair jobFairResult = jobFairMapper.toEntity(jobFairDto);

        assertEquals(jobFair.getId(), jobFairResult.getId());
        assertEquals(jobFair.getName(), jobFairResult.getName());
        assertEquals(jobFair.getOrganizer().getId(), jobFairResult.getOrganizer().getId());
        assertEquals(jobFair.getDateStart(), jobFairResult.getDateStart());
        assertEquals(jobFair.getDateEnd(), jobFairResult.getDateEnd());
        assertEquals(jobFair.getAddress(), jobFairResult.getAddress());
        assertEquals(jobFair.getDescription(), jobFairResult.getDescription());
        assertEquals(jobFair.getDisplayDescription(), jobFairResult.getDisplayDescription());
        assertEquals(jobFair.getPhoto(), jobFairResult.getPhoto());
    }
}
