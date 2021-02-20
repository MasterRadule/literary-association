package goveed20.LiteraryAssociationApplication.elastic.indexingUnits;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(indexName = "beta-readers", replicas = 0, type = "beta-reader")
public class BetaReaderIndexingUnit {

    @Id
    @Field(type = FieldType.Long, store = true)
    private Long id;

    @Field(type = FieldType.Text)
    private String username;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Text, store = true, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
    private String name;

    @Field(type = FieldType.Keyword)
    private List<String> betaGenres;
}
