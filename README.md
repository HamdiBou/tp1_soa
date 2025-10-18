# TP1 - Web Services REST avec Spring Boot

## 📋 Description du Projet

Ce projet implémente un service web REST pour la gestion de restaurants et de menus, développé dans le cadre du TP1 Web Services REST. Il utilise Spring Boot avec une architecture hexagonale et inclut trois parties principales :

- **Partie A** : Génération automatique de code à partir d'une spécification OpenAPI
- **Partie B** : Création d'un RestaurantController avec architecture hexagonale et base de données H2
- **Partie C** : Client Angular pour consommer les APIs REST avec interface utilisateur moderne

## 🏗️ Architecture

Le projet suit une **architecture hexagonale** (Ports & Adapters) :

```
src/com/speed_liv/menu/
    ├── controllers/              ← Couche API (Interface REST)
    │   └── RestaurantController.java
    │
    ├── services/                 ← Couche Application (Logique métier)
    │   └── RestaurantService.java
    │
    ├── model/
    │   ├── entity/               ← Entités du domaine
    │   │   ├── Restaurant.java
    │   │   └── Plat.java
    │   │
    │   └── repository/           ← Port (Interface du domaine)
    │       └── RestaurantRepository.java
    │
    ├── infrastructure/           ← Couche Infrastructure (Adapters)
    │   └── persistance/
    │       ├── JpaRestaurantRepository.java       ← Adapter JPA
    │       ├── RestaurantRepositoryAdapter.java   ← Bridge Pattern
    │       └── RestaurantDataLoader.java          ← Chargement JSON
    │
    └── config/
        └── JpaConfig.java        ← Configuration JPA
```

## 🚀 Technologies Utilisées

### Backend
- **Java 17** - Langage de programmation
- **Spring Boot 2.7.15** - Framework web
- **Spring Data JPA** - Couche de persistance
- **H2 Database** - Base de données en mémoire
- **Maven 3.9.5** - Gestion des dépendances
- **OpenAPI Generator** - Génération de code REST
- **Swagger UI / SpringDoc** - Documentation API interactive

### Frontend
- **Angular 19.1.0** - Framework frontend moderne
- **TypeScript 5.7.2** - Langage typé pour JavaScript
- **RxJS 7.8.0** - Programmation réactive
- **Node.js 20.x** - Runtime JavaScript

### Infrastructure
- **Docker** - Conteneurisation de l'environnement de développement
- **Docker Compose** - Orchestration des conteneurs

## 📦 Installation et Démarrage

### Prérequis

- Docker et Docker Compose installés
- Ports 8088 (Backend) et 4200 (Frontend) disponibles sur votre machine

### Démarrage avec Docker

1. **Lancer le conteneur Docker :**
   ```bash
   docker-compose up -d dev-environment
   ```

2. **Compiler le projet Backend :**
   ```bash
   docker exec soa_tp_dev mvn clean install -f /workspace/menu_java/pom.xml
   ```

3. **Démarrer l'application Backend :**
   ```bash
   docker exec soa_tp_dev bash -c "cd /workspace/menu_java && mvn spring-boot:run"
   ```

4. **Démarrer l'application Frontend Angular :**
   ```bash
   docker exec soa_tp_dev bash -c "cd /workspace/speedliv-client && ng serve --host 0.0.0.0 --port 4200"
   ```

5. **Accéder aux applications :**
   - **Frontend Angular** : http://localhost:4200
   - **API Backend** : http://localhost:8088
   - **Swagger UI** : http://localhost:8088/swagger-ui.html
   - **H2 Console** : http://localhost:8088/h2-console

### Configuration H2 Console

Pour accéder à la console H2 :
- **JDBC URL** : `jdbc:h2:mem:restaurantdb`
- **Username** : `sa`
- **Password** : (laisser vide)

## 📚 API Endpoints

### Restaurants

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/restaurants` | Liste tous les restaurants avec leurs plats |
| `GET` | `/restaurants/{id}` | Obtenir un restaurant spécifique |
| `POST` | `/restaurants` | Créer un nouveau restaurant |
| `DELETE` | `/restaurants/{id}` | Supprimer un restaurant |

### Menus (Partie A - OpenAPI Generator)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/menus/du-jour` | Obtenir le menu du jour |
| `GET` | `/plats/{id}` | Obtenir un plat par son ID |
| `GET` | `/plats` | Obtenir tous les plats |

## 🧪 Exemples d'utilisation

### 1. Obtenir tous les restaurants

```bash
curl http://localhost:8088/restaurants
```

**Réponse :**
```json
[
  {
    "id": 1,
    "name": "resto1",
    "plats": [
      {
        "id": 1,
        "name": "Pizza Margherita",
        "price": 8.5,
        "disponible": null
      },
      {
        "id": 2,
        "name": "Lasagnes",
        "price": 10.0,
        "disponible": null
      }
    ]
  },
  {
    "id": 2,
    "name": "resto2",
    "plats": [
      {
        "id": 3,
        "name": "Sushi Saumon",
        "price": 12.0,
        "disponible": null
      },
      {
        "id": 4,
        "name": "Ramen",
        "price": 11.0,
        "disponible": null
      }
    ]
  }
]
```

### 2. Obtenir un restaurant par ID

```bash
curl http://localhost:8088/restaurants/1
```

### 3. Créer un nouveau restaurant

```bash
curl -X POST http://localhost:8088/restaurants \
  -H "Content-Type: application/json" \
  -d '{
    "name": "resto3",
    "plats": [
      {
        "name": "Burger",
        "price": 9.5,
        "disponible": true
      }
    ]
  }'
```

### 4. Supprimer un restaurant

```bash
curl -X DELETE http://localhost:8088/restaurants/1
```

## 📊 Modèle de Données

### Restaurant
```java
{
  "id": Long,
  "name": String,
  "plats": List<Plat>
}
```

### Plat
```java
{
  "id": Long,
  "name": String,
  "price": Double,
  "disponible": Boolean,
  "restaurant": Restaurant
}
```

## 🔧 Configuration

### application.properties

```properties
# Server Configuration
server.port=8088

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:restaurantdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=false

# Jackson Configuration
spring.jackson.date-format=org.openapitools.RFC3339DateFormat
spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS=false
```

## 📝 Partie A : OpenAPI Generator

### Génération du code

Le code de la Partie A a été généré à partir du fichier OpenAPI `openapi-menu-v1.yml` :

```bash
docker exec soa_tp_dev openapi-generator generate \
  -i /workspace/openapi-menu-v1.yml \
  -g spring \
  -o /workspace/menu_java \
  --package-name com.speed_liv.menu \
  --api-package com.speed_liv.menu.api \
  --model-package com.speed_liv.menu.model
```

### Fichier OpenAPI

Le fichier `openapi-menu-v1.yml` définit :
- Les endpoints pour gérer les menus et les plats
- Les modèles de données
- Les réponses HTTP attendues

## 🔄 Partie B : Architecture Hexagonale

### Avantages de l'Architecture Hexagonale

1. **Séparation des préoccupations** : Chaque couche a une responsabilité claire
2. **Testabilité** : Les composants peuvent être testés indépendamment
3. **Maintenabilité** : Facilite les modifications et l'évolution du code
4. **Flexibilité** : Facile de changer d'implémentation (ex: passer de JSON à H2)

### Passage JSON → H2

**Observations :**

| Aspect | Fichier JSON | Base H2 |
|--------|--------------|---------|
| **Performance** | Lecture disque à chaque requête | Données en mémoire, accès rapide |
| **Requêtes** | Parsing JSON manuel | SQL généré automatiquement par Hibernate |
| **Relations** | Gestion manuelle | Relations JPA (`@OneToMany`, `@ManyToOne`) |
| **Persistence** | Lecture seule | CRUD complet (Create, Read, Update, Delete) |
| **Console** | Pas de console | Console H2 pour visualiser les données |
| **Transactions** | Non supportées | Transactions ACID |

## 🎨 Partie C : Client Angular

### Description
Application web Angular qui consomme les APIs REST du backend Spring Boot pour permettre aux clients de:
1. ✅ Voir la liste des restaurants disponibles
2. 🍕 Consulter le menu (plats) de chaque restaurant
3. 🛒 Ajouter des plats au panier avec gestion des quantités
4. ✔️ Valider leur commande avec un récapitulatif

### Architecture Frontend
```
speedliv-client/
├── src/app/
│   ├── models/
│   │   └── restaurant.model.ts       # Interfaces TypeScript
│   ├── services/
│   │   ├── restaurant.service.ts     # Consommation API REST
│   │   └── cart.service.ts           # Gestion du panier (BehaviorSubject)
│   ├── app.component.ts              # Composant principal
│   ├── app.component.html            # Template avec binding
│   └── app.component.css             # Styles avec thème moderne
└── package.json
```

### Fonctionnalités du Client
- **Liste des Restaurants** : Affichage en grille responsive avec compteur de plats
- **Menu Détaillé** : Visualisation des plats avec prix et disponibilité
- **Panier Intelligent** : Gestion des quantités, calcul automatique du total
- **Validation de Commande** : Modal de confirmation avec résumé détaillé
- **États de Chargement** : Spinner animé pendant les requêtes API
- **Gestion d'Erreurs** : Messages d'erreur avec bouton "Réessayer"

### Design System

**Palette de Couleurs :**
- **Background Principal** : Dégradé bleu foncé vers violet (`#1e3c72` → `#2a5298` → `#7e22ce`)
- **Purple Primaire** : Violet riche `#7e22ce` avec accents clairs `#a855f7`
- **Texte** : Bleu foncé `#1e3c72` pour les titres, gris ardoise `#475569` pour le corps
- **Cartes** : Dégradés blanc/bleu avec ombres violettes

**Effets Visuels :**
- Dégradés de couleur sur tous les éléments interactifs
- Ombres portées avec teintes de couleur
- Effet de flou d'arrière-plan (backdrop-filter)
- Transitions fluides sur hover avec animations de scale
- Bordures subtiles avec teintes violettes

**Responsive Design :**
- **Desktop** : Grille multi-colonnes pour restaurants et plats
- **Tablet** : Grille 2 colonnes adaptative
- **Mobile** : Colonne unique avec espacement optimisé

### Communication API

Le client Angular communique avec le backend Spring Boot via HttpClient :

```typescript
// GET /restaurants - Récupère tous les restaurants
getAllRestaurants(): Observable<Restaurant[]>

// GET /restaurants/{id} - Récupère un restaurant spécifique
getRestaurantById(id: number): Observable<Restaurant>
```

**CORS Configuration** : Le backend autorise les requêtes depuis `http://localhost:4200`

### State Management

Utilisation de **RxJS BehaviorSubject** pour la gestion réactive du panier :
- Ajout/Suppression d'articles
- Mise à jour des quantités
- Calcul automatique du total
- Réinitialisation après validation

### Redémarrage du Serveur Angular

Si le hot reload s'arrête :

```bash
# Arrêter les processus Angular existants
docker exec soa_tp_dev bash -c "pkill -9 -f 'ng serve'"

# Redémarrer le serveur de développement
docker exec soa_tp_dev bash -c "cd /workspace/speedliv-client && ng serve --host 0.0.0.0 --port 4200"
```

Attendre 20-30 secondes pour la compilation, puis actualiser http://localhost:4200

## 🐳 Docker

### Structure du Dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk
# Installation de Maven 3.9.5
# Installation d'OpenAPI Generator CLI 7.7.0
# Configuration de l'environnement de développement
```

### docker-compose.yml

```yaml
services:
  dev-environment:
    build: .
    container_name: soa_tp_dev
    ports:
      - "8088:8088"
    volumes:
      - ./workspace:/workspace
```

## 📖 Documentation API

La documentation interactive de l'API est disponible via Swagger UI :

**URL** : http://localhost:8088/swagger-ui.html

Cette interface permet de :
- Visualiser tous les endpoints disponibles
- Tester les endpoints directement depuis le navigateur
- Voir les modèles de données
- Consulter les codes de réponse HTTP

## 🧪 Tests

Pour tester l'application :

1. **Vérifier que l'application démarre :**
   ```bash
   curl http://localhost:8088/actuator/health
   ```

2. **Tester l'endpoint restaurants :**
   ```bash
   curl http://localhost:8088/restaurants
   ```

3. **Accéder à Swagger UI :**
   Ouvrir http://localhost:8088/swagger-ui.html dans un navigateur

## 📁 Structure des Fichiers

```
workspace/
├── menu_java/                      # Projet Spring Boot généré
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/speed_liv/menu/
│   │   │   │   │   ├── controllers/
│   │   │   │   │   ├── services/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── entity/
│   │   │   │   │   │   └── repository/
│   │   │   │   │   ├── infrastructure/
│   │   │   │   │   │   └── persistance/
│   │   │   │   │   └── config/
│   │   │   │   └── org/openapitools/
│   │   │   └── resources/
│   │   │       ├── bd/
│   │   │       │   └── restaurants.json
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
├── speedliv-client/                # Client Angular
│   ├── src/
│   │   ├── app/
│   │   │   ├── models/
│   │   │   │   └── restaurant.model.ts
│   │   │   ├── services/
│   │   │   │   ├── restaurant.service.ts
│   │   │   │   └── cart.service.ts
│   │   │   ├── app.component.ts
│   │   │   ├── app.component.html
│   │   │   ├── app.component.css
│   │   │   └── app.config.ts
│   │   └── main.ts
│   ├── package.json
│   ├── angular.json
│   ├── README.md
│   └── README_CLIENT.md
├── openapi-menu-v1.yml            # Spécification OpenAPI
└── README.md                      # Ce fichier
```

## 🤝 Contribution

Ce projet a été développé dans le cadre du TP1 Web Services REST.

## 📄 Licence

Projet académique - IIT G3

## 📞 Support

Pour toute question ou problème :
1. Consulter la documentation Swagger : http://localhost:8088/swagger-ui.html
2. Vérifier les logs de l'application : `docker logs soa_tp_dev`
3. Accéder à la console H2 : http://localhost:8088/h2-console
4. Tester le client Angular : http://localhost:4200
5. Consulter le README détaillé du client : `workspace/speedliv-client/README_CLIENT.md`

## 🚀 Flux de Travail Complet

1. **Backend** : Spring Boot → JPA → H2 Database
2. **Frontend** : Angular → HttpClient → REST API
3. **Communication** : CORS activé pour localhost:4200
4. **Données** : JSON chargé au démarrage dans H2
5. **UI** : Interface moderne avec thème sombre et animations

---

**Date de création** : 18 octobre 2025  
**Version** : 1.0.0  
**Framework Backend** : Spring Boot 2.7.15  
**Framework Frontend** : Angular 19.1.0  
**Java Version** : 17  
**Node.js Version** : 20.x
# TP1 - Web Services REST avec Spring Boot

## 📋 Description du Projet

Ce projet implémente un service web REST pour la gestion de restaurants et de menus, développé dans le cadre du TP1 Web Services REST. Il utilise Spring Boot avec une architecture hexagonale et inclut deux parties principales :

- **Partie A** : Génération automatique de code à partir d'une spécification OpenAPI
- **Partie B** : Création d'un RestaurantController avec architecture hexagonale et base de données H2

## 🏗️ Architecture

Le projet suit une **architecture hexagonale** (Ports & Adapters) :

```
src/com/speed_liv/menu/
    ├── controllers/              ← Couche API (Interface REST)
    │   └── RestaurantController.java
    │
    ├── services/                 ← Couche Application (Logique métier)
    │   └── RestaurantService.java
    │
    ├── model/
    │   ├── entity/               ← Entités du domaine
    │   │   ├── Restaurant.java
    │   │   └── Plat.java
    │   │
    │   └── repository/           ← Port (Interface du domaine)
    │       └── RestaurantRepository.java
    │
    ├── infrastructure/           ← Couche Infrastructure (Adapters)
    │   └── persistance/
    │       ├── JpaRestaurantRepository.java       ← Adapter JPA
    │       ├── RestaurantRepositoryAdapter.java   ← Bridge Pattern
    │       └── RestaurantDataLoader.java          ← Chargement JSON
    │
    └── config/
        └── JpaConfig.java        ← Configuration JPA
```

## 🚀 Technologies Utilisées

- **Java 17** - Langage de programmation
- **Spring Boot 2.7.15** - Framework web
- **Spring Data JPA** - Couche de persistance
- **H2 Database** - Base de données en mémoire
- **Maven 3.9.5** - Gestion des dépendances
- **OpenAPI Generator** - Génération de code REST
- **Swagger UI / SpringDoc** - Documentation API interactive
- **Docker** - Conteneurisation de l'environnement de développement

## 📦 Installation et Démarrage

### Prérequis

- Docker et Docker Compose installés
- Port 8088 disponible sur votre machine

### Démarrage avec Docker

1. **Lancer le conteneur Docker :**
   ```bash
   docker-compose up -d dev-environment
   ```

2. **Compiler le projet :**
   ```bash
   docker exec soa_tp_dev mvn clean install -f /workspace/menu_java/pom.xml
   ```

3. **Démarrer l'application :**
   ```bash
   docker exec soa_tp_dev bash -c "cd /workspace/menu_java && mvn spring-boot:run"
   ```

4. **Accéder à l'application :**
   - API Base URL : http://localhost:8088
   - Swagger UI : http://localhost:8088/swagger-ui.html
   - H2 Console : http://localhost:8088/h2-console

### Configuration H2 Console

Pour accéder à la console H2 :
- **JDBC URL** : `jdbc:h2:mem:restaurantdb`
- **Username** : `sa`
- **Password** : (laisser vide)

## 📚 API Endpoints

### Restaurants

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/restaurants` | Liste tous les restaurants avec leurs plats |
| `GET` | `/restaurants/{id}` | Obtenir un restaurant spécifique |
| `POST` | `/restaurants` | Créer un nouveau restaurant |
| `DELETE` | `/restaurants/{id}` | Supprimer un restaurant |

### Menus (Partie A - OpenAPI Generator)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/menus/du-jour` | Obtenir le menu du jour |
| `GET` | `/plats/{id}` | Obtenir un plat par son ID |
| `GET` | `/plats` | Obtenir tous les plats |

## 🧪 Exemples d'utilisation

### 1. Obtenir tous les restaurants

```bash
curl http://localhost:8088/restaurants
```

**Réponse :**
```json
[
  {
    "id": 1,
    "name": "resto1",
    "plats": [
      {
        "id": 1,
        "name": "Pizza Margherita",
        "price": 8.5,
        "disponible": null
      },
      {
        "id": 2,
        "name": "Lasagnes",
        "price": 10.0,
        "disponible": null
      }
    ]
  },
  {
    "id": 2,
    "name": "resto2",
    "plats": [
      {
        "id": 3,
        "name": "Sushi Saumon",
        "price": 12.0,
        "disponible": null
      },
      {
        "id": 4,
        "name": "Ramen",
        "price": 11.0,
        "disponible": null
      }
    ]
  }
]
```

### 2. Obtenir un restaurant par ID

```bash
curl http://localhost:8088/restaurants/1
```

### 3. Créer un nouveau restaurant

```bash
curl -X POST http://localhost:8088/restaurants \
  -H "Content-Type: application/json" \
  -d '{
    "name": "resto3",
    "plats": [
      {
        "name": "Burger",
        "price": 9.5,
        "disponible": true
      }
    ]
  }'
```

### 4. Supprimer un restaurant

```bash
curl -X DELETE http://localhost:8088/restaurants/1
```

## 📊 Modèle de Données

### Restaurant
```java
{
  "id": Long,
  "name": String,
  "plats": List<Plat>
}
```

### Plat
```java
{
  "id": Long,
  "name": String,
  "price": Double,
  "disponible": Boolean,
  "restaurant": Restaurant
}
```

## 🔧 Configuration

### application.properties

```properties
# Server Configuration
server.port=8088

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:restaurantdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=false

# Jackson Configuration
spring.jackson.date-format=org.openapitools.RFC3339DateFormat
spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS=false
```

## 📝 Partie A : OpenAPI Generator

### Génération du code

Le code de la Partie A a été généré à partir du fichier OpenAPI `openapi-menu-v1.yml` :

```bash
docker exec soa_tp_dev openapi-generator generate \
  -i /workspace/openapi-menu-v1.yml \
  -g spring \
  -o /workspace/menu_java \
  --package-name com.speed_liv.menu \
  --api-package com.speed_liv.menu.api \
  --model-package com.speed_liv.menu.model
```

### Fichier OpenAPI

Le fichier `openapi-menu-v1.yml` définit :
- Les endpoints pour gérer les menus et les plats
- Les modèles de données
- Les réponses HTTP attendues

## 🔄 Partie B : Architecture Hexagonale

### Avantages de l'Architecture Hexagonale

1. **Séparation des préoccupations** : Chaque couche a une responsabilité claire
2. **Testabilité** : Les composants peuvent être testés indépendamment
3. **Maintenabilité** : Facilite les modifications et l'évolution du code
4. **Flexibilité** : Facile de changer d'implémentation (ex: passer de JSON à H2)

### Passage JSON → H2

**Observations :**

| Aspect | Fichier JSON | Base H2 |
|--------|--------------|---------|
| **Performance** | Lecture disque à chaque requête | Données en mémoire, accès rapide |
| **Requêtes** | Parsing JSON manuel | SQL généré automatiquement par Hibernate |
| **Relations** | Gestion manuelle | Relations JPA (`@OneToMany`, `@ManyToOne`) |
| **Persistence** | Lecture seule | CRUD complet (Create, Read, Update, Delete) |
| **Console** | Pas de console | Console H2 pour visualiser les données |
| **Transactions** | Non supportées | Transactions ACID |

## 🐳 Docker

### Structure du Dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk
# Installation de Maven 3.9.5
# Installation d'OpenAPI Generator CLI 7.7.0
# Configuration de l'environnement de développement
```

### docker-compose.yml

```yaml
services:
  dev-environment:
    build: .
    container_name: soa_tp_dev
    ports:
      - "8088:8088"
    volumes:
      - ./workspace:/workspace
```

## 📖 Documentation API

La documentation interactive de l'API est disponible via Swagger UI :

**URL** : http://localhost:8088/swagger-ui.html

Cette interface permet de :
- Visualiser tous les endpoints disponibles
- Tester les endpoints directement depuis le navigateur
- Voir les modèles de données
- Consulter les codes de réponse HTTP

## 🧪 Tests

Pour tester l'application :

1. **Vérifier que l'application démarre :**
   ```bash
   curl http://localhost:8088/actuator/health
   ```

2. **Tester l'endpoint restaurants :**
   ```bash
   curl http://localhost:8088/restaurants
   ```

3. **Accéder à Swagger UI :**
   Ouvrir http://localhost:8088/swagger-ui.html dans un navigateur

## 📁 Structure des Fichiers

```
workspace/
├── menu_java/                      # Projet Spring Boot généré
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/speed_liv/menu/
│   │   │   │   │   ├── controllers/
│   │   │   │   │   ├── services/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── entity/
│   │   │   │   │   │   └── repository/
│   │   │   │   │   ├── infrastructure/
│   │   │   │   │   │   └── persistance/
│   │   │   │   │   └── config/
│   │   │   │   └── org/openapitools/
│   │   │   └── resources/
│   │   │       ├── bd/
│   │   │       │   └── restaurants.json
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
├── openapi-menu-v1.yml            # Spécification OpenAPI
└── README.md                      # Ce fichier
```

## 🤝 Contribution

Ce projet a été développé dans le cadre du TP1 Web Services REST.

## 📄 Licence

Projet académique - IIT G3

## 📞 Support

Pour toute question ou problème :
1. Consulter la documentation Swagger : http://localhost:8088/swagger-ui.html
2. Vérifier les logs de l'application : `docker logs soa_tp_dev`
3. Accéder à la console H2 : http://localhost:8088/h2-console

---

**Date de création** : 18 octobre 2025  
**Version** : 1.0.0  
**Framework** : Spring Boot 2.7.15  
**Java Version** : 17
