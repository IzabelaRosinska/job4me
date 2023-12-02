package miwm.job4me.services.pdf;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.web.model.pdf.PdfDto;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private TemplateEngine templateEngine;

    public PdfGenerateServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public PdfDto generateAndDownloadPdfFile(String templateName, Map<String, Object> data, List<String> fonts) {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);

        try {
            ByteArrayOutputStream target = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            if (fonts != null) {
                for (String font : fonts) {
                    renderer.getFontResolver().addFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                }
            }

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(target, false);
            renderer.finishPDF();

            byte[] bytes = target.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(bytes);

            PdfDto pdfDto = new PdfDto();
            pdfDto.setEncodedPdf(base64String);

            return pdfDto;
        } catch (DocumentException e) {
            throw new InvalidArgumentException("Document exception");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
