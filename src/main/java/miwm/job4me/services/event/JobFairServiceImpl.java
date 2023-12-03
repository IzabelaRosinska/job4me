package miwm.job4me.services.event;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.event.JobFair;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.repositories.event.JobFairRepository;
import miwm.job4me.services.users.OrganizerService;
import miwm.job4me.validators.entity.event.JobFairValidator;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.validators.pagination.PaginationValidator;
import miwm.job4me.web.mappers.event.JobFairMapper;
import miwm.job4me.web.mappers.listDisplay.ListDisplayMapper;
import miwm.job4me.web.model.event.JobFairDto;
import miwm.job4me.web.model.listDisplay.ListDisplayDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobFairServiceImpl implements JobFairService {
    private final JobFairRepository jobFairRepository;
    private final JobFairMapper jobFairMapper;
    private final ListDisplayMapper listDisplayMapper;
    private final JobFairValidator jobFairValidator;
    private final IdValidator idValidator;
    private final PaginationValidator paginationValidator;
    private final OrganizerService organizerService;
    private final String ENTITY_NAME = "JobFair";

    private final Map<String, Sort> orderMap = Map.of(
            "1", Sort.unsorted(),
            "2", Sort.by(Sort.Direction.ASC, "dateStart"),
            "3", Sort.by(Sort.Direction.DESC, "dateStart")
    );

    public JobFairServiceImpl(JobFairRepository jobFairRepository, JobFairMapper jobFairMapper, ListDisplayMapper listDisplayMapper, JobFairValidator jobFairValidator, IdValidator idValidator, PaginationValidator paginationValidator, OrganizerService organizerService) {
        this.jobFairRepository = jobFairRepository;
        this.jobFairMapper = jobFairMapper;
        this.listDisplayMapper = listDisplayMapper;
        this.jobFairValidator = jobFairValidator;
        this.idValidator = idValidator;
        this.paginationValidator = paginationValidator;
        this.organizerService = organizerService;
    }

    @Override
    public Set<JobFairDto> findAll() {
        return jobFairRepository
                .findAll()
                .stream()
                .map(jobFairMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public JobFairDto findById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return jobFairRepository
                .findById(id)
                .map(jobFairMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

    @Override
    public JobFairDto save(JobFair jobFair) {
        idValidator.validateNoIdForCreate(jobFair.getId(), ENTITY_NAME);
        Organizer organizer = organizerService.getAuthOrganizer();
        jobFair.setOrganizer(organizer);
        jobFairValidator.validate(jobFair);
        return jobFairMapper.toDto(jobFairRepository.save(jobFair));
    }

    @Override
    public void delete(JobFair jobFair) {
        if (jobFair == null) {
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_NAME));
        }

        strictExistsById(jobFair.getId());
        jobFairRepository.delete(jobFair);
    }

    @Override
    public void deleteById(Long id) {
        strictExistsById(id);
        jobFairRepository.deleteById(id);
    }

    @Override
    public Page<JobFair> findAllByFilters(int page, int size, String order, Boolean showUpcoming, String address) {
        return findAllOfOrganizerByFilters(page, size, order, showUpcoming, address, null);
    }

    @Override
    public Page<JobFair> findAllOfOrganizerByFilters(int page, int size, String order, Boolean showUpcoming, String address, Long organizerId) {
        paginationValidator.validatePagination(page, size);
        LocalDateTime dateStart = null;
        LocalDateTime dateEnd = null;

        if (showUpcoming != null && showUpcoming) {
            dateStart = LocalDateTime.now();
        } else if (showUpcoming != null) {
            dateEnd = LocalDateTime.now();
        }

        return jobFairRepository
                .findAllByFilters(PageRequest.of(page, size, prepareSort(order)), dateStart, dateEnd, address, organizerId);
    }

    @Override
    public Page<ListDisplayDto> findAllByFiltersListDisplay(int page, int size, String order, Boolean showUpcoming, String address) {
        return findAllByFilters(page, size, order, showUpcoming, address).map(listDisplayMapper::toDtoFromJobFair);
    }

    @Override
    public Page<ListDisplayDto> findAllOfSignedInOrganizerByFiltersListDisplay(int page, int size, String order, Boolean showUpcoming, String address) {
        Long organizerId = organizerService.getAuthOrganizer().getId();

        return findAllOfOrganizerByFiltersListDisplay(page, size, order, showUpcoming, address, organizerId);
    }

    @Override
    public Page<ListDisplayDto> findAllOfOrganizerByFiltersListDisplay(int page, int size, String order, Boolean showUpcoming, String address, Long organizerId) {
        return findAllOfOrganizerByFilters(page, size, order, showUpcoming, address, organizerId).map(listDisplayMapper::toDtoFromJobFair);
    }

    private Sort prepareSort(String order) {
        if (order == null) {
            return Sort.unsorted();
        }

        return orderMap.getOrDefault(order, Sort.unsorted());
    }

    @Override
    public JobFairDto saveDto(JobFairDto jobFairDto) {
        idValidator.validateNoIdForCreate(jobFairDto.getId(), ENTITY_NAME);
        Organizer organizer = organizerService.getAuthOrganizer();
        jobFairDto.setOrganizerId(organizer.getId());
        jobFairDto.setIsPaymentSuccessful(false);
        jobFairValidator.validateDto(jobFairDto);
        return jobFairMapper.toDto(jobFairRepository.save(jobFairMapper.toEntity(jobFairDto)));
    }

    @Override
    public boolean existsById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return jobFairRepository.existsById(id);
    }

    @Override
    public void strictExistsById(Long id) {
        if (!existsById(id)) {
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id));
        }
    }

    @Override
    public JobFairDto update(Long id, JobFairDto jobFair) {
        idValidator.validateLongId(id, ENTITY_NAME);
        strictExistsById(id);
        jobFair.setId(id);
        Organizer organizer = organizerService.getAuthOrganizer();
        jobFair.setOrganizerId(organizer.getId());
        jobFairValidator.validateDto(jobFair);
        return jobFairMapper.toDto(jobFairRepository.save(jobFairMapper.toEntity(jobFair)));
    }

    @Override
    public String getContactEmail(Long id) {
        JobFairDto jobFairDto = findById(id);
        return organizerService.getContactEmail(jobFairDto.getOrganizerId());
    }

    @Override
    public JobFair getJobFairById(Long id) {
        idValidator.validateLongId(id, ENTITY_NAME);
        return jobFairRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_NAME, id)));
    }

}
