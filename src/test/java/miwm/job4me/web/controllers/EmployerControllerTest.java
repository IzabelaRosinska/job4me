package miwm.job4me.web.controllers;

import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.services.users.SavedEmployeeService;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.users.EmployeeReviewDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployerController.class,
        excludeFilters =
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtUsernameAndPasswordAuthenticationFilter.class))
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployerService employerService;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private SavedEmployeeService savedEmployeeService;

    @MockBean
    private OrganizerService organizerService;

    @MockBean
    SecretKey secretKey;

    @MockBean
    JwtConfig jwtConfig;

    @Test
    public void testGetEmployerAccount() throws Exception {
        // Arrange
        EmployerDto employerDto = new EmployerDto();
        employerDto.setId(1L);
        employerDto.setCompanyName("P&G");
        when(employerService.getEmployerDetails()).thenReturn(employerDto);

        // Act & Assert
        mockMvc.perform(get("/employer/account")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(employerDto.getId()))
                .andExpect(jsonPath("$.companyName").value(employerDto.getCompanyName()));
    }

    @Test
    public void testUpdateEmployerAccount() throws Exception {
        // Arrange
        EmployerDto employerDto = new EmployerDto();
        employerDto.setId(1L);
        employerDto.setCompanyName("P&G");
        when(employerService.saveEmployerDetails(any(EmployerDto.class))).thenReturn(employerDto);

        // Act & Assert
        mockMvc.perform(post("/employer/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"companyName\": \"P&G\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(employerDto.getId()))
                .andExpect(jsonPath("$.companyName").value(employerDto.getCompanyName()));
    }

    @Test
    public void testGetOrganizerWithIdForEmployer() throws Exception {
        // Arrange
        OrganizerDto organizerDto = new OrganizerDto();
        organizerDto.setId(1L);
        organizerDto.setName("ABC");
        when(organizerService.findOrganizerById(anyLong())).thenReturn(organizerDto);

        // Act & Assert
        mockMvc.perform(get("/employer/organizer/{id}/account", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(organizerDto.getId()))
                .andExpect(jsonPath("$.name").value(organizerDto.getName()));
    }

    @Test
    public void testGetEmployeeWithIdForEmployer() throws Exception {
        // Arrange
        EmployeeReviewDto employeeReviewDto = new EmployeeReviewDto();
        employeeReviewDto.setId(1L);
        employeeReviewDto.setFirstName("John Doe");
        when(savedEmployeeService.findEmployeeWithIdByUser(anyLong())).thenReturn(employeeReviewDto);

        // Act & Assert
        mockMvc.perform(get("/employer/employee/{id}/account", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(employeeReviewDto.getId()))
                .andExpect(jsonPath("$.firstName").value(employeeReviewDto.getFirstName()));
    }

    @Test
    public void testSaveEmployeeForEmployer() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/employer/save-employee/{id}", 1L))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteEmployeeForEmployer() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/employer/delete-employee/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSavedEmployees() throws Exception {
        // Arrange
        List<EmployeeReviewDto> employees = new ArrayList<>();
        EmployeeReviewDto employee1 = new EmployeeReviewDto();
        employee1.setId(1L);
        employee1.setFirstName("John Doe");
        EmployeeReviewDto employee2 = new EmployeeReviewDto();
        employee2.setId(2L);
        employee2.setFirstName("Jane Smith");
        employees.add(employee1);
        employees.add(employee2);
        when(savedEmployeeService.getSavedEmployees()).thenReturn(employees);

        // Act & Assert
        mockMvc.perform(get("/employer/get-saved-employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[0].firstName").value(employee1.getFirstName()))
                .andExpect(jsonPath("$[1].id").value(employee2.getId()));
    }

    @Test
    public void testGetAllSavedEmployees() throws Exception {
        // Arrange
        Page<ListDisplayDto> employeeDtoPage = new PageImpl<>(new ArrayList<>());
        when(savedEmployeeService.getSavedEmployeesForEmployerWithIdListDisplay(anyInt(), anyInt())).thenReturn(employeeDtoPage);

        // Act & Assert
        mockMvc.perform(get("/employer/saved/employees")
                        .param("page", "0")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
