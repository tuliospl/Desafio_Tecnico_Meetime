.PHONY: up down run test format install package springdoc

COMPOSE=./docker-compose.yaml

up:
	docker-compose -f $(COMPOSE) up -d

down:
	docker-compose -f $(COMPOSE) down -v --remove-orphans

run:
	./mvnw spring-boot:run

test:
	./mvnw clean verify

format:
	./mvnw detekt:check -Ddetekt.config=detekt-custom-rules.yml

install:
	./mvnw clean install
