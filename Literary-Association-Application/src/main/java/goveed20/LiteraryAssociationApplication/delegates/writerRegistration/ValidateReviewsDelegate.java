package goveed20.LiteraryAssociationApplication.delegates.writerRegistration;

import goveed20.LiteraryAssociationApplication.utils.NotificationService;
import goveed20.LiteraryAssociationApplication.utils.ReviewResult;
import goveed20.LiteraryAssociationApplication.utils.ReviewStatus;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ValidateReviewsDelegate implements JavaDelegate {

    @Autowired
    private NotificationService notificationService;

    @SuppressWarnings("unchecked")
    @Override
    public void execute(DelegateExecution delegateExecution) {
        Map<String, Object> data = (Map<String, Object>) delegateExecution.getVariable("data");
        List<ReviewResult> reviewResults = getOrCreateReviewResultList(delegateExecution);

        ReviewResult reviewResult = ReviewResult.builder()
                .status(ReviewStatus.valueOf(String.valueOf(data.get("status"))))
                .comment(String.valueOf(data.get("comments")))
                .build();

        reviewResults.add(reviewResult);
        delegateExecution.setVariable("review_results", reviewResults);

        notificationService.sendSuccessNotification("Review successfully sent");
    }

    @SuppressWarnings("unchecked")
    private List<ReviewResult> getOrCreateReviewResultList(DelegateExecution delegateExecution) {
        if (delegateExecution.hasVariable("review_results")) {
            return (List<ReviewResult>) delegateExecution.getVariable("review_results");
        }
        return new ArrayList<>();
    }
}
