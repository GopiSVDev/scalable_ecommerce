# Scalable E-Commerce Platform

This project demonstrates a scalable eCommerce platform built using a microservices architecture. It leverages key tools such as Docker, Docker Compose, API Gateway, Service Discovery, and individual microservices to create a flexible and scalable solution.

## Microservices

### 1. **User Service**
Handles user registration, authentication, and profile management.

### 2. **Product Catalog Service**
Manages product listings, categories, and inventory.

### 3. **Shopping Cart Service**
Allows users to add, update, and view products in their shopping carts.

## Architecture

- **API Gateway**: Routes client requests to the appropriate microservice.
- **Service Discovery**: Uses **Eureka** for automatic detection and management of service instances.

## Containerization

- The platform utilizes **Docker** and **Docker Compose** to containerize the microservices and manage multi-container applications.
- Docker Compose simplifies orchestration, networking, and scaling of services.
