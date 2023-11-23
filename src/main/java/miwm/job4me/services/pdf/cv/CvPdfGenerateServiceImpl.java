package miwm.job4me.services.pdf.cv;

import miwm.job4me.model.users.Employee;
import miwm.job4me.services.pdf.PdfGenerateService;
import miwm.job4me.services.users.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class CvPdfGenerateServiceImpl implements CvPdfGenerateService {
    private final EmployeeService employeeService;
    private final PdfGenerateService pdfGenerateService;
    private final String CV_TEMPLATE_NAME = "cv";

    public CvPdfGenerateServiceImpl(EmployeeService employeeService, PdfGenerateService pdfGenerateService) {
        this.employeeService = employeeService;
        this.pdfGenerateService = pdfGenerateService;
    }

    @Override
    public ResponseEntity<byte[]> downloadCvFile(HttpServletRequest request, HttpServletResponse response) {
        Employee employee = employeeService.getAuthEmployee();
        String fileName = createCvFileName(employee);

        Map<String, Object> data = new HashMap<>();

        data.put("employee", employee);
        data.put("education", employee.getEducation());
        data.put("experience", employee.getExperience());
        data.put("skills", employee.getSkills());
        data.put("projects", employee.getProjects());

        return pdfGenerateService.generateAndDownloadPdfFile(CV_TEMPLATE_NAME, data, fileName);
    }

    private String createCvFileName(Employee employee) {
        return employee.getFirstName() + "_" + employee.getLastName() + "_CV.pdf";
    }

}
