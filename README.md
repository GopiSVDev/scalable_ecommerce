# Scalable E-Commerce Backend

This project is a **scalable e-commerce backend** designed for handling products, users, carts, and orders. It uses modern tools and techniques such as **service discovery (Eureka Server)**, **API Gateway**, and **JWT authentication** to manage and route requests efficiently. This backend is built using **Spring Boot** and follows a **microservices architecture**.

---

## 🏗️ Microservices Architecture

This project is designed using a **microservices architecture**, where each core feature is built as an independent service. This makes the system modular, scalable, and easy to maintain.

| Microservice       | Description                                           | Port   |
|--------------------|-------------------------------------------------------|--------|
| **User Service**   | Manages user registration, login, and profile         | `8080` |
| **Product Service**| Handles products, categories, and search              | `8081` |
| **Cart Service**   | Manages shopping cart operations                      | `8082` |
| **API Gateway**    | Central routing layer using Spring Cloud Gateway      | `8090` |
| **Eureka Server**  | Service discovery and registry                        | `8761` |

Each service registers with **Eureka**, and all requests are routed via the **API Gateway**. Secure access is handled using **JWT tokens**.

---

## 🚀 Features

### ✅ User Service
- User Registration
- User Login (with JWT)
- Get Authenticated User Profile

### ✅ Product Service
- Create / Update / Delete Products
- Get Product by ID
- Get Products by Category
- Search Products by Title
- Get All Products

### ✅ Cart Service
- Add Product to Cart
- Get Cart Items
- Remove Product from Cart
- Update Quantity
- Clear Cart

### ✅ Infrastructure
- API Gateway for routing and JWT filtering
- Eureka Server for service discovery
- PostgreSQL for persistent storage (each service uses its own database)
- JWT-based authentication

### ✅ DevOps
- Dockerized microservices
- Docker Compose support for running all services and databases together

---

## 🧩 API Routes

### 🔐 User Service (`/api/user/`)

- `POST /create` – Register new user  
- `POST /login` – Authenticate user and return JWT token  
- `GET /me` – Get profile of logged-in user (JWT required)

### 📦 Product Service (`/api/products/`)

- `POST /` – Create a product  
- `PUT /{id}` – Update product by ID  
- `GET /{id}` – Get product by ID  
- `GET /category/{category}` – Get products by category  
- `GET /search?title=` – Search products by title  
- `GET /` – Get all products  
- `DELETE /{id}` – Delete product by ID

### 🛒 Cart Service (`/api/cart/`)

- `POST /add` – Add item to cart  
- `GET /` – Get current cart  
- `DELETE /remove/{productId}` – Remove item from cart  
- `PUT /update/{productId}` – Update quantity  
- `DELETE /clear` – Clear all cart items

---

## 🔐 JWT Authentication

After logging in via `/api/user/login`, include the returned token in the `Authorization` header for protected routes:

---

## 🐘 Database

- All microservices use **PostgreSQL** as their relational database.
- Each service can use its own schema or isolated DB for better modularity and scaling.

---

## 🐳 Docker & Containerization

- All microservices are **Dockerized** using individual Dockerfiles.
- A **Docker Compose** file is provided to spin up:
  - All microservices
  - API Gateway
  - Eureka Server
  - PostgreSQL databases

---

## 📦 Technologies Used

- Java 17
- Spring Boot
- Spring Cloud (Eureka, Gateway)
- Spring Security + JWT
- PostgreSQL
- Docker & Docker Compose
- Maven
- Lombok

---

