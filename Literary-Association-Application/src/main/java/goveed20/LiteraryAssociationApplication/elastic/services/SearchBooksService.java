package goveed20.LiteraryAssociationApplication.elastic.services;

import goveed20.LiteraryAssociationApplication.elastic.dtos.SearchParamsDTO;
import goveed20.LiteraryAssociationApplication.elastic.indexingUnits.BookIndexingUnit;
import goveed20.LiteraryAssociationApplication.elastic.utils.BooksMapper;
import goveed20.LiteraryAssociationApplication.model.Reader;
import goveed20.LiteraryAssociationApplication.services.BookService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SearchBooksService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private BookService bookService;

    public Page<BookIndexingUnit> searchBooks(SearchParamsDTO searchParams) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        Reader reader = (goveed20.LiteraryAssociationApplication.model.Reader) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Set<String> myBookTitles = bookService.getBookTitles(reader);
        myBookTitles.forEach(title -> boolQueryBuilder.mustNot(QueryBuilders.commonTermsQuery("title", title)));

        searchParams.getParams().forEach(param -> {

            String field = param.getField();
            String searchValue = param.getSearchValue();

            if (!param.getAndOperation()) {
                if (param.getPhrazeQuery()) {
                    boolQueryBuilder.should(QueryBuilders.matchPhraseQuery(field, searchValue));
                } else {
                    boolQueryBuilder.should(QueryBuilders.commonTermsQuery(field, searchValue));
                }
            } else {
                if (param.getPhrazeQuery()) {
                    boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(field, searchValue));
                } else {
                    boolQueryBuilder.must(QueryBuilders.commonTermsQuery(field, searchValue));
                }
            }
        });

        SearchQuery query = nativeSearchQueryBuilder.withQuery(boolQueryBuilder).withHighlightFields(
                new HighlightBuilder.Field("text")
                        .preTags("<b>")
                        .postTags("</b>")
                        .numOfFragments(1)
                        .fragmentSize(200)).withPageable(PageRequest.of(searchParams.getPageNumber(), 5)).build();

        return elasticsearchTemplate.queryForPage(query, BookIndexingUnit.class, new BooksMapper());
    }
}
