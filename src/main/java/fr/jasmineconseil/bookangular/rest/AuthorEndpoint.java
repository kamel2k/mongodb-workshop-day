package fr.jasmineconseil.bookangular.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
import org.jongo.Update;

import com.mongodb.DB;
import com.mongodb.WriteResult;

import fr.jasmineconseil.bookangular.model.Author;
import fr.jasmineconseil.bookangular.model.Book;

/**
 * 
 */
@Stateless
@Path("/authors")
public class AuthorEndpoint
{
	@Inject
	DB mongoDb;

	Jongo jongo;
	MongoCollection authors;

	@PostConstruct
	public void init() {
		jongo = new Jongo(mongoDb);
		authors = jongo.getCollection("authors");
	}

	@POST
	@Consumes("application/json")
	public Response create(Author entity) {
		
		authors.save(entity);
		return Response.created(UriBuilder.fromResource(BookEndpoint.class).path(entity.getId()).build()).build();
	}
	
	@DELETE
	@Path("/{id:\\w*}")
	public Response deleteById(@PathParam("id") String id) {
		WriteResult result = authors.remove(new ObjectId(id));
		
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
		Author entity = authors.findOne(new ObjectId(id)).as(Author.class);
		if (entity == null) 
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		
		System.out.println("AUTHOR ENTITY : " + entity.getBooks().size());
		for (Book books : entity.getBooks())
		{
			System.out.println(" -- Book ID: " + books.getId());
		}
		
		return Response.ok(entity).build();

		
	  /* 
      TypedQuery<Author> findByIdQuery = em.createQuery("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :entityId ORDER BY a.id", Author.class);
      findByIdQuery.setParameter("entityId", id);
      Author entity;
      try
      {
         entity = findByIdQuery.getSingleResult();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
      */
   }

   @GET
   @Produces("application/json")
   public List<Author> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
   {
		Find findAllQuery = authors.find();
		
		if (startPosition != null) 
		{
				findAllQuery.skip(startPosition);
		}		
		if (maxResult != null) 
		{
				findAllQuery.limit(maxResult);
		}
		
		MongoCursor<Author> all = findAllQuery.as(Author.class);
		List<Author> results = IteratorUtils.toList(all);

		return results;

   }

	@PUT
	@Path("/{id:\\w*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") String id, Author entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		authors.update(new ObjectId(id)).with(entity);
		
		return Response.noContent().build();
	}
}
