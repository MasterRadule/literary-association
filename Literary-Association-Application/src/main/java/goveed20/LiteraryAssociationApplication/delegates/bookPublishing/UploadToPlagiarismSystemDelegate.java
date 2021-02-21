package goveed20.LiteraryAssociationApplication.delegates.bookPublishing;

import goveed20.LiteraryAssociationApplication.dtos.PaperDTO;
import goveed20.LiteraryAssociationApplication.dtos.PlagiarismResultDTO;
import goveed20.LiteraryAssociationApplication.model.WorkingPaper;
import goveed20.LiteraryAssociationApplication.repositories.WorkingPaperRepository;
import goveed20.LiteraryAssociationApplication.utils.PlagiatorService;
import goveed20.LiteraryAssociationApplication.utils.UtilService;
import lombok.SneakyThrows;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UploadToPlagiarismSystemDelegate implements JavaDelegate {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private WorkingPaperRepository workingPaperRepository;

    @Autowired
    private PlagiatorService plagiatorService;

    @SneakyThrows
    @Override
    public void execute(DelegateExecution delegateExecution) {
        String workingPaperTitle = (String) delegateExecution.getVariable("working_paper");
        WorkingPaper workingPaper = workingPaperRepository.findByTitle(workingPaperTitle);

        try {
            PlagiarismResultDTO results = plagiatorService
                    .uploadNewPaper(workingPaper.getTitle(), workingPaper.getFile());
            delegateExecution.setVariable("uploaded_paper_id", results.getUploadedPaper().getId());

            List<String> titlesAndPercents = results.getSimilarPapers().stream()
                    .filter(UtilService.distinctByKey(PaperDTO::getTitle))
                    .filter(p -> !p.getTitle().contains(workingPaperTitle))
                    .map(p -> String.format("Title: %s  Similar percent: %f%%", p.getTitle(), p.getSimilarProcent()))
                    .collect(Collectors.toList());
            delegateExecution.setVariable("similar_papers", titlesAndPercents);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            delegateExecution.setVariable("similar_papers", new ArrayList<>());
        }
    }
}
