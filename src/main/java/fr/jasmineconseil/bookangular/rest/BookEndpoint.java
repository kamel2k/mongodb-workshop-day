package fr.jasmineconseil.bookangular.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.collections4.IteratorUtils;
import org.bson.types.ObjectId;
import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import com.mongodb.DB;
import com.mongodb.WriteResult;

import fr.jasmineconseil.bookangular.model.Book;

/**
 * 
 */
@Stateless
@Path("/books")
public class BookEndpoint {

	@Inject
	DB mongoDb;

	Jongo jongo;
	MongoCollection books;
	MongoCollection authors;

	@PostConstruct
	public void init() {
		jongo = new Jongo(mongoDb);
		books = jongo.getCollection("books");
		authors = jongo.getCollection("authors");
	}

	@POST
	@Consumes("application/json")
	public Response create(Book entity) {
		
		books.save(entity);
		return Response.created(UriBuilder.fromResource(BookEndpoint.class).path(entity.getId()).build()).build();
	}

	@DELETE
	@Path("/{id:\\w*}")
	public Response deleteById(@PathParam("id") String id) {
		WriteResult result = books.remove(new ObjectId(id));
		
		String with = " { $pull: { books: {'_id' : '#' } } } ";
		
		System.out.println("with : " + with);
		WriteResult resultat = authors.update("{}").multi().with(with, new ObjectId(id));
		System.out.println("Nombre d'elements : " + resultat.getN());
		
		
		if (null == result || result.getN() == 0)
		{
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.noContent().build();
	}

	@GET
	@Path("/{id:\\w*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") String id) 
	{
		Book entity = books.findOne(new ObjectId(id)).as(Book.class);
		if (entity == null) 
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Book> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) 
	{
		Find findAllQuery = books.find();
			
		if (startPosition != null) 
		{
				findAllQuery.skip(startPosition);
		}		
		if (maxResult != null) 
		{
				findAllQuery.limit(maxResult);
		}
		
		MongoCursor<Book> all = findAllQuery.as(Book.class);
		List<Book> results = IteratorUtils.toList(all);

		return results;
	}

	@PUT
	@Path("/{id:\\w*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") String id, Book entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		books.update(new ObjectId(id)).with(entity);

		return Response.noContent().build();
	}
}
