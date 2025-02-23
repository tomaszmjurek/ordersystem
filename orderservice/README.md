# Order Service

System entry point for creating order requests. Passes them to other systems for processing. Keeps track of the orders.

## Running integrated applications

Please refer to global [README](./../README.md)

## Running single application

1. Make sure you have Docker environment configured and running on your machine (ie. Docker Desktop for Windows)
2. Start Kafka broker and Kafka UI locally using command:
```
docker compose up -d
```
3. Run _OrderServiceApplication_
