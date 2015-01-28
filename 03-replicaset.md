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

Créer les dossiers nécessaires pour la mise en place du **replicaset**
> mkdir replicaset

> cd replicaset

> mkdir mongo\node1 mongo\node2 mongo\node3 data\rs1 data\rs2 data\rs3 conf\node1 conf\node2 conf\node3 logs pids






## Références
[http://docs.mongodb.org/manual/core/replication-introduction/](http://docs.mongodb.org/manual/core/replication-introduction/)