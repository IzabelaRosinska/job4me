package miwm.job4me.services.cv.pdf;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface CvPdfGenerateService {
    ResponseEntity<byte[]> downloadCvFile(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<byte[]> generateAndDownloadPdfFile(String templateName, Map<String, Object> data, String pdfFileName);
}
