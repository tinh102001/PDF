import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.ArrayList;

public class Main {
//    public static void main(String[] args) throws IOException, DocumentException {
//        for (int i = 1; i <= 10; i++) {
//            String outputPathDocx = "output_" + i + ".docx";
//            String outputPathPdf = "output_" + i + ".pdf";
//
//            // Create DOCX from template
//            try (InputStream templateStream = new FileInputStream("D:\\Project\\template.docx");
//                 OutputStream docxOutputStream = new FileOutputStream(outputPathDocx)) {
//
//                XWPFDocument doc = new XWPFDocument(templateStream);
//                for (XWPFParagraph p : doc.getParagraphs()) {
//                    for (XWPFRun r : p.getRuns()) {
//                        String text = r.getText(0);
//                        if (text != null && text.contains("test")) {
//                            text = text.replace("test", "newTest");
//                            r.setText(text, 0);
//                        }
//                    }
//                }
//                doc.write(docxOutputStream);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            // Convert DOCX to PDF
//            try (InputStream docxInputStream = new FileInputStream(outputPathDocx);
//                 OutputStream pdfOutputStream = new FileOutputStream(outputPathPdf)) {
//
//                XWPFDocument docx = new XWPFDocument(docxInputStream);
//                Document pdf = new Document();
//                PdfWriter.getInstance(pdf, pdfOutputStream);
//                pdf.open();
//
//                for (XWPFParagraph p : docx.getParagraphs()) {
//                    String text = p.getText();
//                    pdf.add(new Paragraph(text));
//                }
//
//                pdf.close();
//            } catch (IOException | DocumentException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//        Document document = new Document();
//        PdfCopy copy = new PdfCopy(document, new FileOutputStream("D:\\Project\\Java\\data\\output.pdf"));
//        document.open();
//
//        for (int i = 1; i <= 10; i++) {
//            PdfReader reader = new PdfReader("output_" + i + ".pdf");
//            copy.addDocument(reader);
//            reader.close();
//        }
//
//        document.close();
//    }

    public static void main(String[] args) throws IOException, DocumentException {
        ArrayList<byte[]> pdfFiles = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            XWPFDocument doc;
            try (InputStream templateStream = new FileInputStream("D:\\Project\\template.docx")) {
                doc = new XWPFDocument(templateStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (XWPFParagraph p : doc.getParagraphs()) {
                for (XWPFRun r : p.getRuns()) {
                    String text = r.getText(0);
                    if (text != null && text.contains("test")) {
                        text = text.replace("test", "newTest123");
                        r.setText(text, 0);
                    }
                }
            }
            try (ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
                Document pdfDocument = new Document();
                PdfWriter.getInstance(pdfDocument, pdfOutputStream);
                pdfDocument.open();

                for (XWPFParagraph p : doc.getParagraphs()) {
                    String text = p.getText();
                    pdfDocument.add(new Paragraph(text));
                }

                pdfDocument.close();
                pdfFiles.add(pdfOutputStream.toByteArray());
            } catch (IOException | DocumentException e) {
                throw new RuntimeException(e);
            }
        }
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream("D:\\Project\\Java\\data\\output.pdf"));
        document.open();

        for (byte[] pdfFile : pdfFiles) {
            PdfReader reader = new PdfReader(pdfFile);
            copy.addDocument(reader);
            reader.close();
        }

        document.close();
    }
}