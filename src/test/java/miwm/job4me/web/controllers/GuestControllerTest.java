package miwm.job4me.web.controllers;

import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.web.model.users.EmployerDto;
import miwm.job4me.web.model.users.OrganizerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GuestController.class,
        excludeFilters =
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtUsernameAndPasswordAuthenticationFilter.class))
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployerService employerService;

    @MockBean
    private OrganizerService organizerService;

    @MockBean
    SecretKey secretKey;

    @MockBean
    JwtConfig jwtConfig;

    @Test
    public void testGetEmployerWhenEmployerFoundThenReturnOk() throws Exception {
        // Arrange
        EmployerDto employerDto = new EmployerDto();
        employerDto.setId(1L);
        employerDto.setCompanyName("P&G");
        when(employerService.findEmployerById(anyLong())).thenReturn(employerDto);

        // Act & Assert
        mockMvc.perform(get("/account/employer/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(employerDto.getId()))
                .andExpect(jsonPath("$.companyName").value(employerDto.getCompanyName()));
    }

    @Test
    public void testGetEmployerWhenEmployerNotFoundThenReturnNotFound() throws Exception {
        // Arrange
        when(employerService.findEmployerById(anyLong())).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/account/employer/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOrganizerWhenOrganizerFoundThenReturnOk() throws Exception {
        // Arrange
        OrganizerDto organizerDto = new OrganizerDto();
        organizerDto.setId(1L);
        organizerDto.setName("ABC");
        when(organizerService.findOrganizerById(anyLong())).thenReturn(organizerDto);

        // Act & Assert
        mockMvc.perform(get("/account/organizer/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(organizerDto.getId()))
                .andExpect(jsonPath("$.name").value(organizerDto.getName()));
    }

    @Test
    public void testGetOrganizerWhenOrganizerNotFoundThenReturnNotFound() throws Exception {
        // Arrange
        when(organizerService.findOrganizerById(anyLong())).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/account/organizer/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
