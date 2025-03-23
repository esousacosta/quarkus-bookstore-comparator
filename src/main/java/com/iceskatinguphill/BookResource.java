package com.iceskatinguphill;

import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.slf4j.ILoggerFactory;

// Java SE
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/books")
public class BookResource {

    @GET
    public Response getAllBooks() {
        List<Book> books = new ArrayList<>();
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
        int nbOfBooks = 0;
        if (nbOfBooks == 0) {
            return Response.noContent().build();
        }
        return Response.ok(nbOfBooks).build();
    }
}
