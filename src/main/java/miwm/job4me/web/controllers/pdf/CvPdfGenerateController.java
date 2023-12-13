package miwm.job4me.web.controllers.pdf;

import io.swagger.v3.oas.annotations.Operation;
import miwm.job4me.services.pdf.cv.CvPdfGeneratorService;
import miwm.job4me.services.pdf.cv.CvPdfGeneratorServiceImpl;
import miwm.job4me.web.model.pdf.PdfDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CvPdfGenerateController {
    private final CvPdfGeneratorService cvPdfGeneratorService;

    public CvPdfGenerateController(CvPdfGeneratorServiceImpl cvPdfGenerateService) {
        this.cvPdfGeneratorService = cvPdfGenerateService;
    }

    @GetMapping("employee/cv/pdf")
    @Operation(summary = "Download cv pdf file", description = "Downloads cv pdf file of signed in employee")
    public ResponseEntity<PdfDto> downloadCV() {
        return new ResponseEntity<>(cvPdfGeneratorService.generateCvFile(), HttpStatus.OK);
    }

}
