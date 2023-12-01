package miwm.job4me.services.pdf.cv;

import miwm.job4me.web.model.pdf.PdfDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CvPdfGenerateService {

    byte[] downloadCvFile(HttpServletRequest request, HttpServletResponse response);
}
