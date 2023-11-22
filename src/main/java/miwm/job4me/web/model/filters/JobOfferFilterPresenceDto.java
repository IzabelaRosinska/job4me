package miwm.job4me.web.model.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobOfferFilterPresenceDto {
    private Boolean isCitiesDefined;
    private Boolean isEmploymentFormNamesDefined;
    private Boolean isLevelNamesDefined;
    private Boolean isContractTypeNamesDefined;
    private Boolean isIndustryNamesDefined;
    private Boolean isOfferIdsDefined;
    private Boolean isEmployerIdsDefined;

}
