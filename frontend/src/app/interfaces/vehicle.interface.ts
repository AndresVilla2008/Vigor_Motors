export interface Vehicle {
  id: number;
  brand: string;
  model: string;
  year: number;
  price: number;
  stock: number;
  color: string;
  fuelType: 'Gasolina' | 'Diesel' | 'Híbrido' | 'Eléctrico';
  transmission: 'Manual' | 'Automática';
  mileage: number;
  description: string;
  imageUrl: string;
}