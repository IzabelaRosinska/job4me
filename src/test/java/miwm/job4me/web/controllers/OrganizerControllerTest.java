package miwm.job4me.web.controllers;

import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.web.model.users.EmployeeDto;
import miwm.job4me.web.model.users.EmployerDto;
import miwm.job4me.web.model.users.OrganizerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.crypto.SecretKey;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrganizerController.class,
        excludeFilters =
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtUsernameAndPasswordAuthenticationFilter.class))
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class OrganizerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizerService organizerService;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployerService employerService;

    @MockBean
    SecretKey secretKey;

    @MockBean
    JwtConfig jwtConfig;

    @Test
    public void testGetOrganizerAccountWhenDetailsExistThenReturnOk() throws Exception {
        OrganizerDto organizerDto = new OrganizerDto();
        Mockito.when(organizerService.getOrganizerDetails()).thenReturn(organizerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/organizer/account"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetOrganizerAccountWhenDetailsDoNotExistThenReturnNotFound() throws Exception {
        Mockito.when(organizerService.getOrganizerDetails()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/organizer/account"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateOrganizerAccountWhenDetailsAreUpdatedThenReturnCreated() throws Exception {
        OrganizerDto organizerDto = new OrganizerDto();
        Mockito.when(organizerService.saveOrganizerDetails(any(OrganizerDto.class))).thenReturn(organizerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/organizer/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetEmployerWithIdForOrganizerWhenIdIsValidThenReturnOk() throws Exception {
        EmployerDto employerDto = new EmployerDto();
        Mockito.when(employerService.findEmployerById(anyLong())).thenReturn(employerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/organizer/employer/1/account"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetEmployerWithIdForOrganizerWhenIdIsInvalidThenReturnNotFound() throws Exception {
        Mockito.when(employerService.findEmployerById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/organizer/employer/999/account"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmployeeWithIdForOrganizerWhenIdIsValidThenReturnOk() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto();
        Mockito.when(employeeService.findEmployeeById(anyLong())).thenReturn(employeeDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/organizer/employee/1/account"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetEmployeeWithIdForOrganizerWhenIdIsInvalidThenReturnNotFound() throws Exception {
        Mockito.when(employeeService.findEmployeeById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/organizer/employee/999/account"))
                .andExpect(status().isOk());
    }
}
