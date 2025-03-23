package com.iceskatinguphill;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// Java SE
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/books")
public class BookResource {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    BookService bookService;

    @GET
    public Response getAllBooks() {
        List<Book> books = bookService.getBooks();
        if (books == null)
            return Response.noContent().build();
        return Response.ok(Book.class, MediaType.APPLICATION_JSON).entity(books).build();
    }

    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") @Min(1) long id) {
        Book book = new Book();
        return Response.ok(Book.class, MediaType.APPLICATION_JSON).entity(book).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countBooks() {
        Long nbOfBooks = bookService.countBooks();
        if (nbOfBooks == 0) {
            return Response.noContent().build();
        }
        return Response.ok(nbOfBooks).build();
    }

    @POST
    public Response createBook(Book book) throws URISyntaxException {
        logger.info("Creating a new book...");
        book = bookService.create(book);
        // Reminder: if I don't build the <entity>, I have no body of the response.
        return Response.created(URI.create("/" + book.id)).entity(book).build();
    }
}
