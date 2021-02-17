package goveed20.LiteraryAssociationApplication.elastic.utils;

import goveed20.LiteraryAssociationApplication.model.Book;

public class UtilService {

    public static String getBookBasicInfo(Book book) {
        String authors = String.format("%s %s,%s", book.getWriter().getName(), book.getWriter().getSurname(), book
                .getAdditionalAuthors());

        return String.format("%s - %s, %s, %d", authors, book.getPublisher(), book.getPublicationPlace(), book
                .getPublicationYear());
    }
}
