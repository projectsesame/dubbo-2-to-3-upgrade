clean:
	mvn clean
package:
	mvn package -DskipTests

HUB ?= release-ci.daocloud.io/mspider
VERSION ?= v0.2
PLATFORM ?= linux/amd64 # linux/amd64,linux/arm64

consumer-docker: package
	cd dubbo-spring-boot-consumer; \
	docker build -t $(HUB)/spring-dubbo3-consumer:$(VERSION) . --platform $(PLATFORM) --push
provider-docker: package
	cd dubbo-spring-boot-provider; \
	docker build -t $(HUB)/spring-dubbo3-provider:$(VERSION) . --platform $(PLATFORM) --push

push: consumer-docker provider-docker
