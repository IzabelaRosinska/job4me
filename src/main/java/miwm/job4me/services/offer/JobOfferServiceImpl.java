package miwm.job4me.services.offer;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.repositories.offer.JobOfferRepository;
import miwm.job4me.validators.entity.offer.JobOfferValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.offer.JobOfferMapper;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobOfferServiceImpl implements JobOfferService {
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferMapper jobOfferMapper;
    private final JobOfferValidator jobOfferValidator;
    private final IdValidator idValidator;

    private final ContractTypeService contractTypeService;
    private final EmploymentFormService employmentFormService;
    private final IndustryService industryService;
    private final LevelService levelService;
    private final LocalizationService localizationService;
    private final RequirementService requirementService;
    private final ExtraSkillService extraSkillService;

    private final String ENTITY_NAME = "JobOffer";

    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository, JobOfferMapper jobOfferMapper, JobOfferValidator jobOfferValidator, IdValidator idValidator, ContractTypeService contractTypeService, EmploymentFormService employmentFormService, IndustryService industryService, LevelService levelService, LocalizationService localizationService, RequirementService requirementService, ExtraSkillService extraSkillService) {
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferMapper = jobOfferMapper;
        this.jobOfferValidator = jobOfferValidator;
        this.idValidator = idValidator;
        this.contractTypeService = contractTypeService;
        this.employmentFormService = employmentFormService;
        this.industryService = industryService;
        this.levelService = levelService;
        this.localizationService = localizationService;
        this.requirementService = requirementService;
        this.extraSkillService = extraSkillService;
    }

    @Override
    public Set<JobOfferDto> findAll() {
        return jobOfferRepository
                .findAll()
                .stream()
                .map(jobOfferMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public JobOfferDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return jobOfferRepository
                .findById(id)
                .map(jobOfferMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public JobOfferDto save(JobOffer jobOffer) {
        idValidator.validateNoIdForCreate(jobOffer.getId(), ENTITY_NAME);
        jobOfferValidator.validate(jobOffer);

        return jobOfferMapper.toDto(jobOfferRepository.save(jobOffer));
    }

    @Override
    public void delete(JobOffer jobOffer) {
        strictExistsById(jobOffer.getId());
        jobOfferRepository.delete(jobOffer);
    }

    @Override
    public void deleteById(Long id) {
        strictExistsById(id);
        jobOfferRepository.deleteById(id);
    }

    @Override
    public Page<JobOfferDto> findByFilters(int page, int size, String city, String employmentFormName, String levelName, String contractTypeName, Integer salaryFrom, Integer salaryTo, String industryName, String offerName) {
        return jobOfferRepository
                .findByFilters(PageRequest.of(page, size), city, employmentFormName, levelName, contractTypeName, salaryFrom, salaryTo, industryName, offerName)
                .map(jobOfferMapper::toDto);
    }

    @Override
    public JobOfferDto saveDto(JobOfferDto jobOfferDto) {
        idValidator.validateNoIdForCreate(jobOfferDto.getId(), ENTITY_NAME);

        return saveJobOfferDto(jobOfferDto);
    }

    private JobOfferDto saveJobOfferDto(JobOfferDto jobOfferDto) {
        jobOfferValidator.validateDto(jobOfferDto);
        JobOffer jobOffer = jobOfferMapper.toEntity(jobOfferDto);

        jobOffer.setContractTypes(jobOfferDto.getContractTypes()
                .stream()
                .map(contractTypeService::findByName)
                .collect(Collectors.toSet()));

        jobOffer.setEmploymentForms(jobOfferDto.getEmploymentForms()
                .stream()
                .map(employmentFormService::findByName)
                .collect(Collectors.toSet()));

        jobOffer.setIndustries(jobOfferDto.getIndustries()
                .stream()
                .map(industryService::findByName)
                .collect(Collectors.toSet()));

        jobOffer.setLevels(jobOfferDto.getLevels()
                .stream()
                .map(levelService::findByName)
                .collect(Collectors.toSet()));

        jobOffer.setLocalizations(jobOfferDto.getLocalizations()
                .stream()
                .map(localizationService::findByCity)
                .collect(Collectors.toSet()));

        return jobOfferMapper.toDto(jobOfferRepository.save(jobOffer));
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);

        return jobOfferRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    @Transactional
    public JobOfferDto update(Long id, JobOfferDto jobOffer) {
        strictExistsById(id);
        requirementService.deleteAllByJobOfferId(id);
        extraSkillService.deleteAllByJobOfferId(id);
        return saveJobOfferDto(jobOffer);
    }

    @Override
    public JobOffer findOfferById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        Optional<JobOffer> jobOffer = jobOfferRepository.findById(id);
        if (jobOffer.isPresent())
            return jobOffer.get();
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
    }
}
