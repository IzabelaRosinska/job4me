package miwm.job4me.services.pdf;

import miwm.job4me.web.model.pdf.PdfDto;

import java.util.List;
import java.util.Map;

public interface PdfGenerateService {

    byte[] generateAndDownloadPdfFile(String templateName, Map<String, Object> data, List<String> fonts);

}
