package goveed20.LiteraryAssociationApplication.elastic.utils;

import lombok.SneakyThrows;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PDFService {

    @SneakyThrows
    public String getText(File pdfFile) {
        PDFParser parser = new PDFParser(new RandomAccessFile(pdfFile, "r"));
        parser.parse();
        PDFTextStripper stripper = new PDFTextStripper();
        return stripper.getText(parser.getPDDocument());
    }
}
