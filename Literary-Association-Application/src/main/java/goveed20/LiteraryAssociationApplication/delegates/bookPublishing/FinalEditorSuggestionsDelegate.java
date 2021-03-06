package goveed20.LiteraryAssociationApplication.delegates.bookPublishing;

import goveed20.LiteraryAssociationApplication.utils.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FinalEditorSuggestionsDelegate implements JavaDelegate {

    @Autowired
    private NotificationService notificationService;

    @SuppressWarnings("unchecked")
    @Override
    public void execute(DelegateExecution delegateExecution) {
        Map<String, Object> data = (Map<String, Object>) delegateExecution.getVariable("data");
        delegateExecution.setVariable("editor_suggested", !data.get("editor_suggestions").equals(""));
        delegateExecution.setVariable("editor_suggestion_comment", data.get("editor_suggestions"));

        notificationService.sendSuccessNotification("Suggestions successfully sent");
    }
}
