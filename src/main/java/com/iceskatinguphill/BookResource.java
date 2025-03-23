package com.iceskatinguphill;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
}
