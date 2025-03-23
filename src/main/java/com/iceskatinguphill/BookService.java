package com.iceskatinguphill;

// CDI
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;
// Java utils
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;


@ApplicationScoped
@Transactional(SUPPORTS)
public class BookService {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    EntityManager em;

    public List<Book> getBooks() {
        logger.info("Getting all the books...");
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public Long countBooks(){
        TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(b) FROM Book b WHERE b.title LIKE :title", Long.class).setParameter("title", "%Java%");
        // Query to count books with a given word in their title
//        TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(b) FROM Book b WHERE b.title LIKE :title", Long.class).setParameter("title", "%Java%");
        long nbOfbooks = countQuery.getSingleResult();
        if (nbOfbooks == 0) {
            logger.info("No books in the database...");
        }
        return nbOfbooks;
    }

    @Transactional(REQUIRED)
    public Book create(Book book) {
        em.persist(book);
        logger.info("Persisted book: " + book.title);
        return book;
    }
}
