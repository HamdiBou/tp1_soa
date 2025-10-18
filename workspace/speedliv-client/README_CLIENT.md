# SpeedLiv Client - Angular Application

## 📱 Description
Application web Angular qui consomme les APIs REST du backend Spring Boot pour permettre aux clients de:
1. Voir la liste des restaurants disponibles
2. Consulter le menu (plats) de chaque restaurant
3. Ajouter des plats au panier
4. Valider leur commande

## 🚀 Démarrage

### Prérequis
- Docker container `soa_tp_dev` en cours d'exécution
- Backend Spring Boot sur le port 8088
- Node.js et Angular CLI installés dans Docker

### Lancer l'application

1. **Démarrer le backend Spring Boot** (si pas déjà démarré):
```bash
docker exec -d soa_tp_dev bash -c "cd /workspace/menu_java && mvn spring-boot:run > /workspace/spring-boot.log 2>&1 &"
```

2. **Démarrer le serveur Angular**:
```bash
docker exec -d soa_tp_dev bash -c "cd /workspace/speedliv-client && ng serve --host 0.0.0.0 --port 4200 --disable-host-check > /workspace/angular.log 2>&1 &"
```

3. **Accéder à l'application**:
   - Angular App: http://localhost:4200
   - API Backend: http://localhost:8088
   - Swagger UI: http://localhost:8088/swagger-ui.html

## 🏗️ Architecture

### Structure du projet
```
speedliv-client/
├── src/
│   ├── app/
│   │   ├── models/
│   │   │   └── restaurant.model.ts    # Interfaces TypeScript
│   │   ├── services/
│   │   │   ├── restaurant.service.ts  # Service API REST
│   │   │   └── cart.service.ts        # Gestion du panier
│   │   ├── app.component.ts           # Composant principal
│   │   ├── app.component.html         # Template HTML
│   │   └── app.component.css          # Styles CSS
│   └── main.ts
├── package.json
└── angular.json
```

### Flux de données
```
Angular App (Port 4200)
    ↓ HTTP GET
Spring Boot API (Port 8088)
    ↓ JPA
H2 Database (In-Memory)
```

## 📋 Fonctionnalités

### 1. **Liste des Restaurants**
- Affichage en grille de tous les restaurants
- Compteur de plats disponibles par restaurant
- Click pour voir le menu

### 2. **Menu des Plats**
- Liste des plats avec prix
- Badge disponible/indisponible
- Bouton "Ajouter au panier"

### 3. **Panier (Cart)**
- Affichage fixe en bas à droite
- Gestion des quantités (+/-)
- Suppression d'articles
- Calcul automatique du total
- Bouton "Valider la Commande"

### 4. **Confirmation de Commande**
- Modal avec résumé
- Liste détaillée des articles
- Total final
- Réinitialisation du panier

## 🔧 Services Angular

### RestaurantService
```typescript
- getAllRestaurants(): Observable<Restaurant[]>
- getRestaurantById(id: number): Observable<Restaurant>
```

### CartService
```typescript
- addToCart(plat: Plat, restaurant: Restaurant): void
- removeFromCart(platId: number): void
- updateQuantity(platId: number, quantity: number): void
- getTotal(): number
- clearCart(): void
```

## 🎨 Design

### Theme Customization
The application features a modern dark gradient theme:

**Color Palette:**
- **Primary Background**: Dark blue to purple gradient (`#1e3c72` → `#2a5298` → `#7e22ce`)
- **Primary Purple**: Rich purple `#7e22ce` with light purple accents `#a855f7`
- **Text Colors**: Dark blue `#1e3c72` for headings, slate `#475569` for body text
- **Interactive Elements**: Purple gradients with hover effects and shadows

**Visual Features:**
- Gradient backgrounds throughout the interface
- Box shadows with color-matched tints
- Backdrop blur effects for depth
- Smooth transitions on all interactive elements
- Scale animations on button hover
- Enhanced borders with subtle purple tints

**Responsive Design:**
- Desktop: Multi-column grid layouts
- Tablet: 2-column responsive grid
- Mobile: Single column with optimized spacing

**Key UI Elements:**
- Restaurant cards with white/blue gradients and purple shadows
- Purple gradient buttons with hover scale effects
- Fixed shopping cart with glass-morphism effect
- Modal overlays with backdrop blur
- Loading spinner with purple glow effect

### How to Restart Angular Dev Server

If the hot reload stops working, restart the development server:

```bash
# Stop any existing Angular processes
docker exec soa_tp_dev bash -c "pkill -9 -f 'ng serve'"

# Start the Angular dev server
docker exec soa_tp_dev bash -c "cd /workspace/speedliv-client && ng serve --host 0.0.0.0 --port 4200"
```

Wait 20-30 seconds for compilation, then refresh your browser at http://localhost:4200

## 🔗 APIs Consommées

### GET /restaurants
Récupère tous les restaurants avec leurs plats

**Response:**
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
      }
    ]
  }
]
```

### GET /restaurants/{id}
Récupère un restaurant spécifique

## 🐛 Debugging

### Vérifier les logs Angular:
```bash
docker exec soa_tp_dev tail -f /workspace/angular.log
```

### Vérifier les logs Spring Boot:
```bash
docker exec soa_tp_dev tail -f /workspace/spring-boot.log
```

### Vérifier que les serveurs sont actifs:
```bash
docker exec soa_tp_dev ps aux | grep -E "java|node"
```

## 🌐 CORS Configuration

Le backend Spring Boot est configuré pour accepter les requêtes depuis:
- http://localhost:4200 (Angular dev server)
- http://localhost:8088 (Backend)

Voir: `menu_java/src/main/java/com/speed_liv/menu/config/CorsConfig.java`

## 📦 Dépendances Principales

### Angular (package.json)
- @angular/core: ^19.1.0
- @angular/common: ^19.1.0
- @angular/platform-browser: ^19.1.0
- rxjs: ~7.8.0
- typescript: ~5.7.2

### Build Tool
- npm avec option `--no-bin-links` pour compatibilité Windows/Docker

## 🚦 États de l'Application

1. **Loading**: Spinner pendant le chargement des données
2. **Error**: Message d'erreur avec bouton "Réessayer"
3. **Restaurants List**: Grille des restaurants disponibles
4. **Menu View**: Liste des plats d'un restaurant
5. **Cart Open**: Panier avec articles sélectionnés
6. **Order Confirmed**: Modal de confirmation

## 📱 Responsive

- **Desktop**: Grille multi-colonnes
- **Tablet**: 2 colonnes
- **Mobile**: 1 colonne, panier plein écran

## 🎯 Prochaines Améliorations Possibles

- [ ] Authentification utilisateur
- [ ] Historique des commandes
- [ ] Filtres et recherche
- [ ] Images des plats
- [ ] Notation et commentaires
- [ ] Paiement en ligne
- [ ] Suivi de livraison en temps réel

## 📝 Notes

- L'application utilise Angular Standalone Components (pas de NgModule)
- HttpClient pour les appels API REST
- RxJS Observables pour la gestion asynchrone
- BehaviorSubject pour le state management du panier
