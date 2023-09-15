# Introduction

The Orion-LD Adapter is one of the components used by any service that wishes to interact with Orion-LD. It is a RESTful API that offers a wide range of endpoints for interacting with various operations supported by Orion-LD. Additionally, it provides a subscription service that can configure subscriptions on a given server, allowing the creation of multiple subscriptions as needed.

It is built using Java 17, Spring Boot 3.x, and Gradle.

## Main features:
- Subscribe to new entities stored in Orion-LD.
- Persist new entities to Orion-LD
- Manage entities or subscriptions on Orion-LD

## Subscription Components
[![](https://mermaid.ink/img/pako:eNqVkzFvwjAQhf-K5amVQNCOHlhK1aFFIFJ18mLsS2Mpsd2zjYQQ_72mcZBSAqWZLN_zd--dcnsqrQLKqIevCEbCXItPFA03JH1OYNBSO2ECWQhZaQPnhSJuvETtgrbmyZqAtq4Br-sKwK2WA7Alpur4bc5NW8tdx7PZcBtGVsvinayP7n0gE-H0ZPsw8a14kzsMv_0FzZ4YcWgleJ-Zd_fnjCxNgM4vIy_Qj-hJibY5BSLHN91YusvxJQt90FwEcc3EIOIDUJe7PukypBdlFTe19hWxSJ6VDkOMPxOswbukhn_7XrXTvwmQ_w5GHqdTsny9Gu-k7c82ZwXVpQXFDR3RBrARWqXN2B-xnIYKGuCUpaOCUsQ6cMrNIUlFDLbYGUlZwAgjGp0SoVskykpR-3QLiW1x0W7bz9IdvgG-tT_W?type=png)](https://mermaid.live/edit#pako:eNqVkzFvwjAQhf-K5amVQNCOHlhK1aFFIFJ18mLsS2Mpsd2zjYQQ_72mcZBSAqWZLN_zd--dcnsqrQLKqIevCEbCXItPFA03JH1OYNBSO2ECWQhZaQPnhSJuvETtgrbmyZqAtq4Br-sKwK2WA7Alpur4bc5NW8tdx7PZcBtGVsvinayP7n0gE-H0ZPsw8a14kzsMv_0FzZ4YcWgleJ-Zd_fnjCxNgM4vIy_Qj-hJibY5BSLHN91YusvxJQt90FwEcc3EIOIDUJe7PukypBdlFTe19hWxSJ6VDkOMPxOswbukhn_7XrXTvwmQ_w5GHqdTsny9Gu-k7c82ZwXVpQXFDR3RBrARWqXN2B-xnIYKGuCUpaOCUsQ6cMrNIUlFDLbYGUlZwAgjGp0SoVskykpR-3QLiW1x0W7bz9IdvgG-tT_W)

For the subscriptions we will need to use "/api/v1/subscribe" endpoint, it will permit us to subscribe to a topic.  In this case, the request needs to be a JSON with the following parameters.
```
{
    "type": String,
    "notification-endpoint-uri": String,
    "entities": [String]
}
```
We will need to specify a type, which is typically Subscription. Next, specify the notification endpoint, where an automatic POST request will be made if there are any machines subscribed to the topic we have specified. Then, provide a list of the topics to which we want to subscribe.

## Publisher Components
[![](https://mermaid.ink/img/pako:eNp1kjFvwyAQhf8KujmW1ZUhS9OtUaK6oxeCz_VJNlA4IkVR_nsutd2qcswE3PveO3FcwfoGQUPC74zO4o7MVzRD7ZSsYCKTpWAcq72xHTlcFo751FPqXr3j6Pse46qkwngmKxajYnIsttuFhVbHQ_WpPh5NJValCVSeX8ow6kZ8Af0ZTUHiEr3FlGajf-AkEuoQybvifafnknpzTHxR7NVcG9H5VDzLmlhpqKU4GBbpSuB6myl4l_ApJtz0YvpXCBsYULKokRFeH1gN3OGANWjZNtia3HMNtbuJ1GT21cVZ0BwzbiCHxvA8cdCt6ZPcYkPs4378Fj-_43YHvODBWg?type=png)](https://mermaid.live/edit#pako:eNp1kjFvwyAQhf8KujmW1ZUhS9OtUaK6oxeCz_VJNlA4IkVR_nsutd2qcswE3PveO3FcwfoGQUPC74zO4o7MVzRD7ZSsYCKTpWAcq72xHTlcFo751FPqXr3j6Pse46qkwngmKxajYnIsttuFhVbHQ_WpPh5NJValCVSeX8ow6kZ8Af0ZTUHiEr3FlGajf-AkEuoQybvifafnknpzTHxR7NVcG9H5VDzLmlhpqKU4GBbpSuB6myl4l_ApJtz0YvpXCBsYULKokRFeH1gN3OGANWjZNtia3HMNtbuJ1GT21cVZ0BwzbiCHxvA8cdCt6ZPcYkPs4378Fj-_43YHvODBWg)

To publish entities, we need to adhere to the NGSI-LD specifications, which typically require the inclusion of certain mandatory fields. These fields include an "id" as a String to uniquely identify the entity and a "type" indicating the entity's type. If these requirements are met, the entity will be published.

The intention is to expand this section with more operations for Orion-LD, thereby preparing it to integrate this Adapter with any service that requires the handling of Fiware data.

# Getting Started

## Prerequisites
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Gradle](https://gradle.org/install/)
- [Spring Boot](https://spring.io/projects/spring-boot/)
- [MongoDB](https://www.mongodb.com/)
- [Docker Desktop](https://www.docker.com/)
- [Go](https://golang.org/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Git](https://git-scm.com/)
- [Azure DevOps](https://azure.microsoft.com/en-us/services/devops/)
- [Orion-LD](https://github.com/FIWARE/context.Orion-LD/blob/develop/doc/manuals-ld/installation-guide-docker.md)

## Application profiles
- <b>*Default*</b>: this profile is only used to execute test during the CI/CD pipeline.
- <b>DEV</b>: this profile is used to execute the application in a Docker container.
- <b>TEST</b>: this profile is used to execute the application in a pre-production environment.
- <b>PROD</b>: this profile is used to execute the application in a production environment.

## API references (DEV environment)
- Swagger: http://localhost:8080/swagger-ui.html
- OpenAPI: http://localhost:8080/api-docs
## Installing
- Clone Blockchain Connector project and Alastria Red T to your local machine.
  ```git clone https://dev.azure.com/in2Dome/DOME/_git/in2-dome-blockchain_connector_orionld_interface```
  ```git clone https://dev.azure.com/in2Dome/DOME/_git/in2-test-alastria_red_t```
- Open/Run Docker Desktop
- Navigate to the root folder of the in2-test-alastria_red_t and execute the docker-compose. This will execute the Alastria Red T that consists in 6 local nodes (1 boot, 1 regular, and 4 validators).
  ```cd in2-test-alastria_red_t```
  ```docker-compose up -d```
- Navigate to the root folder of the in2-dome-blockchain_connector and build the docker image. This will execute the Blockchain Connector Solution.
  ```cd in2-dome-blockchain-connector-orionld-interface```
  ```docker build -t blk-conn-orionld-intf  . ```
- Navigate to the root folder of the in2-dome-iac/blockchain-connector and execute the docker-compose. This will execute the Blockchain Connector Solution, the Orion Context Broker, and the MongoDB linked to Context Broker
  ```cd in2-dome-iac/blockchain-connector```
  ```docker-compose up -d```




