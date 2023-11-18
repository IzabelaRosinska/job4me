package miwm.job4me.services.event;

import lombok.Builder;
import miwm.job4me.emails.EMailService;
import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.EmailMessages;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.repositories.event.JobFairEmployerParticipationRepository;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.validators.entity.event.JobFairEmployerParticipationValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.event.JobFairEmployerParticipationMapper;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobFairEmployerParticipationServiceImpl implements JobFairEmployerParticipationService {
    private final JobFairEmployerParticipationRepository jobFairEmployerParticipation;
    private final JobFairEmployerParticipationMapper jobFairEmployerParticipationMapper;
    private final JobFairEmployerParticipationValidator jobFairEmployerParticipationValidator;
    private final JobFairService jobFairService;
    private final EmployerService employerService;
    private final OrganizerService organizerService;
    private final IdValidator idValidator;
    private final String ENTITY_NAME = "JobFairEmployerParticipation";
    private final EMailService eMailService;
    private final JobFairEmployerParticipationRepository jobFairEmployerParticipationRepository;

    @Builder
    public JobFairEmployerParticipationServiceImpl(JobFairEmployerParticipationRepository jobFairEmployerParticipation, JobFairEmployerParticipationMapper jobFairEmployerParticipationMapper, JobFairEmployerParticipationValidator jobFairEmployerParticipationValidator, JobFairService jobFairService, EmployerService employerService, OrganizerService organizerService, IdValidator idValidator, EMailService eMailService, JobFairEmployerParticipationRepository jobFairEmployerParticipationRepository) {
        this.jobFairEmployerParticipation = jobFairEmployerParticipation;
        this.jobFairEmployerParticipationMapper = jobFairEmployerParticipationMapper;
        this.jobFairEmployerParticipationValidator = jobFairEmployerParticipationValidator;
        this.jobFairService = jobFairService;
        this.employerService = employerService;
        this.organizerService = organizerService;
        this.idValidator = idValidator;
        this.eMailService = eMailService;
        this.jobFairEmployerParticipationRepository = jobFairEmployerParticipationRepository;
    }

    @Override
    public Set<JobFairEmployerParticipationDto> findAll() {
        return jobFairEmployerParticipation
                .findAll()
                .stream()
                .map(jobFairEmployerParticipationMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public JobFairEmployerParticipationDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return jobFairEmployerParticipation
                .findById(id)
                .map(jobFairEmployerParticipationMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public JobFairEmployerParticipationDto save(JobFairEmployerParticipation jobFairEmployerParticipation) {
        if (jobFairEmployerParticipation == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        preSaveValidation(jobFairEmployerParticipation.getId(), jobFairEmployerParticipation.getJobFair().getId(), jobFairEmployerParticipation.getEmployer().getId());
        jobFairEmployerParticipationValidator.validate(jobFairEmployerParticipation);
        return jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipationRepository.save(jobFairEmployerParticipation));
    }

    @Override
    public void delete(JobFairEmployerParticipation jobFairEmployerParticipation) {
        if (jobFairEmployerParticipation == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        strictExistsById(jobFairEmployerParticipation.getId());
        jobFairEmployerParticipationRepository.delete(jobFairEmployerParticipation);
    }

    @Override
    public void deleteById(Long id) {
        strictExistsById(id);
        jobFairEmployerParticipationRepository.deleteById(id);
    }

    @Override
    public Page<JobFairEmployerParticipationDto> findAllByFilters(int page, int size) {
        return jobFairEmployerParticipationRepository
                .findAllByFilters(PageRequest.of(page, size))
                .map(jobFairEmployerParticipationMapper::toDto);
    }

    @Override
    public Page<JobFairEmployerParticipationDto> findAllByEmployerAndFilters(int page, int size) {
        Employer employer = employerService.getAuthEmployer();

        return jobFairEmployerParticipationRepository
                .findAllByEmployerId(PageRequest.of(page, size), employer.getId())
                .map(jobFairEmployerParticipationMapper::toDto);
    }

    @Override
    public Page<JobFairEmployerParticipationDto> findAllByOrganizerAndFilters(int page, int size) {
        Organizer organizer = organizerService.getAuthOrganizer();

        return jobFairEmployerParticipationRepository
                .findByJobFair_Organizer_Id(PageRequest.of(page, size), organizer.getId())
                .map(jobFairEmployerParticipationMapper::toDto);
    }

    @Override
    public Page<JobFairEmployerParticipationDto> findAllByOrganizerAndJobFairAndFilters(int page, int size, Long jobFairId) {
        Organizer organizer = organizerService.getAuthOrganizer();

        return jobFairEmployerParticipationRepository
                .findByJobFair_IdAndJobFair_Organizer_Id(PageRequest.of(page, size), organizer.getId(), jobFairId)
                .map(jobFairEmployerParticipationMapper::toDto);
    }

    @Override
    public JobFairEmployerParticipationDto saveDto(JobFairEmployerParticipationDto jobFairDto) {
        if (jobFairDto == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        preSaveValidation(jobFairDto.getId(), jobFairDto.getJobFairId(), jobFairDto.getEmployerId());
        return validateAndSaveDto(jobFairDto);
    }

    @Override
    public boolean existsById(Long id) {
        return jobFairEmployerParticipationRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public boolean existsByJobFairIdAndEmployerId(Long jobFairId, Long employerId) {
        return jobFairEmployerParticipationRepository.existsByJobFair_IdAndEmployer_Id(jobFairId, employerId);
    }

    @Override
    public JobFairEmployerParticipationDto update(Long id, JobFairEmployerParticipationDto jobFairEmployerParticipation) {
        strictExistsById(id);
        jobFairEmployerParticipation.setId(id);
        return validateAndSaveDto(jobFairEmployerParticipation);
    }

    @Override
    public JobFairEmployerParticipationDto createParticipationRequestByEmployer(Long jobFairId) {
        Employer employer = employerService.getAuthEmployer();
        JobFairDto jobFair = jobFairService.findById(jobFairId);

        JobFairEmployerParticipationDto jobFairEmployerParticipation = new JobFairEmployerParticipationDto();
        jobFairEmployerParticipation.setJobFairId(jobFairId);
        jobFairEmployerParticipation.setEmployerId(employer.getId());

        sendRequestEmailToOrganizer(employer, jobFair);
        return saveDto(jobFairEmployerParticipation);
    }

    private void sendRequestEmailToOrganizer(Employer employer, JobFairDto jobFair) {
        String recipient = jobFairService.getContactEmail(jobFair.getId());
        String subject = EmailMessages.employerJobFairParticipationRequestEmailSubject(employer.getCompanyName(), jobFair.getName());
        String text = EmailMessages.employerJobFairParticipationRequestEmailText(employer.getCompanyName(), jobFair.getId(), jobFair.getName());

        eMailService.sendSimpleMessage(recipient, subject, text);
    }

    private JobFairEmployerParticipationDto validateAndSaveDto(JobFairEmployerParticipationDto jobFairEmployerParticipationDto) {
        jobFairEmployerParticipationValidator.validateDto(jobFairEmployerParticipationDto);
        return jobFairEmployerParticipationMapper.toDto(jobFairEmployerParticipationRepository.save(jobFairEmployerParticipationMapper.toEntity(jobFairEmployerParticipationDto)));
    }

    private void preSaveValidation(Long jobFairEmployerParticipationId, Long jobFairId, Long employerId) {
        idValidator.validateNoIdForCreate(jobFairEmployerParticipationId, ENTITY_NAME);
        jobFairService.strictExistsById(jobFairId);
        employerService.strictExistsById(employerId);

        if (existsByJobFairIdAndEmployerId(jobFairId, employerId)) {
            throw new InvalidArgumentException(ExceptionMessages.elementAlreadyExists(ENTITY_NAME, "jobFair and employer", jobFairId + " and " + employerId));
        }
    }
}
