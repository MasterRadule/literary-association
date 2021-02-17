package goveed20.LiteraryAssociationApplication.elastic.repositories;

import goveed20.LiteraryAssociationApplication.elastic.indexingUnits.BookIndexingUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookIndexingUnitRepository extends ElasticsearchRepository<BookIndexingUnit, Long> {
}
