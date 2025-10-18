import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { OrderItem, Plat, Restaurant } from '../models/restaurant.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems = new BehaviorSubject<OrderItem[]>([]);
  cartItems$ = this.cartItems.asObservable();

  addToCart(plat: Plat, restaurant: Restaurant): void {
    const currentItems = this.cartItems.value;
    const existingItem = currentItems.find(item => item.plat.id === plat.id);

    if (existingItem) {
      existingItem.quantity++;
      this.cartItems.next([...currentItems]);
    } else {
      this.cartItems.next([...currentItems, { plat, restaurant, quantity: 1 }]);
    }
  }

  removeFromCart(platId: number): void {
    const currentItems = this.cartItems.value.filter(item => item.plat.id !== platId);
    this.cartItems.next(currentItems);
  }

  updateQuantity(platId: number, quantity: number): void {
    const currentItems = this.cartItems.value;
    const item = currentItems.find(item => item.plat.id === platId);
    if (item) {
      if (quantity <= 0) {
        this.removeFromCart(platId);
      } else {
        item.quantity = quantity;
        this.cartItems.next([...currentItems]);
      }
    }
  }

  getTotal(): number {
    return this.cartItems.value.reduce((total, item) => total + (item.plat.price * item.quantity), 0);
  }

  clearCart(): void {
    this.cartItems.next([]);
  }

  getCartItems(): OrderItem[] {
    return this.cartItems.value;
  }
}
