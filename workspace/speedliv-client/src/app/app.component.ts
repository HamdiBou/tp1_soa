import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Restaurant, Plat, OrderItem } from './models/restaurant.model';
import { RestaurantService } from './services/restaurant.service';
import { CartService } from './services/cart.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'SpeedLiv - Commande en Ligne';
  restaurants: Restaurant[] = [];
  selectedRestaurant: Restaurant | null = null;
  cartItems: OrderItem[] = [];
  loading = false;
  error = '';
  showOrderModal = false;

  constructor(
    private restaurantService: RestaurantService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.loadRestaurants();
    this.cartService.cartItems$.subscribe(items => {
      this.cartItems = items;
    });
  }

  loadRestaurants(): void {
    this.loading = true;
    this.error = '';
    this.restaurantService.getAllRestaurants().subscribe({
      next: (data) => {
        this.restaurants = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des restaurants. Vérifiez que le serveur Spring Boot est démarré sur le port 8088.';
        this.loading = false;
        console.error('Error loading restaurants:', err);
      }
    });
  }

  selectRestaurant(restaurant: Restaurant): void {
    this.selectedRestaurant = restaurant;
  }

  backToRestaurants(): void {
    this.selectedRestaurant = null;
  }

  addToCart(plat: Plat): void {
    if (this.selectedRestaurant) {
      this.cartService.addToCart(plat, this.selectedRestaurant);
    }
  }

  removeFromCart(platId: number): void {
    this.cartService.removeFromCart(platId);
  }

  updateQuantity(platId: number, quantity: number): void {
    this.cartService.updateQuantity(platId, quantity);
  }

  getTotal(): number {
    return this.cartService.getTotal();
  }

  validateOrder(): void {
    if (this.cartItems.length === 0) {
      alert('Votre panier est vide!');
      return;
    }
    this.showOrderModal = true;
  }

  closeModal(): void {
    this.showOrderModal = false;
    this.cartService.clearCart();
    this.selectedRestaurant = null;
  }
}
