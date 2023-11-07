package miwm.job4me.model.offer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.users.Employer;
import miwm.job4me.validators.fields.ValidSalaryRange;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ValidSalaryRange
@Entity
@Table(name = "job_offers")
public class JobOffer extends BaseEntity {

    @Builder
    public JobOffer(Long id, String offerName, Employer employer, Set<Industry> industries, Set<Localization> localizations, Set<EmploymentForm> employmentForms, Integer salaryFrom, Integer salaryTo, Set<ContractType> contractTypes, String workingTime, Set<Level> levels, Set<Requirement> requirements, Set<ExtraSkill> extraSkills, String duties, String description) {
        super(id);
        this.offerName = offerName;
        this.employer = employer;
        this.industries = industries;
        this.localizations = localizations;
        this.employmentForms = employmentForms;
        this.salaryFrom = salaryFrom;
        this.salaryTo = salaryTo;
        this.contractTypes = contractTypes;
        this.workingTime = workingTime;
        this.levels = levels;
        this.requirements = requirements;
        this.extraSkills = extraSkills;
        this.duties = duties;
        this.description = description;
    }

    @NotBlank(message = "JobOffer name cannot be blank")
    @Size(min = 1, max = 120, message = "JobOffer name must be between 1 and 120 characters")
    @Column(name = "offer_name", length = 120)
    private String offerName;

    @NotNull(message = "JobOffer employer cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @Size(min = 1, max = 10, message = "JobOffer industries list length must be between 1 and 10")
    @ManyToMany
    @JoinTable(
            name = "job_offer_industries",
            joinColumns = @JoinColumn(name = "job_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "industry_id"))
    private Set<Industry> industries;

    @Size(min = 1, max = 10, message = "JobOffer localizations list length must be between 1 and 10")
    @ManyToMany
    @JoinTable(
            name = "job_offer_localizations",
            joinColumns = @JoinColumn(name = "job_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "localization_id"))
    private Set<Localization> localizations;

    @Size(min = 1, message = "JobOffer employmentForms cannot be empty")
    @ManyToMany
    @JoinTable(
            name = "job_offer_employment_forms",
            joinColumns = @JoinColumn(name = "job_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "employment_form_id"))
    private Set<EmploymentForm> employmentForms;

    @NotNull(message = "JobOffer salaryFrom cannot be null")
    @Column(name = "salary_from")
    private Integer salaryFrom;

    @Column(name = "salary_to")
    private Integer salaryTo;

    @ManyToMany
    @JoinTable(
            name = "job_offer_contract_types",
            joinColumns = @JoinColumn(name = "job_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_type_id"))
    private Set<ContractType> contractTypes;

    @NotBlank(message = "JobOffer workingTime cannot be blank")
    @Size(min = 1, max = 20, message = "JobOffer workingTime must be between 1 and 20 characters")
    @Column(name = "working_time", length = 20)
    private String workingTime;

    @Size(min = 1, message = "JobOffer levels cannot be empty")
    @ManyToMany
    @JoinTable(
            name = "job_offer_levels",
            joinColumns = @JoinColumn(name = "job_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id"))
    private Set<Level> levels;

    @Size(min = 1, max = 15, message = "JobOffer requirements list length must be between 1 and 15")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobOffer")
    private Set<Requirement> requirements;

    @Size(max = 10, message = "JobOffer extraSkills list length must be lower than 10")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobOffer")
    private Set<ExtraSkill> extraSkills;

    @Size(max = 1000, message = "JobOffer duties length must be lower than 1000")
    @Column(name = "duties", length = 1000)
    private String duties;

    @Size(max = 500, message = "JobOffer description length must be lower than 500")
    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "duties_embeddings", length = 3072)
    private byte[] dutiesEmbeddings;

    @Column(name = "description_embeddings", length = 3072)
    private byte[] descriptionEmbeddings;

    @Column(name = "skills_embeddings", length = 3072)
    private byte[] skillsEmbeddings;

}
