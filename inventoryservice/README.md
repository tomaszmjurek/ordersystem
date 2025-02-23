# Inventory Service

Keeps track of product inventory and updates stock levels. Listens to order requests and validates them with products availability and produces shipping requests.

## Running integrated applications

Please refer to global [README](./../README.md)

## Running single application

1. Make sure you have Docker environment configured and running on your machine (ie. Docker Desktop for Windows)
2. Start Postgres container:
```
docker run --rm -d -it --name pg-inventory -e "POSTGRES_PASSWORD=postgres" -p 5432:5432 postgres:15
```
3. Run _InventoryServiceApplication_
