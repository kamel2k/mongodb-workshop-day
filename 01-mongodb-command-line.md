# MongoDB en ligne de commande

Cette rubrique va vous permettre de vous familiariser avec les commandes de base de mongo shell.

## Préparation de la base MongoDB

- Décompresser l'archive mongodb-win32-x86_64-2008plus-2.6.7.zip dans le dossier d:\atelier\standalone

- Créer le dossier d:\atelier\standalone\data

- Lancer MongoDB avec la commande

```
cd d:\atelier\standalone\bin
mongod.exe --dbpath d:\atelier\standalone\data
```

## Lacement du shell client mongo

- Lancer le client mongo à l'aide de la commande

```
cd d:\atelier\standalone\bin
mongo.exe
```

## Lister les bases de données

- Pour lister les bases mongo 

```
> show dbs
admin  (empty)
local  0.078GB
```

- Créer une nouvelle base avec le nom bookangular

```
> use bookangular
switched to db bookangular
```

> Lorsque la base n'est pas présente, mongo génère une nouvelle

## Lister les collections

- Pour lister les collections 

```
> show collections
```

> (vide) : pas de collections. Lorsque vous insérez des données dans une collection inéxistante, cette dernière va se créee automatiquement.

## Insertion des données dans les collections "books" et "authors"

- Voici les attributs de l'entité "BOOK"

>\_id   : ObjectId

>isbn  : chaine

>title : chaine

>pages : numérique
   
- Voici les attributs de l'entité "AUTHOR"

>\_id   : ObjectId

>name  : chaine;
   
- Pour ajouter un document dans une collection :

```
> db.books.insert( {"isbn":"9782212120615", "title":"Developpement JEE avec Eclipse Europa", "pages": 379} );
```

> La collection books va être créer automatiquement
> L'identifiant \_id lorsqu'il n'est pas défini dans la requête, il sera créer automatiquement
> L'\_id doit être unique
> Ajouter d'autres livres dans la base.

## Recherche dans les données 

- Pour lister les documents dans la collection "books"

```
> db.books.find();
```

> Lancer db.books.find().pretty() pour avoir une meilleure lisibilié

- Pour récupérer un seul document 

```
> db.books.findOne();
```

- Pour déterminer le nombre d'élements dans la collection "books"

```
> db.books.count();
```

- Pour retrouver le document qui à un isbn = 9782212120615

```
> db.books.find( {"isbn":"9782212120615"} );
```

- Pour retrouver le nombre de résultat de la reqûete 

```
> db.books.find( {"isbn":"9782212120615"} ).count();
```

- Pagination

```
> db.books.find().limit(5).skip(2);
```


> Refaire les mêmes opérations avec la collection "authors"

## Mise à jour d'un document

- Corriger le titre du document avec l'isbn 9782212120615 en remplaçant JEE par JEE5

```
> db.books.update( {"isbn":"9782212120615"}, { $set : {"title":"Developpement JEE5 avec Eclipse Europa"} } );
```

## Suppression d'un document

- Supprimer le document avec ObjectId("54cac9e984f6739f1def12a3")

```
> db.books.remove({ "_id" : ObjectId("54cac9e984f6739f1def12a3")})
```

## Questions

- Trouver tous les documents avec le nombre de pages qui dépasse 300 ?

> Indication : utiliser l'opérateur $gt (Greater than) pour retrouver les documents

> Voici quelques opérateurs à utiliser dans les requêtes : $gt, $ne, $gte, $exists, $lt, $lte
