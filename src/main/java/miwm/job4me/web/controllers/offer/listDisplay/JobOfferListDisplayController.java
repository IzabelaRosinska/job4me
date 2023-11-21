package miwm.job4me.web.controllers.offer.listDisplay;

import miwm.job4me.services.offer.listDisplay.JobOfferListDisplayService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobOfferListDisplayController {
    private final JobOfferListDisplayService jobOfferListDisplayService;

    public JobOfferListDisplayController(JobOfferListDisplayService jobOfferListDisplayService) {
        this.jobOfferListDisplayService = jobOfferListDisplayService;
    }

}
