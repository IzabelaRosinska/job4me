package miwm.job4me.services.offer;

import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.users.Employer;
import miwm.job4me.repositories.offer.JobOfferRepository;
import miwm.job4me.services.offer.description.ExtraSkillService;
import miwm.job4me.services.offer.description.RequirementService;
import miwm.job4me.services.offer.parameters.*;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.validators.entity.offer.JobOfferValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.offer.JobOfferMapper;
import miwm.job4me.web.model.filters.JobOfferFilterDto;
import miwm.job4me.web.model.filters.JobOfferFilterPresenceDto;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
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
    private final EmployerService employerService;

    private final String ENTITY_NAME = "JobOffer";

    private final Map<String, Sort> orderMap = Map.of(
            "1", Sort.unsorted(),
            "2", Sort.by(Sort.Direction.ASC, "salaryFrom"),
            "3", Sort.by(Sort.Direction.DESC, "salaryFrom"),
            "4", Sort.by(Sort.Direction.ASC, "offerName"),
            "5", Sort.by(Sort.Direction.DESC, "offerName")
    );

    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository, JobOfferMapper jobOfferMapper, JobOfferValidator jobOfferValidator, IdValidator idValidator, ContractTypeService contractTypeService, EmploymentFormService employmentFormService, IndustryService industryService, LevelService levelService, LocalizationService localizationService, RequirementService requirementService, ExtraSkillService extraSkillService, EmployerService employerService) {
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
        this.employerService = employerService;
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
        Employer employer = employerService.getAuthEmployer();
        jobOffer.setEmployer(employer);

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
    public Page<JobOfferDto> findAllByPage(int page, int size, String order) {
        return jobOfferRepository.findAll(PageRequest.of(page, size, prepareSort(order)))
                .map(jobOfferMapper::toDto);
    }

    @Override
    public Page<JobOffer> findByPage(int page, int size, String order, Boolean isActive, List<Long> offerIds) {
        Boolean isOfferIdsDefined = offerIds != null && !offerIds.isEmpty();
        offerIds = prepareIds(offerIds);
        Sort sort = prepareSort(order);

        return jobOfferRepository.findByIsActive(PageRequest.of(page, size, sort), isActive, isOfferIdsDefined, offerIds);
    }

    @Override
    public Page<JobOffer> findByFilters(int page, int size, String order, JobOfferFilterDto jobOfferFilterDto, List<Long> employerIds, Boolean isActive, List<Long> offerIds) {
        JobOfferFilterPresenceDto jobOfferFilterPresenceDto = prepareFilterDefinition(jobOfferFilterDto, employerIds, offerIds);
        jobOfferFilterDto = prepareFilter(jobOfferFilterDto);
        offerIds = prepareIds(offerIds);
        Sort sort = prepareSort(order);

        return jobOfferRepository.findAllOffersByFilters(PageRequest.of(page, size, sort),
                jobOfferFilterPresenceDto.getIsEmployerIdsDefined(),
                employerIds,
                isActive,
                jobOfferFilterPresenceDto.getIsCitiesDefined(),
                jobOfferFilterDto.getCities(),
                jobOfferFilterPresenceDto.getIsEmploymentFormNamesDefined(),
                jobOfferFilterDto.getEmploymentFormNames(),
                jobOfferFilterPresenceDto.getIsLevelNamesDefined(),
                jobOfferFilterDto.getLevelNames(),
                jobOfferFilterPresenceDto.getIsContractTypeNamesDefined(),
                jobOfferFilterDto.getContractTypeNames(),
                jobOfferFilterDto.getSalaryFrom(),
                jobOfferFilterDto.getSalaryTo(),
                jobOfferFilterPresenceDto.getIsIndustryNamesDefined(),
                jobOfferFilterDto.getIndustryNames(),
                jobOfferFilterDto.getOfferName(),
                jobOfferFilterPresenceDto.getIsOfferIdsDefined(),
                offerIds);
    }

    @Override
    public Page<JobOffer> findAllOffersOfEmployers(int page, int size, String order, List<Long> employerIds, Boolean isActive, List<Long> offerIds) {
        Boolean isEmployerIdsDefined = employerIds != null && !employerIds.isEmpty();
        Boolean isOfferIdsDefined = offerIds != null && !offerIds.isEmpty();

        Sort sort = prepareSort(order);
        offerIds = prepareIds(offerIds);
        employerIds = prepareIds(employerIds);

        return jobOfferRepository.findAllOffersOfEmployers(PageRequest.of(page, size, sort), isEmployerIdsDefined, employerIds, isActive, isOfferIdsDefined, offerIds);
    }

    private List<Long> prepareIds(List<Long> ids) {
        if (ids == null) {
            return List.of();
        }

        return ids;
    }

    private Sort prepareSort(String order) {
        if (order == null) {
            return Sort.unsorted();
        }

        return orderMap.getOrDefault(order, Sort.unsorted());
    }

    private JobOfferFilterPresenceDto prepareFilterDefinition(JobOfferFilterDto jobOfferFilterDto, List<Long> employerIds, List<Long> offerIds) {
        JobOfferFilterPresenceDto jobOfferFilterPresenceDto = new JobOfferFilterPresenceDto();

        if (jobOfferFilterDto == null) {
            return new JobOfferFilterPresenceDto();
        }

        jobOfferFilterPresenceDto.setIsCitiesDefined(
                jobOfferFilterDto.getCities() != null && !jobOfferFilterDto.getCities().isEmpty());

        jobOfferFilterPresenceDto.setIsEmploymentFormNamesDefined(
                jobOfferFilterDto.getEmploymentFormNames() != null && !jobOfferFilterDto.getEmploymentFormNames().isEmpty());

        jobOfferFilterPresenceDto.setIsLevelNamesDefined(
                jobOfferFilterDto.getLevelNames() != null && !jobOfferFilterDto.getLevelNames().isEmpty());

        jobOfferFilterPresenceDto.setIsIndustryNamesDefined(
                jobOfferFilterDto.getIndustryNames() != null && !jobOfferFilterDto.getIndustryNames().isEmpty());

        jobOfferFilterPresenceDto.setIsIndustryNamesDefined(
                jobOfferFilterDto.getIndustryNames() != null && !jobOfferFilterDto.getIndustryNames().isEmpty());

        jobOfferFilterPresenceDto.setIsEmployerIdsDefined(employerIds != null && !employerIds.isEmpty());

        jobOfferFilterPresenceDto.setIsOfferIdsDefined(offerIds != null && !offerIds.isEmpty());

        return jobOfferFilterPresenceDto;
    }

    private JobOfferFilterDto prepareFilter(JobOfferFilterDto jobOfferFilterDto) {
        if (jobOfferFilterDto == null) {
            jobOfferFilterDto = new JobOfferFilterDto();
        }

        if (jobOfferFilterDto.getOfferName() == null) {
            jobOfferFilterDto.setOfferName("");
        }

        if (jobOfferFilterDto.getCities() != null && jobOfferFilterDto.getCities().isEmpty()) {
            jobOfferFilterDto.setCities(null);
        }

        if (jobOfferFilterDto.getEmploymentFormNames() != null && jobOfferFilterDto.getEmploymentFormNames().isEmpty()) {
            jobOfferFilterDto.setEmploymentFormNames(null);
        }

        if (jobOfferFilterDto.getLevelNames() != null && jobOfferFilterDto.getLevelNames().isEmpty()) {
            jobOfferFilterDto.setLevelNames(null);
        }

        if (jobOfferFilterDto.getIndustryNames() != null && jobOfferFilterDto.getIndustryNames().isEmpty()) {
            jobOfferFilterDto.setContractTypeNames(null);
        }

        if (jobOfferFilterDto.getIndustryNames() != null && jobOfferFilterDto.getIndustryNames().isEmpty()) {
            jobOfferFilterDto.setIndustryNames(null);
        }

        return jobOfferFilterDto;
    }

    @Override
    public JobOfferDto saveDto(JobOfferDto jobOfferDto) {
        Employer employer = employerService.getAuthEmployer();
        jobOfferDto.setEmployerId(employer.getId());

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
