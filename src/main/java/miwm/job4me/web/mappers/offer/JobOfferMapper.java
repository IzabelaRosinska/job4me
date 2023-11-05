package miwm.job4me.web.mappers.offer;

import miwm.job4me.model.offer.*;
import miwm.job4me.model.users.Employer;
import miwm.job4me.web.model.offer.JobOfferDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class JobOfferMapper {
    public JobOfferDto toDto(JobOffer jobOffer) {
        JobOfferDto jobOfferDto = new JobOfferDto();
        jobOfferDto.setId(jobOffer.getId());
        jobOfferDto.setOfferName(jobOffer.getOfferName());
        jobOfferDto.setEmployerId(jobOffer.getEmployer().getId());
        jobOfferDto.setIndustries(industriesSetToStringList(jobOffer.getIndustries()));
        jobOfferDto.setLocalizations(localizationsSetToStringList(jobOffer.getLocalizations()));
        jobOfferDto.setEmploymentForms(employmentFormsSetToStringList(jobOffer.getEmploymentForms()));
        jobOfferDto.setSalaryFrom(jobOffer.getSalaryFrom());
        jobOfferDto.setSalaryTo(jobOffer.getSalaryTo());
        jobOfferDto.setContractTypes(contractTypesSetToStringList(jobOffer.getContractTypes()));
        jobOfferDto.setWorkingTime(jobOffer.getWorkingTime());
        jobOfferDto.setLevels(levelsSetToStringList(jobOffer.getLevels()));
        jobOfferDto.setRequirements(requirementsSetToStringList(jobOffer.getRequirements()));
        jobOfferDto.setExtraSkills(extraSkillsSetToStringList(jobOffer.getExtraSkills()));
        jobOfferDto.setDuties(jobOffer.getDuties());
        jobOfferDto.setDescription(jobOffer.getDescription());

        return jobOfferDto;
    }

    public JobOffer toEntity(JobOfferDto jobOfferDto) {
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(jobOfferDto.getId());
        jobOffer.setOfferName(jobOfferDto.getOfferName());
        jobOffer.setEmployer(Employer.builder().id(jobOfferDto.getEmployerId()).build());
        jobOffer.setIndustries(stringListToIndustriesSet(jobOfferDto.getIndustries()));
        jobOffer.setLocalizations(stringListToLocalizationsSet(jobOfferDto.getLocalizations()));
        jobOffer.setEmploymentForms(stringListToEmploymentFormsSet(jobOfferDto.getEmploymentForms()));
        jobOffer.setSalaryFrom(jobOfferDto.getSalaryFrom());
        jobOffer.setSalaryTo(jobOfferDto.getSalaryTo());
        jobOffer.setContractTypes(stringListToContractTypesSet(jobOfferDto.getContractTypes()));
        jobOffer.setWorkingTime(jobOfferDto.getWorkingTime());
        jobOffer.setLevels(stringListToLevelsSet(jobOfferDto.getLevels()));
        jobOffer.setRequirements(stringListToRequirementsSet(jobOfferDto.getRequirements(), jobOffer));
        jobOffer.setExtraSkills(stringListToExtraSkillsSet(jobOfferDto.getExtraSkills(), jobOffer));
        jobOffer.setDuties(jobOfferDto.getDuties());
        jobOffer.setDescription(jobOfferDto.getDescription());

        return jobOffer;
    }

    public ArrayList<String> industriesSetToStringList(Set<Industry> industries) {
        ArrayList<String> industriesList = new ArrayList<>();

        if (industries != null) {
            for (Industry e : industries) {
                industriesList.add(e.getName());
            }
        }

        return industriesList;
    }

    public ArrayList<String> localizationsSetToStringList(Set<Localization> localizations) {
        ArrayList<String> localizationsList = new ArrayList<>();

        if (localizations != null) {
            for (Localization e : localizations) {
                localizationsList.add(e.getCity());
            }
        }

        return localizationsList;
    }

    public ArrayList<String> employmentFormsSetToStringList(Set<EmploymentForm> employmentForms) {
        ArrayList<String> employmentFormsList = new ArrayList<>();

        if (employmentForms != null) {
            for (EmploymentForm e : employmentForms) {
                employmentFormsList.add(e.getName());
            }
        }

        return employmentFormsList;
    }

    public ArrayList<String> contractTypesSetToStringList(Set<ContractType> contractTypes) {
        ArrayList<String> contractTypesList = new ArrayList<>();

        if (contractTypes != null) {
            for (ContractType e : contractTypes) {
                contractTypesList.add(e.getName());
            }
        }

        return contractTypesList;
    }

    public ArrayList<String> levelsSetToStringList(Set<Level> levels) {
        ArrayList<String> levelsList = new ArrayList<>();

        if (levels != null) {
            for (Level e : levels) {
                levelsList.add(e.getName());
            }
        }

        return levelsList;
    }

    public ArrayList<String> requirementsSetToStringList(Set<Requirement> requirements) {
        ArrayList<String> requirementsList = new ArrayList<>();

        if (requirements != null) {
            for (Requirement e : requirements) {
                requirementsList.add(e.getDescription());
            }
        }

        return requirementsList;
    }

    public ArrayList<String> extraSkillsSetToStringList(Set<ExtraSkill> extraSkills) {
        ArrayList<String> extraSkillsList = new ArrayList<>();

        if (extraSkills != null) {
            for (ExtraSkill e : extraSkills) {
                extraSkillsList.add(e.getDescription());
            }
        }

        return extraSkillsList;
    }

    public Set<Industry> stringListToIndustriesSet(ArrayList<String> industries) {
        Set<Industry> industriesSet = new HashSet<>();

        if (industries != null) {
            for (String e : industries) {
                Industry industryEntity = Industry.builder().name(e).build();
                industriesSet.add(industryEntity);
            }
        }

        return industriesSet;
    }

    public Set<Localization> stringListToLocalizationsSet(ArrayList<String> localizations) {
        Set<Localization> localizationsSet = new HashSet<>();

        if (localizations != null) {
            for (String e : localizations) {
                Localization localizationEntity = Localization.builder().city(e).build();
                localizationsSet.add(localizationEntity);
            }
        }

        return localizationsSet;
    }

    public Set<EmploymentForm> stringListToEmploymentFormsSet(ArrayList<String> employmentForms) {
        Set<EmploymentForm> employmentFormsSet = new HashSet<>();

        if (employmentForms != null) {
            for (String e : employmentForms) {
                EmploymentForm employmentFormEntity = EmploymentForm.builder().name(e).build();
                employmentFormsSet.add(employmentFormEntity);
            }
        }

        return employmentFormsSet;
    }

    public Set<ContractType> stringListToContractTypesSet(ArrayList<String> contractTypes) {
        Set<ContractType> contractTypesSet = new HashSet<>();

        if (contractTypes != null) {
            for (String e : contractTypes) {
                ContractType contractTypeEntity = ContractType.builder().name(e).build();
                contractTypesSet.add(contractTypeEntity);
            }
        }

        return contractTypesSet;
    }

    public Set<Level> stringListToLevelsSet(ArrayList<String> levels) {
        Set<Level> levelsSet = new HashSet<>();

        if (levels != null) {
            for (String e : levels) {
                Level levelEntity = Level.builder().name(e).build();
                levelsSet.add(levelEntity);
            }
        }

        return levelsSet;
    }

    public Set<Requirement> stringListToRequirementsSet(ArrayList<String> requirements, JobOffer jobOffer) {
        Set<Requirement> requirementsSet = new HashSet<>();

        if (requirements != null) {
            for (String e : requirements) {
                Requirement requirementEntity = Requirement.builder().description(e).jobOffer(jobOffer).build();
                requirementsSet.add(requirementEntity);
            }
        }

        return requirementsSet;
    }

    public Set<ExtraSkill> stringListToExtraSkillsSet(ArrayList<String> extraSkills, JobOffer jobOffer) {
        Set<ExtraSkill> extraSkillsSet = new HashSet<>();

        if (extraSkills != null) {
            for (String e : extraSkills) {
                ExtraSkill extraSkillEntity = ExtraSkill.builder().description(e).jobOffer(jobOffer).build();
                extraSkillsSet.add(extraSkillEntity);
            }
        }

        return extraSkillsSet;
    }
}
