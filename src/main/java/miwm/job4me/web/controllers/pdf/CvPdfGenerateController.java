package miwm.job4me.web.controllers.pdf;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.pdf.cv.CvPdfGenerateService;
import miwm.job4me.services.pdf.cv.CvPdfGenerateServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CvPdfGenerateController {
    private final CvPdfGenerateService cvPdfGenerateService;

    public CvPdfGenerateController(CvPdfGenerateServiceImpl cvPdfGenerateService) {
        this.cvPdfGenerateService = cvPdfGenerateService;
    }

    @GetMapping("employee/cv/pdf")
    @Operation(summary = "Download cv pdf file", description = "Downloads cv pdf file of signed in employee")
    public ResponseEntity<byte[]> downloadEJournalFile(HttpServletRequest request, HttpServletResponse response) {
        return cvPdfGenerateService.downloadCvFile(request, response);
    }
}
