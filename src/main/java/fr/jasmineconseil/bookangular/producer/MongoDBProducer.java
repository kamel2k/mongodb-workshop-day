package fr.jasmineconseil.bookangular.producer;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@ApplicationScoped
public class MongoDBProducer {
 
	private MongoClientURI mongoClientURI = new MongoClientURI("mongodb://localhost/test");
	
	private DB db;
 
	@PostConstruct
	public void init() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(mongoClientURI);
		db =  mongoClient.getDB(mongoClientURI.getDatabase());
	}
 
	@Produces
	public DB createDB() {
		return db;
	}
 
}