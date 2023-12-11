package miwm.job4me.web.controllers;

import lombok.val;
import miwm.job4me.utils.JsonMappingUtils;
import miwm.job4me.jwt.JwtConfig;
import miwm.job4me.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import miwm.job4me.model.users.Employee;
import miwm.job4me.services.offer.SavedOfferService;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.services.users.SavedEmployerService;
import miwm.job4me.web.model.users.EmployeeDto;
import miwm.job4me.web.model.users.EmployerReviewDto;
import miwm.job4me.web.model.users.OrganizerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = EmployeeController.class,
        excludeFilters =
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtUsernameAndPasswordAuthenticationFilter.class))
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @MockBean
    OrganizerService organizerService;

    @MockBean
    SavedOfferService savedOfferService;

    @MockBean
    SavedEmployerService savedEmployerService;

    @MockBean
    SecretKey secretKey;

    @MockBean
    JwtConfig jwtConfig;

    private Employee employee;
    private EmployeeDto employeeDto;

    private EmployerReviewDto savedEmployer;

    @BeforeEach
    public void setUp() {
        employee = Employee
                .builder()
                .id(1L)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jankowalski@gmail.com")
                .password("password")
                .userRole(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
                .contactEmail("jankowalski@gmail.com")
                .telephone("123456789")
                .education(null)
                .experience(null)
                .projects(null)
                .skills(null)
                .aboutMe("aboutMe")
                .interests("interests")
                .build();

        employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getContactEmail());
        employeeDto.setTelephone(employee.getTelephone());
        employeeDto.setEducation(null);
        employeeDto.setExperience(null);
        employeeDto.setProjects(null);
        employeeDto.setSkills(null);
        employeeDto.setAboutMe(employee.getAboutMe());
        employeeDto.setInterests(employee.getInterests());

        savedEmployer = new EmployerReviewDto();
        savedEmployer.setId(1L);
        savedEmployer.setEmail("saved@wp.pl");
    }

    @Test
    void getEmployeeAccountTest() throws Exception {
        // given
        when(employeeService.getEmployeeDetails()).thenReturn(employeeDto);
        // when
        val resultActions = mockMvc.perform(get("/employee/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMappingUtils.objectToJsonString(employeeDto)));
        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void updateEmployeeDetailsTest() throws Exception {
        // given
        when(employeeService.saveEmployeeDetails(employeeDto)).thenReturn(employeeDto);
        // when
        val resultActions = mockMvc.perform(post("/employee/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMappingUtils.objectToJsonString(employeeDto)));
        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    void getEmployerWithIdForEmployee() throws Exception {
        // given
        when(savedEmployerService.findEmployerWithIdByUser(savedEmployer.getId())).thenReturn(savedEmployer);
        // when
        String url = "/employee/employer/" + savedEmployer.getId() + "/account";
        val resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON));
        // then
        resultActions.andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        val result = (EmployerReviewDto) JsonMappingUtils.jsonStringToObject(contentAsString, EmployerReviewDto.class);
        assertThat(result.getId()).isEqualTo(1L);
    }
}