# JEE6 & Jongo Wrapper

## Présentation de jongo

[jongo](http://www.jongo.org) est un framework opensource qui permet de requêter sur une base mongoDB à la façon shell et convertir le résultat en objets Java (API Jackson par défaut).

Exemple :

- SHELL

```
db.friends.find({age: {$gt: 18}})
```

- JAVA DRIVER

```
friends.find(new BasicDBObject("age",new BasicDBObject("$gt",18)))
```

- JONGO

```
friends.find("{age: {$gt: 18}}").as(Friend.class)
```


## Quelques indications pour faire la migration de l'application book-angular vers une base MongoDB

- Dépendances à rajouter dans le pom.xml

```
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-collections4</artifactId>
			<version>4.0</version>
		</dependency>
		<dependency>
			<groupId>org.jongo</groupId>
			<artifactId>jongo</artifactId>
			<version>1.1</version>
		</dependency>
		<dependency>
			<groupId>org.mongodb</groupId>
			<artifactId>mongo-java-driver</artifactId>
			<version>2.12.5</version>
		</dependency>
```

- Une classe permettant de générer une instance de la base MongoDB

```
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
 
	private MongoClientURI mongoClientURI = new MongoClientURI("mongodb://localhost/bookangular");
	
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
```

- Les entitées du package model doivent déclarer l'id unique graçe à ce code java

```
	@Id @ObjectId 
	private String id;
```

- Bloc de code à intégrer dans la classe BookEndpoint.java

```
	@Inject
	DB mongoDb;

	Jongo jongo;
	MongoCollection books;

	@PostConstruct
	public void init() {
		jongo = new Jongo(mongoDb);
		books = jongo.getCollection("books");
	}
```

- Bloc de code à intégrer dans la classe AuthorEndpoint.java

```
	@Inject
	DB mongoDb;

	Jongo jongo;
	MongoCollection authors;

	@PostConstruct
	public void init() {
		jongo = new Jongo(mongoDb);
		authors = jongo.getCollection("authors");
	}
```
    