package goveed20.LiteraryAssociationApplication.elastic.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchParamsDTO {

    @NotNull
    @NotEmpty
    private List<SearchParamDTO> params;

    @NotNull
    private Integer pageNumber;

}
