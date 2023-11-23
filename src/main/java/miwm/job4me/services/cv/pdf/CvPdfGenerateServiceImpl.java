package miwm.job4me.services.cv.pdf;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;
import miwm.job4me.exceptions.InvalidArgumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CvPdfGenerateServiceImpl implements CvPdfGenerateService {
    private TemplateEngine templateEngine;

    public CvPdfGenerateServiceImpl(TemplateEngine templateEngine) {
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
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(target, true);
            renderer.finishPDF();

            byte[] bytes = target.toByteArray();

            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/pdf");
            System.out.println("htmlContent");
            System.out.println(htmlContent);

            System.out.println("bytes");
            System.out.println(bytes);

            return ResponseEntity.ok()
                    .headers(header)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bytes);

        } catch (DocumentException e) {
            throw new InvalidArgumentException("Document exception");
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadCvFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> data = new HashMap<>();
        Customer customer = new Customer();
        customer.setCompanyName("Simple Solution");
        customer.setContactName("John Doe");
        customer.setAddress("123, Simple Street");
        customer.setEmail("john@simplesolution.dev");
        customer.setPhone("123 456 789");
        data.put("customer", customer);

        List<QuoteItem> quoteItems = new ArrayList<>();
        QuoteItem quoteItem1 = new QuoteItem();
        quoteItem1.setDescription("Test Quote Item 1");
        quoteItem1.setQuantity(1);
        quoteItem1.setUnitPrice(100.0);
        quoteItem1.setTotal(100.0);
        quoteItems.add(quoteItem1);

        QuoteItem quoteItem2 = new QuoteItem();
        quoteItem2.setDescription("Test Quote Item 2");
        quoteItem2.setQuantity(4);
        quoteItem2.setUnitPrice(500.0);
        quoteItem2.setTotal(2000.0);
        quoteItems.add(quoteItem2);

        QuoteItem quoteItem3 = new QuoteItem();
        quoteItem3.setDescription("Test Quote Item 3");
        quoteItem3.setQuantity(2);
        quoteItem3.setUnitPrice(200.0);
        quoteItem3.setTotal(400.0);
        quoteItems.add(quoteItem3);

        data.put("quoteItems", quoteItems);

        return generateAndDownloadPdfFile("quotation", data, "quotation.pdf");
    }

}
