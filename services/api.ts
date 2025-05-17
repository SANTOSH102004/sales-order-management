// This file contains mock API functions that would normally connect to your Spring Boot backend
// In a real application, these would make actual HTTP requests

import type { Customer, Order, Product, User } from "@/types"

// Mock data
const mockUsers: User[] = [
  {
    id: 1,
    name: "Admin User",
    email: "admin@example.com",
    role: "ADMIN",
  },
]

const mockCustomers: Customer[] = [
  {
    id: 1,
    name: "Acme Corporation",
    email: "contact@acme.com",
    phone: "555-123-4567",
    company: "Acme Corp",
    address: {
      street: "123 Main St",
      city: "Anytown",
      state: "CA",
      zipCode: "12345",
      country: "USA",
    },
    orderCount: 5,
  },
  {
    id: 2,
    name: "Globex Industries",
    email: "info@globex.com",
    phone: "555-987-6543",
    company: "Globex",
    address: {
      street: "456 Tech Blvd",
      city: "Silicon Valley",
      state: "CA",
      zipCode: "94123",
      country: "USA",
    },
    orderCount: 3,
  },
  {
    id: 3,
    name: "Stark Enterprises",
    email: "sales@stark.com",
    phone: "555-789-0123",
    company: "Stark",
    address: {
      street: "789 Innovation Way",
      city: "Metropolis",
      state: "NY",
      zipCode: "10001",
      country: "USA",
    },
    orderCount: 8,
  },
]

const mockProducts: Product[] = [
  {
    id: 1,
    name: "Premium T-Shirt",
    description: "High-quality cotton t-shirt",
    sku: "TS-001",
    styleNumber: "ST-1001",
    category: "Apparel",
    price: 29.99,
    stockQuantity: 100,
  },
  {
    id: 2,
    name: "Designer Jeans",
    description: "Slim fit designer jeans",
    sku: "DJ-002",
    styleNumber: "ST-2002",
    category: "Apparel",
    price: 89.99,
    stockQuantity: 50,
  },
  {
    id: 3,
    name: "Leather Jacket",
    description: "Genuine leather jacket",
    sku: "LJ-003",
    styleNumber: "ST-3003",
    category: "Outerwear",
    price: 199.99,
    stockQuantity: 25,
  },
  {
    id: 4,
    name: "Running Shoes",
    description: "Lightweight running shoes",
    sku: "RS-004",
    styleNumber: "ST-4004",
    category: "Footwear",
    price: 119.99,
    stockQuantity: 75,
  },
  {
    id: 5,
    name: "Winter Coat",
    description: "Warm winter coat with hood",
    sku: "WC-005",
    styleNumber: "ST-5005",
    category: "Outerwear",
    price: 149.99,
    stockQuantity: 30,
  },
  {
    id: 6,
    name: "Casual Socks",
    description: "Pack of 5 casual socks",
    sku: "CS-006",
    styleNumber: "ST-6006",
    category: "Accessories",
    price: 14.99,
    stockQuantity: 200,
  },
  {
    id: 7,
    name: "Dress Shirt",
    description: "Formal dress shirt",
    sku: "DS-007",
    styleNumber: "ST-7007",
    category: "Apparel",
    price: 59.99,
    stockQuantity: 80,
  },
  {
    id: 8,
    name: "Sunglasses",
    description: "UV protection sunglasses",
    sku: "SG-008",
    styleNumber: "ST-8008",
    category: "Accessories",
    price: 79.99,
    stockQuantity: 40,
  },
]

const mockOrders: Order[] = [
  {
    id: 1,
    customer: mockCustomers[0],
    orderDate: new Date("2023-05-15T10:30:00"),
    expectedDeliveryDate: new Date("2023-05-22T10:30:00"),
    status: "DELIVERED",
    orderItems: [
      {
        id: 1,
        product: mockProducts[0],
        quantity: 2,
        unitPrice: mockProducts[0].price,
      },
      {
        id: 2,
        product: mockProducts[1],
        quantity: 1,
        unitPrice: mockProducts[1].price,
      },
    ],
    subtotal: 149.97,
    tax: 15.0,
    shippingCost: 10.0,
    total: 174.97,
    salesRepresentative: mockUsers[0],
    billingAddress: mockCustomers[0].address,
    shippingAddress: mockCustomers[0].address,
  },
  {
    id: 2,
    customer: mockCustomers[1],
    orderDate: new Date("2023-05-16T14:45:00"),
    expectedDeliveryDate: new Date("2023-05-23T14:45:00"),
    status: "SHIPPED",
    orderItems: [
      {
        id: 3,
        product: mockProducts[2],
        quantity: 1,
        unitPrice: mockProducts[2].price,
      },
    ],
    subtotal: 199.99,
    tax: 20.0,
    shippingCost: 15.0,
    total: 234.99,
    salesRepresentative: mockUsers[0],
    billingAddress: mockCustomers[1].address,
    shippingAddress: mockCustomers[1].address,
  },
  {
    id: 3,
    customer: mockCustomers[2],
    orderDate: new Date("2023-05-17T09:15:00"),
    expectedDeliveryDate: new Date("2023-05-24T09:15:00"),
    status: "CONFIRMED",
    orderItems: [
      {
        id: 4,
        product: mockProducts[3],
        quantity: 2,
        unitPrice: mockProducts[3].price,
      },
      {
        id: 5,
        product: mockProducts[4],
        quantity: 1,
        unitPrice: mockProducts[4].price,
      },
      {
        id: 6,
        product: mockProducts[5],
        quantity: 3,
        unitPrice: mockProducts[5].price,
      },
    ],
    subtotal: 434.95,
    tax: 43.5,
    shippingCost: 20.0,
    total: 498.45,
    salesRepresentative: mockUsers[0],
    billingAddress: mockCustomers[2].address,
    shippingAddress: mockCustomers[2].address,
  },
  {
    id: 4,
    customer: mockCustomers[0],
    orderDate: new Date("2023-05-18T16:30:00"),
    expectedDeliveryDate: new Date("2023-05-25T16:30:00"),
    status: "PENDING",
    orderItems: [
      {
        id: 7,
        product: mockProducts[6],
        quantity: 1,
        unitPrice: mockProducts[6].price,
      },
      {
        id: 8,
        product: mockProducts[7],
        quantity: 1,
        unitPrice: mockProducts[7].price,
      },
    ],
    subtotal: 139.98,
    tax: 14.0,
    shippingCost: 10.0,
    total: 163.98,
    salesRepresentative: mockUsers[0],
    billingAddress: mockCustomers[0].address,
    shippingAddress: mockCustomers[0].address,
  },
]

// Helper function to simulate API delay
const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms))

// Authentication APIs
export const login = async (username: string, password: string): Promise<User> => {
  await delay(1000)

  // In a real app, this would validate credentials against the backend
  if (username === "admin" && password === "password") {
    localStorage.setItem("auth_token", "mock_jwt_token")
    return mockUsers[0]
  }

  throw new Error("Invalid credentials")
}

export const logout = () => {
  localStorage.removeItem("auth_token")
}

export const getCurrentUser = async (): Promise<User> => {
  await delay(500)

  const token = localStorage.getItem("auth_token")
  if (!token) {
    throw new Error("Not authenticated")
  }

  return mockUsers[0]
}

// Dashboard APIs
export const fetchDashboardStats = async () => {
  await delay(1000)

  return {
    totalOrders: mockOrders.length,
    totalCustomers: mockCustomers.length,
    totalProducts: mockProducts.length,
    totalRevenue: mockOrders.reduce((sum, order) => sum + order.total, 0),
  }
}

export const fetchRecentOrders = async (): Promise<Order[]> => {
  await delay(800)

  // Return the most recent orders
  return [...mockOrders].sort((a, b) => new Date(b.orderDate).getTime() - new Date(a.orderDate).getTime()).slice(0, 5)
}

// Order APIs
export const fetchOrders = async (
  params: {
    page?: number
    size?: number
    status?: string
    search?: string
  } = {},
): Promise<{ content: Order[]; totalPages: number }> => {
  await delay(1000)

  const { page = 0, size = 10, status = "", search = "" } = params

  let filteredOrders = [...mockOrders]

  if (status) {
    filteredOrders = filteredOrders.filter((order) => order.status === status)
  }

  if (search) {
    const searchLower = search.toLowerCase()
    filteredOrders = filteredOrders.filter(
      (order) => order.customer.name.toLowerCase().includes(searchLower) || order.id.toString().includes(searchLower),
    )
  }

  // Sort by most recent
  filteredOrders.sort((a, b) => new Date(b.orderDate).getTime() - new Date(a.orderDate).getTime())

  const totalPages = Math.ceil(filteredOrders.length / size)
  const content = filteredOrders.slice(page * size, (page + 1) * size)

  return { content, totalPages }
}

export const fetchOrderById = async (orderId: number): Promise<Order> => {
  await delay(800)

  const order = mockOrders.find((o) => o.id === orderId)
  if (!order) {
    throw new Error("Order not found")
  }

  return order
}

export const createOrder = async (orderData: any): Promise<Order> => {
  await delay(1500)

  const customer = mockCustomers.find((c) => c.id === orderData.customerId)
  if (!customer) {
    throw new Error("Customer not found")
  }

  const newOrderItems = orderData.orderItems.map((item: any, index: number) => {
    const product = mockProducts.find((p) => p.id === item.productId)
    if (!product) {
      throw new Error(`Product with ID ${item.productId} not found`)
    }

    return {
      id: mockOrders.length * 10 + index + 1,
      product,
      quantity: item.quantity,
      unitPrice: item.unitPrice || product.price,
    }
  })

  const subtotal = newOrderItems.reduce((sum, item) => sum + item.quantity * item.unitPrice, 0)
  const tax = subtotal * 0.1 // 10% tax
  const shippingCost = 15 // Flat shipping rate

  const newOrder: Order = {
    id: mockOrders.length + 1,
    customer,
    orderDate: new Date(),
    expectedDeliveryDate: orderData.expectedDeliveryDate,
    status: "PENDING",
    orderItems: newOrderItems,
    subtotal,
    tax,
    shippingCost,
    total: subtotal + tax + shippingCost,
    salesRepresentative: mockUsers[0],
    billingAddress: customer.address,
    shippingAddress: customer.address,
  }

  mockOrders.push(newOrder)

  return newOrder
}

export const updateOrderStatus = async (orderId: number, status: string): Promise<void> => {
  await delay(1000)

  const orderIndex = mockOrders.findIndex((o) => o.id === orderId)
  if (orderIndex === -1) {
    throw new Error("Order not found")
  }

  mockOrders[orderIndex].status = status
}

// Customer APIs
export const fetchCustomers = async (
  params: {
    page?: number
    size?: number
    search?: string
  } = {},
): Promise<{ content: Customer[]; totalPages: number }> => {
  await delay(1000)

  const { page = 0, size = 10, search = "" } = params

  let filteredCustomers = [...mockCustomers]

  if (search) {
    const searchLower = search.toLowerCase()
    filteredCustomers = filteredCustomers.filter(
      (customer) =>
        customer.name.toLowerCase().includes(searchLower) ||
        customer.email.toLowerCase().includes(searchLower) ||
        customer.phone.includes(search),
    )
  }

  // Sort alphabetically by name
  filteredCustomers.sort((a, b) => a.name.localeCompare(b.name))

  const totalPages = Math.ceil(filteredCustomers.length / size)
  const content = filteredCustomers.slice(page * size, (page + 1) * size)

  return { content, totalPages }
}

export const createCustomer = async (customerData: any): Promise<Customer> => {
  await delay(1500)

  const newCustomer: Customer = {
    id: mockCustomers.length + 1,
    name: customerData.name,
    email: customerData.email,
    phone: customerData.phone,
    company: customerData.company || "",
    address: customerData.address,
    orderCount: 0,
  }

  mockCustomers.push(newCustomer)

  return newCustomer
}

// Product APIs
export const fetchProducts = async (
  params: {
    page?: number
    size?: number
    search?: string
  } = {},
): Promise<{ content: Product[]; totalPages: number }> => {
  await delay(1000)

  const { page = 0, size = 10, search = "" } = params

  let filteredProducts = [...mockProducts]

  if (search) {
    const searchLower = search.toLowerCase()
    filteredProducts = filteredProducts.filter(
      (product) =>
        product.name.toLowerCase().includes(searchLower) ||
        product.sku.toLowerCase().includes(searchLower) ||
        product.styleNumber.toLowerCase().includes(searchLower),
    )
  }

  // Sort alphabetically by name
  filteredProducts.sort((a, b) => a.name.localeCompare(b.name))

  const totalPages = Math.ceil(filteredProducts.length / size)
  const content = filteredProducts.slice(page * size, (page + 1) * size)

  return { content, totalPages }
}
