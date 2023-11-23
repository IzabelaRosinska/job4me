package miwm.job4me.services.pdf.cv;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CvPdfGenerateService {

    ResponseEntity<byte[]> downloadCvFile(HttpServletRequest request, HttpServletResponse response);
}
