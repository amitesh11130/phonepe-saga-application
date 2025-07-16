# 📱 PhonePe Saga Application

This project is a microservices-based phonepe saga system using Spring Boot, Kafka, and Docker. It supports account creation, balance transfer, transaction history, and inter-service communication through Kafka events.

---

## 🧱 Project Structure

### phonepe-saga-application

- account-service/ # initiates transfers & fetch all transfers
- wallet-service/ # Validates accounts, updates balances
- transaction-history-service/ # Records transaction outcomes
- commons-dtos/ # Shared DTOs between services
- docker-compose.yml # Runs all services and Kafka
- pom.xml # Parent Maven file (multi-module)


## 🔄 Workflow: Saga Pattern via Kafka

### 1. `account-service` – Initiates Transfer
- Accepts transfer request.
- Saves transaction with status `PENDING`.
- Produces Kafka message to `wallet.transfer.request`.

### 2. `wallet-service` – Validates and Transfers
- Consumes the Kafka message.
- Verifies:
  - Valid account numbers
  - Sufficient balance
- If **valid**:
  - Transfers funds.
  - Produces Kafka message to `wallet.transfer.result` with status `SUCCESS`.
- If **invalid**:
  - Produces message with status `FAILED`.

### 3. `transaction-history-service` – Records the Outcome
- Consumes `wallet.transfer.result`.
- Stores transaction with final status `SUCCESS` or `FAILED`.

### 4. `account-service` – Final Status Update
- Also consumes the result message.
- Updates initial transaction from `PENDING` to `SUCCESS` or `FAILED`.

---

## ⚙️ Tech Stack

- **Java 17**, **Spring Boot**
- **Apache Kafka**
- **Maven**
- **Docker & Docker Compose**
- **PostgreSQL / MySQL**

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose

### Run All Services

```bash
# 1. Build the project
mvn clean install

# 2. Start services with Kafka
docker-compose up --build
