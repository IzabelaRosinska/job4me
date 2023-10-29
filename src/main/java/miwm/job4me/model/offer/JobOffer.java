package miwm.job4me.model.offer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.users.Employer;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "job_offers")
public class JobOffer extends BaseEntity {
    @Column(name = "offer_name", length = 100)
    private String offerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @ManyToMany
    @JoinTable(
            name = "job_offer_industries",
            joinColumns = @JoinColumn(name = "job_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "industry_id"))
    private Set<Industry> industries;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobOffer")
    private Set<Address> addresses;

    @ManyToMany
    @JoinTable(
            name = "job_offer_employment_forms",
            joinColumns = @JoinColumn(name = "job_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "employment_form_id"))
    private Set<EmploymentForm> employmentForms;

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

    @Column(name = "working_time", length = 20)
    private String workingTime;

    @ManyToMany
    @JoinTable(
            name = "job_offer_levels",
            joinColumns = @JoinColumn(name = "job_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id"))
    private Set<Level> levels;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobOffer")
    private Set<Requirement> requirements;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobOffer")
    private Set<ExtraSkill> extraSkills;

    @Column(name = "duties", length = 1000)
    private String duties;

    @Column(name = "description", length = 500)
    private String description;

}
