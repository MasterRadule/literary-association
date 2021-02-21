package goveed20.LiteraryAssociationApplication.utils;

import goveed20.LiteraryAssociationApplication.dtos.PlagiarismResultDTO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class PlagiatorService {

    @Autowired
    private PlagiatorFeignClient plagiatorClient;

    public PlagiarismResultDTO uploadNewPaper(String title, String path) throws IOException {
        return plagiatorClient.uploadNewPaper(new MockMultipartFile(title, title + ".pdf", "text/plain",
                IOUtils.toByteArray(new FileInputStream(path))));
    }

    public PlagiarismResultDTO uploadExistingPaper(String title, String path) throws IOException {
        return plagiatorClient.uploadExistingPaper(new MockMultipartFile(title, title + ".pdf", "text/plain",
                IOUtils.toByteArray(new FileInputStream(path))));
    }

    public void deletePaper(Long id) {
        try {
            plagiatorClient.deletePaper(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
