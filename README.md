# 📱 PhonePe Saga Application

This project is a microservices-based phonepe saga system using Spring Boot, Kafka, and Docker. It supports account creation, balance transfer, transaction history, and inter-service communication through Kafka events.

---

## 🧱 Project Structure

phonepe-saga-application/
│
├── account-service/ # initiates transfers & fetch all transfers
├── wallet-service/ # Validates accounts, updates balances
├── transaction-history-service/ # Records transaction outcomes
├── commons-dtos/ # Shared DTOs between services
├── docker-compose.yml # Runs all services and Kafka
└── pom.xml # Parent Maven file (multi-module)
---

## 🔄 Workflow Overview

### 1. **Initiate Transfer** (`account-service`)
- A user requests a balance transfer.
- `account-service` saves the transaction with status `PENDING`.
- It produces a Kafka message to a topic (e.g., `wallet.transfer.request`).

### 2. **Validate and Transfer** (`wallet-service`)
- Consumes the Kafka event.
- Validates account number and balance.
- If validation passes:
  - Transfers the amount.
  - Produces a Kafka message with result: `SUCCESS`.
- If validation fails:
  - Produces a Kafka message with result: `FAILED`.

### 3. **Update Transaction Status**
- **`transaction-history-service`**:
  - Consumes result message from Kafka.
  - Saves the transaction with status `SUCCESS` or `FAILED`.

- **`account-service`**:
  - Updates the original transaction record from `PENDING` to `SUCCESS` or `FAILED`.

---

## ⚙️ Tech Stack

- **Spring Boot** - REST APIs & service layers
- **Apache Kafka** - Event-driven communication
- **Maven** - Build and dependency management
- **Docker & Docker Compose** - Container orchestration
- **MySQL** - Database for services

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Docker & Docker Compose
- Maven 3.8+

### Running the Project

```bash
# Build all services
mvn clean install

# Start all services and Kafka
docker-compose up --build
