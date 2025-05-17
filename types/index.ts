export interface User {
  id: number
  name: string
  email: string
  role: string
}

export interface Address {
  street: string
  city: string
  state: string
  zipCode: string
  country: string
}

export interface Customer {
  id: number
  name: string
  email: string
  phone: string
  company: string
  address: Address
  orderCount: number
}

export interface Product {
  id: number
  name: string
  description: string
  sku: string
  styleNumber: string
  category: string
  price: number
  stockQuantity: number
}

export interface OrderItem {
  id: number
  product: Product
  quantity: number
  unitPrice: number
}

export interface Order {
  id: number
  customer: Customer
  orderDate: Date
  expectedDeliveryDate: Date
  status: string
  orderItems: OrderItem[]
  subtotal: number
  tax: number
  shippingCost: number
  total: number
  salesRepresentative: User
  billingAddress: Address
  shippingAddress: Address
}
