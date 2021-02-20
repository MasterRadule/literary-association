package goveed20.LiteraryAssociationApplication.delegates.bookPublishing;

import goveed20.LiteraryAssociationApplication.dtos.PaperDTO;
import goveed20.LiteraryAssociationApplication.dtos.PlagiarismResultDTO;
import goveed20.LiteraryAssociationApplication.model.WorkingPaper;
import goveed20.LiteraryAssociationApplication.repositories.WorkingPaperRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UploadToPlagiarismSystemDelegate implements JavaDelegate {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private WorkingPaperRepository workingPaperRepository;

    @SneakyThrows
    @Override
    public void execute(DelegateExecution delegateExecution) {
        String workingPaperTitle = (String) delegateExecution.getVariable("working_paper");
        WorkingPaper workingPaper = workingPaperRepository.findByTitle(workingPaperTitle);

        MultipartFile file = new MockMultipartFile(workingPaperTitle, workingPaperTitle, "application/pdf",
                IOUtils.toByteArray(new FileInputStream(workingPaper.getFile())));

        PaperDTO paperDTO = PaperDTO.builder().title(workingPaperTitle).file(file).build();

        HttpEntity<PaperDTO> request = new HttpEntity<>(paperDTO);
        ResponseEntity<PlagiarismResultDTO> response = restTemplate
                .postForEntity("localhost:9091/api/file/upload/new", request, PlagiarismResultDTO.class);

        PlagiarismResultDTO results = response.getBody();

        List<String> titlesAndPercents = results.getSimilarPapers().stream()
                .map(p -> String.format("%s %f%%", p.getTitle(), p.getSimilarProcent())).collect(Collectors.toList());

        delegateExecution.setVariable("similar_papers", titlesAndPercents);
    }
}
