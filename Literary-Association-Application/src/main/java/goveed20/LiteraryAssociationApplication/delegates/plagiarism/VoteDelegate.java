package goveed20.LiteraryAssociationApplication.delegates.plagiarism;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VoteDelegate implements JavaDelegate {

    @SuppressWarnings("unchecked")
    @Override
    public void execute(DelegateExecution delegateExecution) {
        Map<String, Object> data = (Map<String, Object>) delegateExecution.getVariable("data");
        Map<String, String> boardMembers = (Map<String, String>) delegateExecution.getVariable("board_members");
        boardMembers.replace((String) delegateExecution.getVariable("current_board_member"), (String) data.get("vote_option"));
        delegateExecution.setVariable("board_members", boardMembers);
    }
}
