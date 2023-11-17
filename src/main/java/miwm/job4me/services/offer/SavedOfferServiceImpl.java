package miwm.job4me.services.offer;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.model.offer.JobOffer;
import miwm.job4me.model.offer.SavedOffer;
import miwm.job4me.model.users.Employee;
import miwm.job4me.repositories.offer.SavedOfferRepository;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.validators.fields.IdValidator;
import miwm.job4me.web.mappers.offer.JobOfferReviewMapper;
import miwm.job4me.web.model.offer.JobOfferReviewDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SavedOfferServiceImpl implements SavedOfferService {

    private final SavedOfferRepository savedOfferRepository;
    private final JobOfferReviewMapper jobOfferReviewMapper;
    private final EmployeeService employeeService;
    private final JobOfferService jobOfferService;
    private final IdValidator idValidator;
    private final String ENTITY_OFFER = "JobOffer";
    private final String ENTITY_EMPLOYEE = "Employee";
    private final String ENTITY_SAVED_OFFER = "SavedOffer";

    public SavedOfferServiceImpl(SavedOfferRepository savedOfferRepository, JobOfferReviewMapper jobOfferReviewMapper, EmployeeService employeeService, JobOfferService jobOfferService, IdValidator idValidator) {
        this.savedOfferRepository = savedOfferRepository;
        this.jobOfferReviewMapper = jobOfferReviewMapper;
        this.employeeService = employeeService;
        this.jobOfferService = jobOfferService;
        this.idValidator = idValidator;
    }

    @Override
    public Set<SavedOffer> findAll() {
        return (Set<SavedOffer>) savedOfferRepository.findAll();
    }

    @Override
    public SavedOffer findById(Long id) {
        idValidator.validateLongId(id, ENTITY_SAVED_OFFER);
        Optional<SavedOffer> savedOffer = savedOfferRepository.findById(id);
        if(savedOffer.isPresent())
            return savedOffer.get();
        throw new NoSuchElementFoundException("Saved offer with that id does not exist");
    }

    @Override
    @Transactional
    public SavedOffer save(SavedOffer savedOffer) {
        if(savedOffer != null)
            return savedOfferRepository.save(savedOffer);
        throw new InvalidArgumentException("Given offer is null");
    }

    @Override
    @Transactional
    public void delete(SavedOffer savedOffer) {
        if(savedOfferRepository.findById(savedOffer.getId()).isPresent())
            savedOfferRepository.delete(savedOffer);
        else
            throw new NoSuchElementFoundException("There is no such offer in database");
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_SAVED_OFFER);
        if(savedOfferRepository.findById(id).isPresent())
            savedOfferRepository.deleteById(id);
        else
            throw new NoSuchElementFoundException("Offer with given id does not exist");
    }

    @Override
    public List<SavedOffer> getSavedForEmployeeWithId(Long employeeId) {
        idValidator.validateLongId(employeeId, ENTITY_EMPLOYEE);
        return savedOfferRepository.getSavedForEmployee(employeeId);
    }

    @Override
    public Optional<SavedOffer> findByIds(Long offerId, Long employeeId) {
        idValidator.validateLongId(offerId, ENTITY_OFFER);
        idValidator.validateLongId(employeeId, ENTITY_EMPLOYEE);
        if(employeeService.findById(employeeId) != null && jobOfferService.findById(offerId) != null)
            return savedOfferRepository.findByIds(offerId, employeeId);
        else
            throw new NoSuchElementFoundException("User with given id does not exist");
    }

    @Override
    public Boolean checkIfSaved(Long id) {
        idValidator.validateLongId(id, ENTITY_OFFER);
        Long employeeId = employeeService.getAuthEmployee().getId();
        Optional<SavedOffer> savedOffer = findByIds(id, employeeId);
        if(savedOffer.isPresent())
            return true;
        return false;
    }

    @Override
    public List<JobOfferReviewDto> getSavedOffers() {
        Employee employee = employeeService.getAuthEmployee();
        return getSavedForEmployeeWithId(employee.getId()).stream()
                .map(offer -> jobOfferReviewMapper.jobOfferToJobOfferDto(offer.getOffer(), checkIfSaved(offer.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteOfferFromSaved(Long id) {
        idValidator.validateLongId(id, ENTITY_OFFER);
        Employee employee = employeeService.getAuthEmployee();
        if(employee != null) {
            Optional<SavedOffer> saved = findByIds(id, employee.getId());
            if (saved.isPresent()) {
                delete(saved.get());
            } else
                throw new NoSuchElementFoundException("Employee with given id does not exist");
        }
    }

    @Override
    @Transactional
    public void addOfferToSaved(Long id) {
        idValidator.validateLongId(id, ENTITY_OFFER);
        Employee employee = employeeService.getAuthEmployee();
        JobOffer offer = jobOfferService.findOfferById(id);
        if(employee != null && offer != null) {
            SavedOffer savedOffer = SavedOffer.builder().employee(employee).offer(offer).build();
            save(savedOffer);
        } else
            throw new NoSuchElementFoundException("Offer does not exist");
    }

    @Override
    public JobOfferReviewDto findOfferWithIdByUser(Long id) {
        idValidator.validateLongId(id, ENTITY_OFFER);
        JobOffer jobOffer = jobOfferService.findOfferById(id);
        if(jobOffer != null)
            return jobOfferReviewMapper.jobOfferToJobOfferDto(jobOffer, checkIfSaved(id));
        else
            throw new NoSuchElementFoundException("Offer with given id does not exist");
    }
}
