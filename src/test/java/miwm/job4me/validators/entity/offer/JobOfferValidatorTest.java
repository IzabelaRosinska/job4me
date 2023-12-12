package miwm.job4me.validators.entity.offer;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.description.ExtraSkill;
import miwm.job4me.model.offer.description.Requirement;
import miwm.job4me.model.offer.parameters.*;
import miwm.job4me.model.users.Employer;
import miwm.job4me.validators.fields.IntegerRangeValidator;
import miwm.job4me.validators.fields.ListValidator;
import miwm.job4me.validators.fields.StringFieldValidator;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class JobOfferValidatorTest {
    @Mock
    private IntegerRangeValidator integerRangeValidator;

    @Mock
    private StringFieldValidator stringFieldValidator;

    @Mock
    private ListValidator listValidator;

    @InjectMocks
    private JobOfferValidator jobOfferValidator;

    private JobOffer jobOffer;
    private JobOfferDto jobOfferDto;

    private final int MIN_OFFER_NAME_LENGTH = 1;
    private final int MAX_OFFER_NAME_LENGTH = 120;
    private final int MIN_WORKING_TIME_LENGTH = 1;
    private final int MAX_WORKING_TIME_LENGTH = 20;
    private final int MIN_DUTIES_LENGTH = 1;
    private final int MAX_DUTIES_LENGTH = 1000;
    private final int MIN_DESCRIPTION_LENGTH = 1;
    private final int MAX_DESCRIPTION_LENGTH = 1000;

    private final int MIN_SIZE_INDUSTRIES = 1;
    private final int MAX_SIZE_INDUSTRIES = 10;
    private final int MAX_LENGTH_INDUSTRY = 100;

    private final int MIN_SIZE_LOCALIZATIONS = 1;
    private final int MAX_SIZE_LOCALIZATIONS = 10;
    private final int MAX_LENGTH_LOCALIZATION = 50;

    private final int MIN_SIZE_EMPLOYMENT_FORMS = 1;
    private final int MAX_LENGTH_EMPLOYMENT_FORM = 50;

    private final int MAX_LENGTH_CONTRACT_TYPE = 50;

    private final int MAX_LENGTH_LEVEL = 50;

    private final int MIN_SIZE_REQUIREMENTS = 1;
    private final int MAX_SIZE_REQUIREMENTS = 15;
    private final int MAX_LENGTH_REQUIREMENT = 250;

    private final int MAX_SIZE_EXTRA_SKILLS = 10;
    private final int MAX_LENGTH_EXTRA_SKILL = 200;

    private final String ENTITY_NAME = "JobOffer";

    @BeforeEach
    void setUp() {
        Employer employer = Employer.builder().id(1L).build();

        HashSet<Industry> industries = new HashSet<>();
        industries.add(Industry.builder().name("industry 1").build());

        HashSet<Localization> localizations = new HashSet<>();
        localizations.add(Localization.builder().city("city 1").build());

        HashSet<EmploymentForm> employmentForms = new HashSet<>();
        employmentForms.add(EmploymentForm.builder().id(1L).name("employment form 1").build());
        employmentForms.add(EmploymentForm.builder().id(2L).name("employment form 2").build());

        HashSet<ContractType> contractTypes = new HashSet<>();
        contractTypes.add(ContractType.builder().name("contract type 1").build());

        HashSet<Level> levels = new HashSet<>();
        levels.add(Level.builder().name("level 1").build());

        HashSet<Requirement> requirements = new HashSet<>();
        requirements.add(Requirement.builder().description("requirement 1").build());

        HashSet<ExtraSkill> extraSkills = new HashSet<>();
        extraSkills.add(ExtraSkill.builder().description("extra skill 1").build());

        ArrayList<String> industriesDto = new ArrayList<>();
        industriesDto.add("industry 1");

        ArrayList<String> localizationsDto = new ArrayList<>();
        localizationsDto.add("city 1");

        ArrayList<String> employmentFormsDto = new ArrayList<>();
        employmentFormsDto.add("employment form 1");
        employmentFormsDto.add("employment form 2");

        ArrayList<String> contractTypesDto = new ArrayList<>();
        contractTypesDto.add("contract type 1");

        ArrayList<String> levelsDto = new ArrayList<>();
        levelsDto.add("level 1");

        ArrayList<String> requirementsDto = new ArrayList<>();
        requirementsDto.add("requirement 1");

        ArrayList<String> extraSkillsDto = new ArrayList<>();
        extraSkillsDto.add("extra skill 1");

        jobOffer = JobOffer.builder()
                .id(1L)
                .offerName("offerName")
                .employer(employer)
                .industries(industries)
                .localizations(localizations)
                .employmentForms(employmentForms)
                .salaryFrom(1000)
                .salaryTo(2000)
                .contractTypes(contractTypes)
                .workingTime("working time")
                .levels(levels)
                .requirements(requirements)
                .extraSkills(extraSkills)
                .duties("duties")
                .description("description")
                .isActive(true)
                .build();

        jobOfferDto = new JobOfferDto();
        jobOfferDto.setId(jobOffer.getId());
        jobOfferDto.setOfferName(jobOffer.getOfferName());
        jobOfferDto.setEmployerId(jobOffer.getEmployer().getId());
        jobOfferDto.setIndustries(industriesDto);
        jobOfferDto.setLocalizations(localizationsDto);
        jobOfferDto.setEmploymentForms(employmentFormsDto);
        jobOfferDto.setSalaryFrom(jobOffer.getSalaryFrom());
        jobOfferDto.setSalaryTo(jobOffer.getSalaryTo());
        jobOfferDto.setContractTypes(contractTypesDto);
        jobOfferDto.setWorkingTime(jobOffer.getWorkingTime());
        jobOfferDto.setLevels(levelsDto);
        jobOfferDto.setRequirements(requirementsDto);
        jobOfferDto.setExtraSkills(extraSkillsDto);
        jobOfferDto.setDuties(jobOffer.getDuties());
        jobOfferDto.setDescription(jobOffer.getDescription());
        jobOfferDto.setIsActive(jobOffer.getIsActive());
    }

    @Test
    @DisplayName("test validate - pass when jobOffer is valid")
    void testValidatePassValidJobOffer() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(integerRangeValidator).validateSalaryRange(jobOffer.getSalaryFrom(), jobOffer.getSalaryTo(), ENTITY_NAME);
        assertDoesNotThrow(() -> jobOfferValidator.validate(jobOffer));
    }

    @Test
    @DisplayName("test validate - fail and throw InvalidArgumentException when jobOffer is null")
    void testValidateFailWhenJobOfferIsNull() {
        try {
            jobOfferValidator.validate(null);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail when StringFieldValidator fails (offerName is null)")
    void testValidateFailWhenOfferNameIsNull() {
        jobOffer.setOfferName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("offerName", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);

        try {
            jobOfferValidator.validate(jobOffer);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("offerName", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail when StringFieldValidator fails (workingTime is null)")
    void testValidateFailWhenWorkingTimeIsNull() {
        jobOffer.setWorkingTime(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("workingTime", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);

        try {
            jobOfferValidator.validate(jobOffer);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("workingTime", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail when StringFieldValidator fails (duties is null)")
    void testValidateFailWhenDutiesIsNull() {
        jobOffer.setDuties(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("duties", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);

        try {
            jobOfferValidator.validate(jobOffer);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("duties", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail when StringFieldValidator fails (description is null)")
    void testValidateFailWhenDescriptionIsNull() {
        jobOffer.setDescription(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("description", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            jobOfferValidator.validate(jobOffer);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("description", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validate - fail when IntegerRangeValidator fails (salaryFrom is null)")
    void testValidateFailWhenSalaryFromIsNull() {
        jobOffer.setSalaryFrom(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOffer.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNull(ENTITY_NAME, "salaryFrom"))).when(integerRangeValidator).validateSalaryRange(jobOffer.getSalaryFrom(), jobOffer.getSalaryTo(), ENTITY_NAME);

        try {
            jobOfferValidator.validate(jobOffer);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "salaryFrom"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - pass when jobOfferDto is valid")
    void testValidateDtoPassValidJobOfferDto() {
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getIndustries(), "industries", ENTITY_NAME, MIN_SIZE_INDUSTRIES, MAX_SIZE_INDUSTRIES, MAX_LENGTH_INDUSTRY);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getLocalizations(), "localizations", ENTITY_NAME, MIN_SIZE_LOCALIZATIONS, MAX_SIZE_LOCALIZATIONS, MAX_LENGTH_LOCALIZATION);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getEmploymentForms(), "employmentForms", ENTITY_NAME, MAX_LENGTH_EMPLOYMENT_FORM);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getContractTypes(), "contractTypes", ENTITY_NAME, MAX_LENGTH_CONTRACT_TYPE);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getLevels(), "levels", ENTITY_NAME, MAX_LENGTH_LEVEL);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getRequirements(), "requirements", ENTITY_NAME, MIN_SIZE_REQUIREMENTS, MAX_SIZE_REQUIREMENTS, MAX_LENGTH_REQUIREMENT);
        doNothing().when(listValidator).validateListSizeAndElemLength(jobOfferDto.getExtraSkills(), "extraSkills", ENTITY_NAME, MAX_SIZE_EXTRA_SKILLS, MAX_LENGTH_EXTRA_SKILL);
        doNothing().when(integerRangeValidator).validateSalaryRange(jobOfferDto.getSalaryFrom(), jobOfferDto.getSalaryTo(), ENTITY_NAME);
        assertDoesNotThrow(() -> jobOfferValidator.validateDto(jobOfferDto));
    }

    @Test
    @DisplayName("test validateDto - fail and throw InvalidArgumentException when jobOfferDto is null")
    void testValidateDtoFailWhenJobOfferDtoIsNull() {
        try {
            jobOfferValidator.validateDto(null);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.nullArgument(ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when StringFieldValidator fails (offerName is null)")
    void testValidateDtoFailWhenOfferNameIsNull() {
        jobOfferDto.setOfferName(null);

        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("offerName", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("offerName", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when StringFieldValidator fails (workingTime is null)")
    void testValidateDtoFailWhenWorkingTimeIsNull() {
        jobOfferDto.setWorkingTime(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("workingTime", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("workingTime", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when StringFieldValidator fails (duties is null)")
    void testValidateDtoFailWhenDutiesIsNull() {
        jobOfferDto.setDuties(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("duties", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("duties", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when StringFieldValidator fails (description is null)")
    void testValidateDtoFailWhenDescriptionIsNull() {
        jobOfferDto.setDescription(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("description", ENTITY_NAME))).when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("description", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when IntegerRangeValidator fails (salaryFrom is null)")
    void testValidateDtoFailWhenSalaryFromIsNull() {
        jobOfferDto.setSalaryFrom(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNull(ENTITY_NAME, "salaryFrom"))).when(integerRangeValidator).validateSalaryRange(jobOfferDto.getSalaryFrom(), jobOfferDto.getSalaryTo(), ENTITY_NAME);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNull(ENTITY_NAME, "salaryFrom"), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when ListValidator fails (industries is null)")
    void testValidateDtoFailWhenIndustriesIsNull() {
        jobOfferDto.setIndustries(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("industries", ENTITY_NAME))).when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getIndustries(), "industries", ENTITY_NAME, MIN_SIZE_INDUSTRIES, MAX_SIZE_INDUSTRIES, MAX_LENGTH_INDUSTRY);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("industries", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when ListValidator fails (localizations is null)")
    void testValidateDtoFailWhenLocalizationsIsNull() {
        // given
        jobOfferDto.setLocalizations(null);

        // when
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getIndustries(), "industries", ENTITY_NAME, MIN_SIZE_INDUSTRIES, MAX_SIZE_INDUSTRIES, MAX_LENGTH_INDUSTRY);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("localizations", ENTITY_NAME))).when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getLocalizations(), "localizations", ENTITY_NAME, MIN_SIZE_LOCALIZATIONS, MAX_SIZE_LOCALIZATIONS, MAX_LENGTH_LOCALIZATION);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("localizations", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when ListValidator fails (employmentForms is null)")
    void testValidateDtoFailWhenEmploymentFormsIsNull() {
        jobOfferDto.setEmploymentForms(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("employmentForms", ENTITY_NAME))).when(listValidator).validateRequiredList(jobOfferDto.getEmploymentForms(), "employmentForms", ENTITY_NAME, MAX_LENGTH_EMPLOYMENT_FORM);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("employmentForms", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when ListValidator fails (contractTypes is null)")
    void testValidateDtoFailWhenContractTypesIsNull() {
        jobOfferDto.setContractTypes(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getIndustries(), "industries", ENTITY_NAME, MIN_SIZE_INDUSTRIES, MAX_SIZE_INDUSTRIES, MAX_LENGTH_INDUSTRY);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getLocalizations(), "localizations", ENTITY_NAME, MIN_SIZE_LOCALIZATIONS, MAX_SIZE_LOCALIZATIONS, MAX_LENGTH_LOCALIZATION);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getEmploymentForms(), "employmentForms", ENTITY_NAME, MAX_LENGTH_EMPLOYMENT_FORM);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("contractTypes", ENTITY_NAME))).when(listValidator).validateRequiredList(jobOfferDto.getContractTypes(), "contractTypes", ENTITY_NAME, MAX_LENGTH_CONTRACT_TYPE);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("contractTypes", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when ListValidator fails (levels is null)")
    void testValidateDtoFailWhenLevelsIsNull() {
        jobOfferDto.setLevels(null);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getIndustries(), "industries", ENTITY_NAME, MIN_SIZE_INDUSTRIES, MAX_SIZE_INDUSTRIES, MAX_LENGTH_INDUSTRY);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getLocalizations(), "localizations", ENTITY_NAME, MIN_SIZE_LOCALIZATIONS, MAX_SIZE_LOCALIZATIONS, MAX_LENGTH_LOCALIZATION);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getEmploymentForms(), "employmentForms", ENTITY_NAME, MAX_LENGTH_EMPLOYMENT_FORM);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getContractTypes(), "contractTypes", ENTITY_NAME, MAX_LENGTH_CONTRACT_TYPE);
        doThrow(new InvalidArgumentException(ExceptionMessages.notNullNotEmpty("levels", ENTITY_NAME))).when(listValidator).validateRequiredList(jobOfferDto.getLevels(), "levels", ENTITY_NAME, MAX_LENGTH_LEVEL);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.notNullNotEmpty("levels", ENTITY_NAME), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when ListValidator fails (requirements list too long)")
    void testValidateDtoFailWhenRequirementsListTooLong() {
        ArrayList<String> requirementsDto = new ArrayList<>();

        for (int i = 0; i < MAX_SIZE_REQUIREMENTS + 1; i++) {
            requirementsDto.add("requirement " + i);
        }

        jobOfferDto.setRequirements(requirementsDto);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getIndustries(), "industries", ENTITY_NAME, MIN_SIZE_INDUSTRIES, MAX_SIZE_INDUSTRIES, MAX_LENGTH_INDUSTRY);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getLocalizations(), "localizations", ENTITY_NAME, MIN_SIZE_LOCALIZATIONS, MAX_SIZE_LOCALIZATIONS, MAX_LENGTH_LOCALIZATION);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getEmploymentForms(), "employmentForms", ENTITY_NAME, MAX_LENGTH_EMPLOYMENT_FORM);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getContractTypes(), "contractTypes", ENTITY_NAME, MAX_LENGTH_CONTRACT_TYPE);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getLevels(), "levels", ENTITY_NAME, MAX_LENGTH_LEVEL);
        doThrow(new InvalidArgumentException(ExceptionMessages.listTooLong("requirements", ENTITY_NAME, MAX_SIZE_REQUIREMENTS))).when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getRequirements(), "requirements", ENTITY_NAME, MIN_SIZE_REQUIREMENTS, MAX_SIZE_REQUIREMENTS, MAX_LENGTH_REQUIREMENT);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.listTooLong("requirements", ENTITY_NAME, MAX_SIZE_REQUIREMENTS), e.getMessage());
        }
    }

    @Test
    @DisplayName("test validateDto - fail when ListValidator fails (extraSkills too long)")
    void testValidateDtoFailWhenExtraSkillsTooLong() {
        ArrayList<String> extraSkillsDto = new ArrayList<>();

        for (int i = 0; i < MAX_SIZE_EXTRA_SKILLS + 1; i++) {
            extraSkillsDto.add("extra skill " + i);
        }

        jobOfferDto.setExtraSkills(extraSkillsDto);

        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getOfferName(), ENTITY_NAME, "offerName", MIN_OFFER_NAME_LENGTH, MAX_OFFER_NAME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getWorkingTime(), ENTITY_NAME, "workingTime", MIN_WORKING_TIME_LENGTH, MAX_WORKING_TIME_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDuties(), ENTITY_NAME, "duties", MIN_DUTIES_LENGTH, MAX_DUTIES_LENGTH);
        doNothing().when(stringFieldValidator).validateClassicStringRestrictedField(jobOfferDto.getDescription(), ENTITY_NAME, "description", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getIndustries(), "industries", ENTITY_NAME, MIN_SIZE_INDUSTRIES, MAX_SIZE_INDUSTRIES, MAX_LENGTH_INDUSTRY);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getLocalizations(), "localizations", ENTITY_NAME, MIN_SIZE_LOCALIZATIONS, MAX_SIZE_LOCALIZATIONS, MAX_LENGTH_LOCALIZATION);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getEmploymentForms(), "employmentForms", ENTITY_NAME, MAX_LENGTH_EMPLOYMENT_FORM);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getContractTypes(), "contractTypes", ENTITY_NAME, MAX_LENGTH_CONTRACT_TYPE);
        doNothing().when(listValidator).validateRequiredList(jobOfferDto.getLevels(), "levels", ENTITY_NAME, MAX_LENGTH_LEVEL);
        doNothing().when(listValidator).validateRequiredListMinMaxSize(jobOfferDto.getRequirements(), "requirements", ENTITY_NAME, MIN_SIZE_REQUIREMENTS, MAX_SIZE_REQUIREMENTS, MAX_LENGTH_REQUIREMENT);
        doThrow(new InvalidArgumentException(ExceptionMessages.listTooLong("extraSkills", ENTITY_NAME, MAX_SIZE_EXTRA_SKILLS))).when(listValidator).validateListSizeAndElemLength(jobOfferDto.getExtraSkills(), "extraSkills", ENTITY_NAME, MAX_SIZE_EXTRA_SKILLS, MAX_LENGTH_EXTRA_SKILL);

        try {
            jobOfferValidator.validateDto(jobOfferDto);
            fail("Expected InvalidArgumentException");
        } catch (InvalidArgumentException e) {
            assertEquals(ExceptionMessages.listTooLong("extraSkills", ENTITY_NAME, MAX_SIZE_EXTRA_SKILLS), e.getMessage());
        }
    }

}
