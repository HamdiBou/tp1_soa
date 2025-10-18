export interface Plat {
  id: number;
  name: string;
  price: number;
  disponible?: boolean;
}

export interface Restaurant {
  id: number;
  name: string;
  plats: Plat[];
}

export interface OrderItem {
  plat: Plat;
  restaurant: Restaurant;
  quantity: number;
}
