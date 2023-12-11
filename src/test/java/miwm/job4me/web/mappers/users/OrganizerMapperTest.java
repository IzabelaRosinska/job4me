package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Organizer;
import miwm.job4me.web.mappers.event.JobFairMapper;
import miwm.job4me.web.model.users.OrganizerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class OrganizerMapperTest {

    @Mock
    private JobFairMapper jobFairMapper;

    @InjectMocks
    private OrganizerMapper organizerMapper;

    private Organizer organizer;
    private OrganizerDto organizerDto;

    @BeforeEach
    void setUp() {
        // Initialize the Organizer and OrganizerDto with sample data
        organizer = new Organizer();
        organizer.setId(1L);
        organizer.setName("Organizer Name");
        organizer.setDescription("Organizer Description");
        organizer.setTelephone("1234567890");
        organizer.setContactEmail("organizer@example.com");

        organizerDto = new OrganizerDto();
        organizerDto.setId(1L);
        organizerDto.setName("Organizer Name");
        organizerDto.setDescription("Organizer Description");
        organizerDto.setTelephone("1234567890");
        organizerDto.setContactEmail("organizer@example.com");
    }

    @Test
    void testOrganizerToOrganizerDtoWhenCalledThenStateIsCorrect() {
        // Act
        OrganizerDto result = organizerMapper.organizerToOrganizerDto(organizer);

        // Assert
        assertEquals(organizer.getId(), result.getId());
        assertEquals(organizer.getName(), result.getName());
        assertEquals(organizer.getDescription(), result.getDescription());
        assertEquals(organizer.getTelephone(), result.getTelephone());
        assertEquals(organizer.getContactEmail(), result.getContactEmail());
    }

    @Test
    void testOrganizerDtoToOrganizerWhenCalledThenBehaviorIsCorrect() {
        // Arrange
        Organizer emptyOrganizer = new Organizer();

        // Act
        Organizer result = organizerMapper.organizerDtoToOrganizer(organizerDto, emptyOrganizer);

        // Assert
        assertEquals(organizerDto.getName(), result.getName());
        assertEquals(organizerDto.getDescription(), result.getDescription());
        assertEquals(organizerDto.getTelephone(), result.getTelephone());
        assertEquals(organizerDto.getContactEmail(), result.getContactEmail());
    }
}
