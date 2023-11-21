package miwm.job4me.services.event;

import miwm.job4me.emails.EMailService;
import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.EmailMessages;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.event.JobFairEmployerParticipation;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.repositories.event.JobFairEmployerParticipationRepository;
import miwm.job4me.services.users.EmployerService;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.validators.arguments.FilterArgumentValidator;
import miwm.job4me.validators.entity.event.JobFairEmployerParticipationValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.event.JobFairEmployerParticipationMapper;
import miwm.job4me.web.model.event.JobFairEmployerParticipationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobFairEmployerParticipationServiceImpl implements JobFairEmployerParticipationService {
    private final JobFairEmployerParticipationRepository jobFairEmployerParticipationRepository;
    private final JobFairEmployerParticipationMapper jobFairEmployerParticipationMapper;
    private final JobFairEmployerParticipationValidator jobFairEmployerParticipationValidator;
    private final JobFairService jobFairService;
    private final EmployerService employerService;
    private final OrganizerService organizerService;
    private final IdValidator idValidator;
    private final FilterArgumentValidator filterArgumentValidator;
    private final EMailService eMailService;
    private final String ENTITY_NAME = "JobFairEmployerParticipation";

    public JobFairEmployerParticipationServiceImpl(JobFairEmployerParticipationRepository jobFairEmployerParticipationRepository,
                                                   JobFairEmployerParticipationMapper jobFairEmployerParticipationMapper,
                                                   JobFairEmployerParticipationValidator jobFairEmployerParticipationValidator,
                                                   JobFairService jobFairService,
                                                   EmployerService employerService,
                                                   OrganizerService organizerService,
                                                   IdValidator idValidator,
                                                   FilterArgumentValidator filterArgumentValidator,
                                                   EMailService eMailService) {
        this.jobFairEmployerParticipationRepository = jobFairEmployerParticipationRepository;
        this.jobFairEmployerParticipationMapper = jobFairEmployerParticipationMapper;
        this.jobFairEmployerParticipationValidator = jobFairEmployerParticipationValidator;
        this.jobFairService = jobFairService;
        this.employerService = employerService;
        this.organizerService = organizerService;
        this.idValidator = idValidator;
        this.filterArgumentValidator = filterArgumentValidator;
        this.eMailService = eMailService;
    }

    @Override
    public Set<JobFairEmployerParticipationDto> findAll() {
        return jobFairEmployerParticipationRepository
                .findAll()
                .stream()
                .map(jobFairEmployerParticipationMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public JobFairEmployerParticipationDto findById(Long id) {
        return jobFairEmployerParticipationMapper.toDto(getJobFairEmployerParticipationById(id));
    }

    @Override
    public JobFairEmployerParticipationDto save(JobFairEmployerParticipation jobFairEmployerParticipation) {
        if (jobFairEmployerParticipation == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        jobFairEmployerParticipation.setIsAccepted(false);
        jobFairEmployerParticipationValidator.validate(jobFairEmployerParticipation);
        preSaveValidation(jobFairEmployerParticipation.getId(), jobFairEmployerParticipation.getJobFair().getId(), jobFairEmployerParticipation.getEmployer().getId());
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
    public Page<JobFairEmployerParticipation> findAllByFilters(int page, int size, Boolean status, Long jobFairId, Long employerId, String jobFairName, String employerCompanyName) {
        filterArgumentValidator.validateStringFilter(jobFairName, ENTITY_NAME, "jobFairName");
        filterArgumentValidator.validateStringFilter(employerCompanyName, ENTITY_NAME, "employerCompanyName");

        if (jobFairId != null) {
            jobFairService.strictExistsById(jobFairId);
        }

        if (employerId != null) {
            employerService.strictExistsById(employerId);
        }

        return jobFairEmployerParticipationRepository
                .findAllByFilters(PageRequest.of(page, size), status, jobFairId, employerId, jobFairName, employerCompanyName);
    }

    @Override
    public Page<JobFairEmployerParticipationDto> findAllByEmployerAndFilters(int page, int size, Boolean status, String jobFairName) {
        Employer employer = employerService.getAuthEmployer();

        return findAllByFilters(page, size, status, null, employer.getId(), jobFairName, "")
                .map(jobFairEmployerParticipationMapper::toDto);
    }

    @Override
    public Page<JobFairEmployerParticipationDto> findAllByOrganizerAndFilters(int page, int size, Boolean status, String jobFairName, String employerCompanyName) {
        Organizer organizer = organizerService.getAuthOrganizer();

        return jobFairEmployerParticipationRepository
                .findByJobFair_Organizer_IdAndByFilters(PageRequest.of(page, size), organizer.getId(), status, jobFairName, employerCompanyName)
                .map(jobFairEmployerParticipationMapper::toDto);
    }

    @Override
    public Page<JobFairEmployerParticipationDto> findAllByOrganizerAndJobFairAndFilters(int page, int size, Long jobFairId, Boolean status, String employerCompanyName) {
        Organizer organizer = organizerService.getAuthOrganizer();
        filterArgumentValidator.validateStringFilter(employerCompanyName, ENTITY_NAME, "employerCompanyName");

        return jobFairEmployerParticipationRepository
                .findByJobFair_IdAndJobFair_Organizer_IdAndByFilters(PageRequest.of(page, size), jobFairId, status, employerCompanyName)
                .map(jobFairEmployerParticipationMapper::toDto);
    }

    @Override
    public JobFairEmployerParticipationDto saveDto(JobFairEmployerParticipationDto jobFairEmployerParticipationDto) {
        if (jobFairEmployerParticipationDto == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        jobFairEmployerParticipationDto.setIsAccepted(false);
        jobFairEmployerParticipationValidator.validateDto(jobFairEmployerParticipationDto);
        preSaveValidation(jobFairEmployerParticipationDto.getId(), jobFairEmployerParticipationDto.getJobFairId(), jobFairEmployerParticipationDto.getEmployerId());
        return mapAndSaveDto(jobFairEmployerParticipationDto);
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
        jobFairEmployerParticipationValidator.validateDto(jobFairEmployerParticipation);
        return mapAndSaveDto(jobFairEmployerParticipation);
    }

    @Override
    public JobFairEmployerParticipationDto createParticipationRequestByEmployer(Long jobFairId) {
        Employer employer = employerService.getAuthEmployer();
        JobFair jobFair = jobFairService.getJobFairById(jobFairId);

        JobFairEmployerParticipation jobFairEmployerParticipation = new JobFairEmployerParticipation();
        jobFairEmployerParticipation.setJobFair(jobFair);
        jobFairEmployerParticipation.setEmployer(employer);

        sendRequestEmailToOrganizer(employer, jobFairId, jobFair.getName());
        return save(jobFairEmployerParticipation);
    }

    @Override
    public JobFairEmployerParticipationDto acceptParticipationRequestByOrganizer(Long jobFairParticipationId) {
        JobFairEmployerParticipationDto jobFairEmployerParticipation = findById(jobFairParticipationId);
        jobFairEmployerParticipation.setIsAccepted(true);
        JobFairEmployerParticipationDto savedJobFairEmployerParticipation = update(jobFairParticipationId, jobFairEmployerParticipation);

        Employer employer = employerService.findById(savedJobFairEmployerParticipation.getEmployerId());
        JobFair jobFair = jobFairService.getJobFairById(savedJobFairEmployerParticipation.getJobFairId());
        sendAcceptEmailToEmployer(employer, savedJobFairEmployerParticipation.getJobFairId(), jobFair.getName());

        return savedJobFairEmployerParticipation;
    }

    @Override
    public void rejectParticipationRequestByOrganizer(Long jobFairParticipationId) {
        JobFairEmployerParticipation jobFairEmployerParticipation = getJobFairEmployerParticipationById(jobFairParticipationId);

        deleteById(jobFairParticipationId);
        sendRejectEmailToEmployer(jobFairEmployerParticipation.getEmployer(), jobFairEmployerParticipation.getJobFair().getId(), jobFairEmployerParticipation.getJobFair().getName());
    }

    @Override
    public void deleteParticipationRequestByOrganizer(Long jobFairParticipationId) {
        JobFairEmployerParticipation jobFairEmployerParticipation = getJobFairEmployerParticipationById(jobFairParticipationId);

        deleteById(jobFairParticipationId);
        sendDeleteEmailToEmployer(jobFairEmployerParticipation.getEmployer(), jobFairEmployerParticipation.getJobFair().getId(), jobFairEmployerParticipation.getJobFair().getName());
    }

    @Override
    public void deleteParticipationRequestByEmployer(Long jobFairParticipationId) {
        JobFairEmployerParticipation jobFairEmployerParticipation = getJobFairEmployerParticipationById(jobFairParticipationId);

        deleteById(jobFairParticipationId);
        sendDeleteEmailToOrganizer(jobFairEmployerParticipation.getEmployer(), jobFairEmployerParticipation.getJobFair().getId(), jobFairEmployerParticipation.getJobFair().getName());
    }

    @Override
    public JobFairEmployerParticipation getJobFairEmployerParticipationById(Long jobFairParticipationId) {
        idValidator.validateLongId(jobFairParticipationId, ENTITY_NAME);
        return jobFairEmployerParticipationRepository
                .findById(jobFairParticipationId)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, jobFairParticipationId)));
    }

    private void sendRequestEmailToOrganizer(Employer employer, Long jobFairId, String jobFairName) {
        String recipient = jobFairService.getContactEmail(jobFairId);
        String subject = EmailMessages.employerJobFairParticipationRequestEmailSubject(employer.getCompanyName(), jobFairName);
        String text = EmailMessages.employerJobFairParticipationRequestEmailText(employer.getCompanyName(), jobFairId, jobFairName);

        eMailService.sendSimpleMessage(recipient, subject, text);
    }

    private void sendAcceptEmailToEmployer(Employer employer, Long jobFairId, String jobFairName) {
        String recipient = getEmployerContactEmail(employer);
        String subject = EmailMessages.employerJobFairParticipationAcceptEmailSubject(employer.getCompanyName(), jobFairName);
        String text = EmailMessages.employerJobFairParticipationAcceptEmailText(employer.getCompanyName(), jobFairId, jobFairName);

        eMailService.sendSimpleMessage(recipient, subject, text);
    }

    private void sendRejectEmailToEmployer(Employer employer, Long jobFairId, String jobFairName) {
        String recipient = getEmployerContactEmail(employer);
        String subject = EmailMessages.employerJobFairParticipationRejectEmailSubject(employer.getCompanyName(), jobFairName);
        String text = EmailMessages.employerJobFairParticipationRejectEmailText(employer.getCompanyName(), jobFairId, jobFairName);

        eMailService.sendSimpleMessage(recipient, subject, text);
    }

    private void sendDeleteEmailToEmployer(Employer employer, Long jobFairId, String jobFairName) {
        String recipient = getEmployerContactEmail(employer);
        String subject = EmailMessages.employerJobFairParticipationDeleteEmailSubject(employer.getCompanyName(), jobFairName);
        String text = EmailMessages.employerJobFairParticipationDeleteEmailText(employer.getCompanyName(), jobFairId, jobFairName);

        eMailService.sendSimpleMessage(recipient, subject, text);
    }

    private void sendDeleteEmailToOrganizer(Employer employer, Long jobFairId, String jobFairName) {
        String recipient = jobFairService.getContactEmail(jobFairId);
        String subject = EmailMessages.organizerJobFairParticipationDeleteEmailSubject(employer.getCompanyName(), jobFairName);
        String text = EmailMessages.organizerJobFairParticipationDeleteEmailText(employer.getCompanyName(), jobFairId, jobFairName);

        eMailService.sendSimpleMessage(recipient, subject, text);
    }

    private String getEmployerContactEmail(Employer employer) {
        return employer.getContactEmail() != null ? employer.getContactEmail() : employer.getEmail();
    }

    private JobFairEmployerParticipationDto mapAndSaveDto(JobFairEmployerParticipationDto jobFairEmployerParticipationDto) {
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