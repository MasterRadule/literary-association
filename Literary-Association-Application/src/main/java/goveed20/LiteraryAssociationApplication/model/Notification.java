package goveed20.LiteraryAssociationApplication.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private Set<String> recepients;
    private String subject;
    private String content;
}
