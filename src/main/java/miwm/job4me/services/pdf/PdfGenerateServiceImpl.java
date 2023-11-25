package miwm.job4me.services.pdf;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import miwm.job4me.exceptions.InvalidArgumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.util.Map;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private TemplateEngine templateEngine;

    public PdfGenerateServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public ResponseEntity<byte[]> generateAndDownloadPdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);

        try {
            ByteArrayOutputStream target = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont("src/main/resources/static/fonts/Roboto-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.getFontResolver().addFont("src/main/resources/static/fonts/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.getFontResolver().addFont("src/main/resources/static/fonts/Roboto-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.getFontResolver().addFont("src/main/resources/static/fonts/Roboto-Medium.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.getFontResolver().addFont("src/main/resources/static/fonts/Roboto-Thin.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(target, true);
            renderer.finishPDF();

            byte[] bytes = target.toByteArray();

            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/pdf");

            return ResponseEntity.ok()
                    .headers(header)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bytes);

        } catch (DocumentException e) {
            throw new InvalidArgumentException("Document exception");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
