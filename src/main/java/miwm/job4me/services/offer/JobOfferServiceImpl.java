package miwm.job4me.services.offer;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.repositories.offer.JobOfferRepository;
import miwm.job4me.validators.entity.offer.JobOfferValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.listDisplay.ListDisplayMapper;
import miwm.job4me.web.mappers.offer.JobOfferMapper;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobOfferServiceImpl implements JobOfferService {
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferMapper jobOfferMapper;
    private final ListDisplayMapper listDisplayMapper;
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

    private final Map<String, String> orderMap = Map.of(
            "1", "j.salaryFrom ASC",
            "2", "j.salaryFrom DESC",
            "3", "j.offerName ASC",
            "4", "j.offerName DESC"
    );

    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository, JobOfferMapper jobOfferMapper, ListDisplayMapper listDisplayMapper, JobOfferValidator jobOfferValidator, IdValidator idValidator, ContractTypeService contractTypeService, EmploymentFormService employmentFormService, IndustryService industryService, LevelService levelService, LocalizationService localizationService, RequirementService requirementService, ExtraSkillService extraSkillService) {
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferMapper = jobOfferMapper;
        this.listDisplayMapper = listDisplayMapper;
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

        if (jobOffer.getIsActive() == null) {
            jobOffer.setIsActive(true);
        }

        jobOffer.setIsEmbeddingCurrent(false);

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
    public Page<ListDisplayDto> findListDisplayByFilters(int page, int size, JobOfferFilterDto jobOfferFilterDto) {
        return jobOfferRepository
                .findByFilters2(PageRequest.of(page, size),
                        jobOfferFilterDto.getCities(),
                        jobOfferFilterDto.getEmploymentFormNames(),
                        jobOfferFilterDto.getLevelNames(),
                        jobOfferFilterDto.getContractTypeNames(),
                        jobOfferFilterDto.getSalaryFrom(),
                        jobOfferFilterDto.getSalaryTo(),
                        jobOfferFilterDto.getIndustryNames(),
                        jobOfferFilterDto.getOfferName(),
                        orderMap.get(jobOfferFilterDto.getOrder()))
                .map(listDisplayMapper::toDtoFromJobOffer);
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

        jobOffer.setIsEmbeddingCurrent(false);

        if (jobOfferDto.getIsActive() == null) {
            jobOffer.setIsActive(true);
        }

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

    @Override
    public JobOfferDto activateOffer(Long id) {
        JobOffer jobOffer = findOfferById(id);

        if (jobOffer.getIsActive()) {
            return jobOfferMapper.toDto(jobOffer);
        }

        jobOffer.setIsActive(true);
        return jobOfferMapper.toDto(jobOfferRepository.save(jobOffer));
    }

    @Override
    public JobOfferDto deactivateOffer(Long id) {
        JobOffer jobOffer = findOfferById(id);

        if (!jobOffer.getIsActive()) {
            return jobOfferMapper.toDto(jobOffer);
        }

        jobOffer.setIsActive(false);
        return jobOfferMapper.toDto(jobOfferRepository.save(jobOffer));
    }

}
