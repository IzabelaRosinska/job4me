package miwm.job4me.web.controllers.pdf;

import miwm.job4me.services.cv.pdf.CvPdfGenerateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CvPdfGenerateController {
    private final CvPdfGenerateService cvPdfGenerateService;

    public CvPdfGenerateController(CvPdfGenerateService cvPdfGenerateService) {
        this.cvPdfGenerateService = cvPdfGenerateService;
    }

    @GetMapping("employee/cv/pdf")
    public ResponseEntity<byte[]> downloadEJournalFile(HttpServletRequest request, HttpServletResponse response) {
        return cvPdfGenerateService.downloadCvFile(request, response);
    }
}
