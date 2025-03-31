package com.iceskatinguphill;

import io.vertx.core.cli.annotations.Summary;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

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
    @Operation(summary = "Returns all the books from the database")
    @APIResponse(responseCode = "200",
            description = "Books found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Book.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No books found")
    public Response getAllBooks() {
        List<Book> books = bookService.getBooks();
        if (books == null)
            return Response.noContent().build();
        return Response.ok(Book.class, MediaType.APPLICATION_JSON).entity(books).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieves a single book from the database using an ID - if it exists")
    @APIResponse(responseCode = "200",
    description = "Book found via its ID",
    content = @Content(mediaType = MediaType.APPLICATION_JSON,
    schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "404",
            description = "No book found with the given ID")
    public Response getBook(@Parameter(description = "The id of the book to look for.") @PathParam("id") @Min(1) long id) {
        Book book = bookService.getBook(id);
        return Response.ok(Book.class, MediaType.APPLICATION_JSON).entity(book).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Returns the count of all books in the database")
    @APIResponse(responseCode = "200", description = "Books found in the database",
    content = @Content(mediaType = MediaType.TEXT_PLAIN, schema = @Schema(implementation = Long.class)))
    public Response countBooks() {
        Long nbOfBooks = bookService.countBooks();
        if (nbOfBooks == 0) {
            return Response.noContent().build();
        }
        return Response.ok(nbOfBooks).build();
    }

    @POST
    @Operation(summary = "Creates a valid book in the database")
    @APIResponse(responseCode = "201", description = "Book created",
    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Book.class)))

    public Response createBook(Book book) throws URISyntaxException {
        logger.info("Creating a new book...");
        book = bookService.create(book);
        // Reminder: if I don't build the <entity>, I have no body of the response.
        return Response.created(URI.create("/" + book.id)).entity(book).build();
    }
}
