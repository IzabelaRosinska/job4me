package miwm.job4me.services.pdf;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PdfGenerateService {

    ResponseEntity<byte[]> generateAndDownloadPdfFile(String templateName, Map<String, Object> data, List<String> fonts);

}
