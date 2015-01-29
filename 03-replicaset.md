# Replicaset

Cette étape de l'atelier permet de montrer la puissance des replicaset MongoDB. 

## Objectifs 

La réplication est le processus de synchronisation des données sur plusieurs serveurs. L'objectif de la replication est d'augmenter la disponibilité des données avec plusieurs copies de la base sur des serveurs différents.

## Replicaset dans MongoDB

Un **replicaset** est un groupe de **mongod** qui hébèrge le même ensemble de données. Dans note atelier le primère reçoit l'ensemble des opérations de lecture et écriture, ensuite les même opérations seront effectuées sur les autres noeuds du **replicaset** grace au **journal d'opérations**

![](images/replica-set-read-write-operations-primary.png)

Les noeuds secondaires représentent une copie intégrale du primaire. Lorsque le primaire n'est pas disponible, le ** replicaset** élira un nouveau primaire graçe au mécanisme d'élection des noeuds disponibles.

Dans notre exemple, nous allons définir 1 primaire, 1 secondaire et 1 arbiter. Ce dernier ne comportera pas de données et participera au vote dans le cas d'indisponibilité du primaire.

![](images/replica-set-primary-with-secondary-and-arbiter.png)

## Préparation des dossiers nécessaires

Se positionner au niveau du dossier d:\atelier

- Créer les dossiers nécessaires pour la mise en place du **replicaset**

> mkdir replicaset

> cd replicaset

> mkdir mongo\node1 mongo\node2 mongo\node3 data\rs1 data\rs2 data\rs3 conf\node1 conf\node2 conf\node3 logs pids

- Décompresser les binaires de mongo dans 3 dossiers différents (mongo\node1, mongo\node2, mongo\node3)

> nom de l'archive : mongodb-win32-x86_64-2008plus-2.6.7.zip

- Créer 3 fichiers de configuration avec le nom mongod.conf dans les 3 dossiers (conf\node1, conf\node2, conf\node3)

Exemple de fichier de configuration pour le noeud 1

>\# mongo.conf

>\#where to log

>logpath=d:\atelier\replicaset\logs\mongod-node1.log

>logappend=true

>\# fork and run in background (seulement pour les utilisateurs linux)

>fork = true

>port = 27017

>dbpath=d:\atelier\replicaset\data\rs1

>\# location of pidfile

>pidfilepath=d:\atelier\replicaset\pids\mongod-node1.pid

>\# authentification

>auth = false

- port 27017 pour le primaire
- port 27018 pour le secondaire
- port 27019 pour l'arbiter


primaire | localhost | 27017

secondaire | localhost | 27018

arbiter | localhost | 27019

 Lancement des processus mongod

./mongod –-replSet uploadsel –-port 27017 --smallfiles –f 

/app/outils/services/mongodb/mongodb1/conf/mongod.conf

./mongod –-replSet uploadsel –-port 27018 -–smallfiles –f 

/app/outils/services/mongodb/mongodb2/conf/mongod.conf

./mongod –-replSet uploadsel –-port 27019 --smallfiles –f 

/app/outils/services/mongodb/mongodb3/conf/mongod.conf 

 Se connecter au shell mongo

./mongo --port 27017

 Création du replica Set.

config = { _id: "uploadsel", members:[

          { _id : 0, host : "localhost:27017"},

          { _id : 1, host : "localhost:27018"} ]

};

rs.initiate(config);

Résultat:

Première commande:

{

        "_id" : "uploadsel",

        "members" : [

                {

                        "_id" : 0,

                        "host" : "localhost:27017"

                },

                {

                        "_id" : 1,

                        "host" : "localhost:27018"

                }

        ]

}

Deuxième commande : rs.initiate(config);

{

        "info" : "Config now saved locally.  Should come online in about a minute.",

        "ok" : 1

}

 Ajout de l’arbiter

rs.addArb("localhost:27019");

Résultat:

 Vérification du status du replica Set

rs.status()

Résultat:

{

        "set" : "uploadsel",

        "date" : ISODate("2013-11-25T14:26:12Z"),

        "myState" : 1,

        "members" : [

                {

                        "_id" : 0,

                        "name" : "localhost:27017",

                        "health" : 1,

                        "state" : 1,

                        "stateStr" : "PRIMARY",

                        "uptime" : 380,

                        "optime" : Timestamp(1385389519, 1),

                        "optimeDate" : ISODate("2013-11-25T14:25:19Z"),

                        "self" : true

                },

                {

                        "_id" : 1,

                        "name" : "localhost:27018",

                        "health" : 1,

                        "state" : 2,

                        "stateStr" : "SECONDARY",

                        "uptime" : 210,

                        "optime" : Timestamp(1385389519, 1),

                        "optimeDate" : ISODate("2013-11-25T14:25:19Z"),

                        "lastHeartbeat" : ISODate("2013-11-25T14:26:12Z"),

                        "lastHeartbeatRecv" : ISODate("2013-11-25T14:26:11Z"),

                        "pingMs" : 0,

                        "syncingTo" : "localhost:27017"

                },

                {

                        "_id" : 2,

                        "name" : "localhost:27019",

                        "health" : 1,

                        "state" : 7,

                        "stateStr" : "ARBITER",

                        "uptime" : 53,

                        "lastHeartbeat" : ISODate("2013-11-25T14:26:11Z"),

                        "lastHeartbeatRecv" : ISODate("2013-11-25T14:26:11Z"),

                        "pingMs" : 0

                }

        ],

        "ok" : 1

}





## Références
[http://docs.mongodb.org/manual/core/replication-introduction/](http://docs.mongodb.org/manual/core/replication-introduction/)