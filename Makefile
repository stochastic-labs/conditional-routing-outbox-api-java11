APP_NAME=conditional-routing-outbox-api-java11
DOCKER_IMAGE=stochasticlabs/routing-api:1.0.0
PORT=8081

.PHONY: build run test clean d-build d-run d-stop

build:
	mvn clean package -DskipTests

test:
	mvn test

run:
	mvn spring-boot:run

clean:
	mvn clean

d-build:
	docker build -t $(DOCKER_IMAGE) .

d-run:
	docker run -d -p $(PORT):$(PORT) --name $(APP_NAME) $(DOCKER_IMAGE)

d-stop:
	@docker stop $(APP_NAME) 2>/dev/null || true
	@docker rm $(APP_NAME) 2>/dev/null || true