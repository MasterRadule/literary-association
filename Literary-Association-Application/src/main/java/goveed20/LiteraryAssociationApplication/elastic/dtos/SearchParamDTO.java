package goveed20.LiteraryAssociationApplication.elastic.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SearchParamDTO {

    @NotBlank
    private String field;

    @NotBlank
    private String searchValue;

    @NotNull
    private Boolean phrazeQuery;

    @NotNull
    private Boolean andOperation;
}
