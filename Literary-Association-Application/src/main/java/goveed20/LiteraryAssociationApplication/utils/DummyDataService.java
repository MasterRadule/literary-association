package goveed20.LiteraryAssociationApplication.utils;

import goveed20.LiteraryAssociationApplication.elastic.utils.IndexingUnitService;
import goveed20.LiteraryAssociationApplication.model.*;
import goveed20.LiteraryAssociationApplication.model.enums.GenreEnum;
import goveed20.LiteraryAssociationApplication.model.enums.TransactionStatus;
import goveed20.LiteraryAssociationApplication.model.enums.UserRole;
import goveed20.LiteraryAssociationApplication.repositories.BaseUserRepository;
import goveed20.LiteraryAssociationApplication.repositories.BookRepository;
import goveed20.LiteraryAssociationApplication.repositories.GenreRepository;
import goveed20.LiteraryAssociationApplication.repositories.RetailerRepository;
import goveed20.LiteraryAssociationApplication.services.CamundaUserService;
import goveed20.LiteraryAssociationApplication.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class DummyDataService {

    private static final String booksFolder = "Literary-Association-Application/src/main/resources/books/";

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CamundaUserService camundaUserService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RetailerRepository retailerRepository;

    @Autowired
    private IndexingUnitService indexingUnitService;

    @Autowired
    private PlagiatorService plagiatorService;

    @EventListener(ApplicationReadyEvent.class)
    public void addDummyData() throws IOException, InterruptedException {
        indexingUnitService.deleteAllIndexes();
        Location belgrade = locationService.createLocation("Serbia", "Belgrade");
        Thread.sleep(1000);
        Location stuttgart = locationService.createLocation("Germany", "Stuttgart");
        Thread.sleep(1000);
        Location subotica = locationService.createLocation("Serbia", "Subotica");

        if (baseUserRepository.findAllByRole(UserRole.BOARD_MEMBER).isEmpty()) {
            BaseUser boardMember1 = BaseUser.builder()
                    .name("board_member_1_name")
                    .surname("board_member_1_surname")
                    .email("board_member_1@test.com")
                    .username("boardMember1")
                    .password(passwordEncoder.encode("board_member_1"))
                    .verified(true)
                    .role(UserRole.BOARD_MEMBER)
                    .location(Location.builder().longitude(0.0).latitude(0.0).city("test1").country("test1").build())
                    .build();

            BaseUser boardMember2 = BaseUser.builder()
                    .name("board_member_2_name")
                    .surname("board_member_2_surname")
                    .email("board_member_2@test.com")
                    .username("boardMember2")
                    .verified(true)
                    .role(UserRole.BOARD_MEMBER)
                    .password(passwordEncoder.encode("board_member_2"))
                    .location(Location.builder().longitude(0.0).latitude(0.0).city("test2").country("test2").build())
                    .build();

            baseUserRepository.save(boardMember1);
            baseUserRepository.save(boardMember2);

            camundaUserService.createCamundaUser(boardMember1);
            camundaUserService.createCamundaUser(boardMember2);

            System.out.println("Created dummy board members!");
        }

        if (baseUserRepository.findAllByRole(UserRole.WRITER).isEmpty() && bookRepository.findAll()
                .isEmpty() && retailerRepository.findAll().isEmpty()
                && baseUserRepository.findAllByRole(UserRole.READER).isEmpty()) {

            if (genreRepository.findAll().isEmpty()) {
                Arrays.stream(GenreEnum.values()).forEach(e -> genreRepository.save(new Genre(null, e)));
            }

            Genre g1 = genreRepository.findByGenre(GenreEnum.ADVENTURE);
            Genre g2 = genreRepository.findByGenre(GenreEnum.FANTASY);
            Genre g3 = genreRepository.findByGenre(GenreEnum.EROTIC);

            Set<Genre> betaGenres1 = new HashSet<>();
            betaGenres1.add(g1);
            BetaReaderStatus brs1 = BetaReaderStatus.builder().betaGenres(betaGenres1).betaReaderPapers(new HashSet<>())
                    .penaltyPoints(0)
                    .build();
            Reader reader1 = Reader.readerBuilder()
                    .role(UserRole.READER)
                    .username("reader1")
                    .password(passwordEncoder.encode("password1"))
                    .name("Marko")
                    .surname("Markovic")
                    .email("reader1@maildrop.cc")
                    .comments(new HashSet<>())
                    .transactions(new HashSet<>())
                    .genres(new HashSet<>())
                    .betaReader(true)
                    .location(belgrade.toBuilder().build())
                    .verified(true)
                    .build();
            brs1.setReader(reader1);
            reader1.setBetaReaderStatus(brs1);

            Set<Genre> betaGenres2 = new HashSet<>();
            betaGenres2.add(g1);
            BetaReaderStatus brs2 = BetaReaderStatus.builder().betaGenres(betaGenres2).betaReaderPapers(new HashSet<>())
                    .penaltyPoints(0)
                    .build();
            Reader reader2 = Reader.readerBuilder()
                    .role(UserRole.READER)
                    .username("reader2")
                    .password(passwordEncoder.encode("password2"))
                    .name("Edo")
                    .surname("Teka")
                    .email("reader2@maildrop.cc")
                    .comments(new HashSet<>())
                    .transactions(new HashSet<>())
                    .genres(new HashSet<>())
                    .betaReader(true)
                    .location(stuttgart.toBuilder().build())
                    .verified(true)
                    .build();
            brs2.setReader(reader2);
            reader2.setBetaReaderStatus(brs2);

            Set<Genre> betaGenres3 = new HashSet<>();
            betaGenres3.add(g1);
            BetaReaderStatus brs3 = BetaReaderStatus.builder().betaGenres(betaGenres3).betaReaderPapers(new HashSet<>())
                    .penaltyPoints(0)
                    .build();
            Reader reader3 = Reader.readerBuilder()
                    .role(UserRole.READER)
                    .username("reader3")
                    .password(passwordEncoder.encode("password3"))
                    .name("Milos")
                    .surname("Vucic")
                    .email("reader3@maildrop.cc")
                    .comments(new HashSet<>())
                    .transactions(new HashSet<>())
                    .genres(new HashSet<>())
                    .betaReader(true)
                    .location(stuttgart.toBuilder().build())
                    .verified(true)
                    .build();
            brs3.setReader(reader3);
            reader3.setBetaReaderStatus(brs3);

            Set<Genre> betaGenres4 = new HashSet<>();
            betaGenres4.add(g1);
            BetaReaderStatus brs4 = BetaReaderStatus.builder().betaGenres(betaGenres4).betaReaderPapers(new HashSet<>())
                    .penaltyPoints(0)
                    .build();
            Reader reader4 = Reader.readerBuilder()
                    .role(UserRole.READER)
                    .username("reader4")
                    .password(passwordEncoder.encode("password4"))
                    .name("Mirko")
                    .surname("Mirkovic")
                    .email("reader4@maildrop.cc")
                    .comments(new HashSet<>())
                    .transactions(new HashSet<>())
                    .genres(new HashSet<>())
                    .betaReader(true)
                    .location(stuttgart.toBuilder().build())
                    .verified(true)
                    .build();
            brs4.setReader(reader4);
            reader4.setBetaReaderStatus(brs4);

            Writer writer1 = Writer.writerBuilder()
                    .role(UserRole.WRITER)
                    .genres(new HashSet<>())
                    .location(belgrade.toBuilder().build())
                    .comments(new HashSet<>())
                    .transactions(new HashSet<>())
                    .verified(true)
                    .membershipApproved(true)
                    .workingPapers(new HashSet<>())
                    .books(new HashSet<>())
                    .username("perata")
                    .password(passwordEncoder.encode("Pera1997!"))
                    .name("Pero")
                    .surname("Peric")
                    .email("perata@maildrop.cc")
                    .genres(new HashSet<>())
                    .build();

            Writer writer2 = Writer.writerBuilder()
                    .role(UserRole.WRITER)
                    .genres(new HashSet<>())
                    .location(subotica.toBuilder().build())
                    .comments(new HashSet<>())
                    .transactions(new HashSet<>())
                    .verified(true)
                    .membershipApproved(true)
                    .workingPapers(new HashSet<>())
                    .books(new HashSet<>())
                    .username("lazata")
                    .password(passwordEncoder.encode("Laza1997!"))
                    .name("Lazo")
                    .surname("Lazic")
                    .email("lazata@maildrop.cc")
                    .genres(new HashSet<>())
                    .build();

            Writer writer3 = Writer.writerBuilder()
                    .role(UserRole.WRITER)
                    .genres(new HashSet<>())
                    .location(subotica.toBuilder().build())
                    .comments(new HashSet<>())
                    .transactions(new HashSet<>())
                    .verified(true)
                    .membershipApproved(true)
                    .workingPapers(new HashSet<>())
                    .books(new HashSet<>())
                    .username("radulata")
                    .password(passwordEncoder.encode("Radule1997!"))
                    .name("Radulo")
                    .surname("Radulic")
                    .email("radulo@maildrop.cc")
                    .genres(new HashSet<>())
                    .build();

            Writer writer4 = Writer.writerBuilder()
                    .role(UserRole.WRITER)
                    .genres(new HashSet<>())
                    .location(subotica.toBuilder().build())
                    .comments(new HashSet<>())
                    .transactions(new HashSet<>())
                    .verified(true)
                    .membershipApproved(true)
                    .workingPapers(new HashSet<>())
                    .books(new HashSet<>())
                    .username("gagatata")
                    .password(passwordEncoder.encode("Gago1997!"))
                    .name("Gago")
                    .surname("Gagic")
                    .email("gago@maildrop.cc")
                    .genres(new HashSet<>())
                    .build();

            Writer writer5 = Writer.writerBuilder()
                    .role(UserRole.WRITER)
                    .genres(new HashSet<>())
                    .location(subotica.toBuilder().build())
                    .comments(new HashSet<>())
                    .transactions(new HashSet<>())
                    .verified(true)
                    .membershipApproved(true)
                    .workingPapers(new HashSet<>())
                    .books(new HashSet<>())
                    .username("djolatata")
                    .password(passwordEncoder.encode("Djole1997!"))
                    .name("Djole")
                    .surname("Djolic")
                    .email("djole@maildrop.cc")
                    .genres(new HashSet<>())
                    .build();

            camundaUserService.createCamundaUser(writer1);
            camundaUserService.createCamundaUser(writer2);
            camundaUserService.createCamundaUser(writer3);
            camundaUserService.createCamundaUser(writer4);
            camundaUserService.createCamundaUser(writer5);

            Book b1 = Book.bookBuilder()
                    .file(String.format("%sUpravljanje digitalnim dokumentima.pdf", booksFolder))
                    .title("Upravljanje digitalnim dokumentima")
                    .synopsis("synopsis1")
                    .genre(g1)
                    .ISBN("0-3818-9816-4")
                    .keywords("burek,meso")
                    .publisher("FTN")
                    .publicationYear(2014)
                    .pages(240)
                    .publicationPlace("Novi Sad, Srbija")
                    .price(0.0)
                    .additionalAuthors("Branko Milosavljevic,Dragan Ivanovic")
                    .build();
            b1.setWriter(writer1);

            Book b2 = Book.bookBuilder()
                    .file(String.format("%sKrv vilenjaka.pdf", booksFolder))
                    .title("Krv vilenjaka")
                    .synopsis("synopsis1")
                    .genre(g2)
                    .ISBN("0-8823-8460-0")
                    .keywords("burek,sir")
                    .publisher("Carobna knjiga")
                    .publicationYear(2014)
                    .pages(317)
                    .price(16.0)
                    .publicationPlace("Beograd, Srbija")
                    .additionalAuthors("Andzej Sapkovski,Branko Milosavljevic")
                    .build();
            b2.setWriter(writer2);

            Book b3 = Book.bookBuilder()
                    .file(String.format("%sŽivotinjska farma.pdf", booksFolder))
                    .title("Životinjska farma")
                    .synopsis("Sinobsis")
                    .genre(g3)
                    .ISBN("0-6918-9816-4")
                    .keywords("burek,sir")
                    .publisher("FTN")
                    .publicationYear(2015)
                    .pages(690)
                    .publicationPlace("Novi Sad, Srbija")
                    .price(30.0)
                    .additionalAuthors("Zolata Zolka, Radata Rolka")
                    .build();
            b3.setWriter(writer3);

            Book book = Book.bookBuilder()
                    .ISBN("123412341234")
                    .publisher("Carobna kljiga")
                    .title("Stranac")
                    .publicationYear(1998)
                    .keywords("kljucne reci")
                    .pages(256)
                    .publicationPlace("mesto publikacije")
                    .genre(genreRepository.findByGenre(GenreEnum.COOKBOOKS))
                    .synopsis("Sinobsis")
                    .price(302.00)
                    .file(String.format("%sStranac.pdf", booksFolder))
                    .additionalAuthors("Gagata Gagic")
                    .build();
            book.setWriter(writer4);

            Book book2 = Book.bookBuilder()
                    .ISBN("653515341234")
                    .publisher("Simgidulum")
                    .title("Prokleta avlija")
                    .publicationYear(2005)
                    .keywords("kljucne reci")
                    .pages(256)
                    .publicationPlace("mesto publikacije")
                    .genre(genreRepository.findByGenre(GenreEnum.COOKBOOKS))
                    .synopsis("Sinobsis")
                    .price(203.00)
                    .file(String.format("%sProkleta avlija.pdf", booksFolder))
                    .additionalAuthors("Ivo Andric,Lazo Lazic")
                    .build();
            book2.setWriter(writer5);

            bookRepository.save(b1);
            bookRepository.save(b2);
            bookRepository.save(b3);
            bookRepository.save(book);
            bookRepository.save(book2);

            indexingUnitService.createBookIndexingUnit(b1);
            indexingUnitService.createBookIndexingUnit(b2);
            indexingUnitService.createBookIndexingUnit(b3);
            indexingUnitService.createBookIndexingUnit(book);
            indexingUnitService.createBookIndexingUnit(book2);

            plagiatorService.uploadNewPaper(b1.getTitle(), b1.getFile());
            plagiatorService.uploadNewPaper(b2.getTitle(), b2.getFile());
            plagiatorService.uploadNewPaper(b3.getTitle(), b3.getFile());
            plagiatorService.uploadNewPaper(book.getTitle(), book.getFile());
            plagiatorService.uploadNewPaper(book2.getTitle(), book2.getFile());

            InvoiceItem item = InvoiceItem.builder().name(b3.getTitle())
                    .price(b3.getPrice()).quantity(1).build();
            Set<InvoiceItem> items = new HashSet<>();
            items.add(item);

            Retailer r = Retailer.builder()
                    .name("Laguna")
                    .email("laguna@maildrop.cc")
                    .books(new HashSet<>(Arrays.asList(b1, b2, b3, book, book2)))
                    .build();

            retailerRepository.save(r);

            Invoice invoice = Invoice.builder().retailer(r).invoiceItems(items).build();

            Transaction transaction = Transaction.builder().completedOn(new Date()).createdOn(new Date())
                    .initializedOn(new Date()).done(true).paidWith("bank-service").total(b3.getPrice()).invoice(invoice)
                    .status(TransactionStatus.COMPLETED).build();
            invoice.setTransaction(transaction);

            reader1.getTransactions().add(transaction);

            baseUserRepository.save(reader1);
            baseUserRepository.save(reader2);
            baseUserRepository.save(reader3);
            baseUserRepository.save(reader4);
            camundaUserService.createCamundaUser(reader1);
            camundaUserService.createCamundaUser(reader2);
            camundaUserService.createCamundaUser(reader3);
            camundaUserService.createCamundaUser(reader4);
            indexingUnitService.createBetaReaderIndexingUnit(reader1);
            indexingUnitService.createBetaReaderIndexingUnit(reader2);
            indexingUnitService.createBetaReaderIndexingUnit(reader3);
            indexingUnitService.createBetaReaderIndexingUnit(reader4);
        }


        if (baseUserRepository.findAllByRole(UserRole.EDITOR).isEmpty()) {
            BaseUser editor = BaseUser.builder()
                    .role(UserRole.EDITOR)
                    .username("editor")
                    .password(passwordEncoder.encode("Editor1997!"))
                    .email("editor@maildrop.cc")
                    .name("Editor")
                    .surname("Editorovic")
                    .verified(true)
                    .location(subotica.toBuilder().build())
                    .build();

            BaseUser editor2 = BaseUser.builder()
                    .role(UserRole.EDITOR)
                    .username("mujoalen")
                    .password(passwordEncoder.encode("Editor1997!"))
                    .email("editor2@maildrop.cc")
                    .name("Mujo")
                    .surname("Alen")
                    .verified(true)
                    .location(subotica.toBuilder().build())
                    .build();

            BaseUser editor3 = BaseUser.builder()
                    .role(UserRole.EDITOR)
                    .username("jurica")
                    .password(passwordEncoder.encode("Editor1997!"))
                    .email("editor3@maildrop.cc")
                    .name("Jurica")
                    .surname("Juric")
                    .verified(true)
                    .location(subotica.toBuilder().build())
                    .build();

            BaseUser editor4 = BaseUser.builder()
                    .role(UserRole.EDITOR)
                    .username("peronikic")
                    .password(passwordEncoder.encode("Editor1997!"))
                    .email("editor4@maildrop.cc")
                    .name("Pero")
                    .surname("Nikic")
                    .verified(true)
                    .location(subotica.toBuilder().build())
                    .build();

            baseUserRepository.save(editor);
            baseUserRepository.save(editor2);
            baseUserRepository.save(editor3);
            baseUserRepository.save(editor4);
            camundaUserService.createCamundaUser(editor);
            camundaUserService.createCamundaUser(editor2);
            camundaUserService.createCamundaUser(editor3);
            camundaUserService.createCamundaUser(editor4);
        }

        if (baseUserRepository.findAllByRole(UserRole.LECTOR).isEmpty()) {
            BaseUser lector = BaseUser.builder()
                    .role(UserRole.LECTOR)
                    .username("lector")
                    .password(passwordEncoder.encode("Lector1997!"))
                    .email("lector@maildrop.cc")
                    .name("Lector")
                    .surname("Lectorovic")
                    .verified(true)
                    .location(subotica.toBuilder().build())
                    .build();

            baseUserRepository.save(lector);
            camundaUserService.createCamundaUser(lector);
        }
    }
}
