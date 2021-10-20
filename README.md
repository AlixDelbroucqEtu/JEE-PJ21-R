Projet JEE-PJ21-R
Genart Valentin
Delbroucq Alix
Pires Theo
Gomez Mickael
19/10/2021

# Introduction
L'objectif de ce projet et de développer la partie promotion du commerce en ligne "Web Drive".

# Guide Installation

* Placer ce repertoire dans le dossier webapps de votre apache.
* Ajouter/Modifier le context du fichier conf/server.xml de votre serveur apache comme suis dans la balise host. 
```
 <Host name="localhost"  appBase="webapps"
            unpackWARs="true" autoDeploy="true">

        <!-- SingleSignOn valve, share authentication between web applications
             Documentation at: /docs/config/valve.html -->
        <!--
        <Valve className="org.apache.catalina.authenticator.SingleSignOn" />
        -->

        <!-- Access log processes all example.
             Documentation at: /docs/config/valve.html
             Note: The pattern used is equivalent to using pattern="common" -->
          <Context  
          docBase="votre_chemin_perso/webapps/JEE-PJ21-R/promos/target/jeepj21r-1.0-SNAPSHOT.war"
          path="jeepj21r"
          reloadable="true"
          backgroundProcessorDelay="3"
        />
        <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
               prefix="localhost_access_log" suffix=".txt"
               pattern="%h %l %u %t &quot;%r&quot; %s %b" />

      </Host>
```

En remplacent "votre_chemin_perso" avec le chemin absolu vers votre serveur apache.

Dans le repertoire promo executez les commandes suivantes : 
* mvn clean
* mvn install
* mvn war:exploded

# Description

# Objectifs

En tant que client je ne peux ajouter plusieurs fois le même article, ce qui revient à spécifier une quantité d'articles dans le panier.

Actuellement le modèle de données ne permet par de préciser une quantité d'articles au panier, modifiez le modèle.
Puis vous modifierez si besoin l'interface pour pouvoir ajouter plusieurs articles au panier.
Dans le cadre de cette fonction, vous modifierez également l'affichage des listes de produits afin de voir rapidement les produits qui se trouvent déjà dans mon panier. Les articles déjà dans mon panier doivent être identifiables facilement.
En tant que qu'administrateur je peux ajouter une promotion afin de dynamiser les ventes d'un produit.

Une page d'admin me permet de saisir une promotion.
Je viens renseigner une référence, le système recherche le produit correspondant, et si il existe.
Je viens préciser une date de début de promo, une date de fin, je renseigne un pourcentage de réduction OU une valeur de réduction.
Modifiez les listes de produit afin de visualiser la promotion. Vous ferez apparaitre le prix d'origine (prix barré), et le prix en cours.
La promo ne s'affiche que si elle est dans sa période de validité (entre les dates de début et de fin).
En tant qu'administrateur je peux gérer une promotion sur un ensemble de produits facilement.

En extension de l'usage précédant, il doit être facile, pour un admin d'appliquer une promotion à un ensemble de produits selon des critères :
la catégorie du produit, la marque du produit (que vous ajouterez au modèle), les produits proches de la date de péremption.
Cette application de promo ne sera pas dynamique, les produits ajoutés (ou modifiés) qui rempliraient ces conditions ne seront pas impactés par la promotion choisie par l'administrateur.
En tant qu'administrateur je peux définir une opération marketing simple sur l'ensemble du panier.

Comme pour les promotions, ces opérations ne s'appliquent qu'entre deux dates.
Les opérations marketing simple peuvent être limitées en nombre (ex: uniquement pour les 100 premiers clients validant leur panier).
Elles peuvent nécessiter la saisie d'un code promo qui serait par exemple diffusé à la télé ou dans un prospectus (ex: PROMO25).
Dans tous les cas, ces opérations marketing simples ne seront applicables à un client qu'une seule fois.
La réduction du panier se fait en pourcentage ou en valeur, mais le montant du panier ne pourra pas avoir une valeur inférieure à 0 !
Vous ferez une page d'administration permettant de lister, ajouter, modifier et supprimer ces opérations simples.
En tant qu'administrateur je peux définir une opération marketing sous condition d'achat de plusieurs produits.

Ce type d'opération ne sera valable que sur un groupe de produits sélectionnés par l'administrateur.
Encore une fois entre deux dates définies.
Il existera 3 types d'offres avancées :
- l'opération "X + 1 gratuit" où X sera paramétrable, le produit offert sera le moins cher.
- le 2e à X%, le moins cher des deux.
- le lot de X à Y Euros.
Ces opérations sont ordonnées, on viendra appliquer les promos selon leur ordre.
Dans cet ordre, les produits d'un panier ayant bénéficié d'une opération ne sont pas candidats pour déclencher une autre opération sur le panier.
Modifier le panier pour faire apparaitre ces opérations marketing.
Le montant du panier avant et après toutes les réductions doit être affiché.

# Réalisation
