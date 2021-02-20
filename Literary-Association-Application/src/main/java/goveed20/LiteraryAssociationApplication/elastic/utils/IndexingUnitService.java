package goveed20.LiteraryAssociationApplication.elastic.utils;

import goveed20.LiteraryAssociationApplication.elastic.indexingUnits.BetaReaderIndexingUnit;
import goveed20.LiteraryAssociationApplication.elastic.indexingUnits.BookIndexingUnit;
import goveed20.LiteraryAssociationApplication.elastic.repositories.BetaReaderIndexingUnitRepository;
import goveed20.LiteraryAssociationApplication.elastic.repositories.BookIndexingUnitRepository;
import goveed20.LiteraryAssociationApplication.model.Book;
import goveed20.LiteraryAssociationApplication.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.stream.Collectors;

@Service
public class IndexingUnitService {

    @Autowired
    private PDFService pdfService;

    @Autowired
    private BookIndexingUnitRepository bookIndexingUnitRepository;

    @Autowired
    private BetaReaderIndexingUnitRepository betaReaderIndexingUnitRepository;

    public void createBookIndexingUnit(Book book) {
        bookIndexingUnitRepository.save(
                BookIndexingUnit.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .genre(book.getGenre().getGenre().serbianName)
                        .openAccess(book.getPrice() == 0.0)
                        .writers(book.getAdditionalAuthors())
                        .basicInfo(UtilService.getBookBasicInfo(book))
                        .text(pdfService.getText(new File(book.getFile())))
                        .build()
        );
    }

    public void createBetaReaderIndexingUnit(Reader reader) {
        betaReaderIndexingUnitRepository.save(
                BetaReaderIndexingUnit.builder()
                        .id(reader.getId())
                        .username(reader.getUsername())
                        .location(new GeoPoint(reader.getLocation().getLatitude(), reader.getLocation().getLongitude()))
                        .name(String.format("%s %s", reader.getName(), reader.getSurname()))
                        .betaGenres(reader.getBetaReaderStatus().getBetaGenres().stream().map(g -> g.getGenre().serbianName)
                                .collect(Collectors.toList()))
                        .build()
        );
    }
}
