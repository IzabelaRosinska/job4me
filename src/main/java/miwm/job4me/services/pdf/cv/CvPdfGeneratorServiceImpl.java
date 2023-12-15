package miwm.job4me.services.pdf.cv;

import miwm.job4me.services.pdf.PdfGeneratorService;
import miwm.job4me.services.users.EmployeeService;
import miwm.job4me.web.model.pdf.PdfDto;
import miwm.job4me.web.model.users.EmployeeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CvPdfGeneratorServiceImpl implements CvPdfGeneratorService {
    private final EmployeeService employeeService;
    private final PdfGeneratorService pdfGeneratorService;
    private final String CV_TEMPLATE_NAME = "cv";

    @Value("${cv.font}")
    private String font;

    public CvPdfGeneratorServiceImpl(EmployeeService employeeService, PdfGeneratorService pdfGeneratorService) {
        this.employeeService = employeeService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @Override
    public PdfDto generateCvFile() {
        EmployeeDto employee = employeeService.findCurrentEmployee();
        Map<String, Object> data = new HashMap<>();

        data.put("employee", employee);

        List<String> fonts = new ArrayList<>();
        fonts.add(font);

        return pdfGeneratorService.generatePdfFile(CV_TEMPLATE_NAME, data, fonts);
    }

}
