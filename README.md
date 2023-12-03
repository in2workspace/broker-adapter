# Broker Adapter

## Introduction
The Broker Adapter is one of the components used by any service that wishes to interact with Broker. It is a RESTful API that offers a wide range of endpoints for interacting with various operations supported by NGSI-LD. Additionally, it provides a subscription service that can configure subscriptions on a given server, allowing the creation of multiple subscriptions as needed.


#### Technologies
The Broker Adapter component is constructed using the following technologies:

- Java 17: The core programming language for building the connector.
- Spring Boot 3.x: A framework that simplifies the development of Java-based applications, providing a robust and efficient platform.
- Gradle: A build automation tool that manages the project's dependencies and facilitates the build process.
- Docker: A containerization platform that allows the creation of isolated environments for the deployment of applications.
- Prometheus: A monitoring tool that collects metrics from monitored targets by scraping metrics HTTP endpoints on these targets.
- Documentation: A tool that generates documentation for the API endpoints. OpenAPI 3.0 and Swagger are used for this purpose.
- WebFlux: A non-blocking reactive framework that provides a functional programming model for building asynchronous applications.

## Functionalities
The key functionalities of the Broker Adapter include:

- **Interaction with Context Broker:** The adapter provides endpoints for fetching, persisting, updating and deleting Entities to and from the Context Broker.

- **CRUD Operations:** Users can perform various CRUD operations, such as persist (POST), update (PATCH), retrieve (GET) and delete (DELETE) entities.
  - **Create:** Users can create new entities, specifying the entity type and attributes.
  - **Read:** Users can retrieve entities, specifying the entity ID.
  - **Update:** Users can update entities, specifying the entity type, ID and attributes.
  - **Delete:** Users can delete entities, specifying the entity ID.

- **Manage subscriptions to New Entities:** Enables one or various subscriptions to new entities, allowing the user to specify the entity types to which they wish to subscribe. Also, the user can specify where they wish to receive notifications for new entities.

## Installation

### Prerequisites
To install the blockchain connector, the following prerequisites are required:

- Git
- Docker or Docker Desktop

### Image installation / Component setup

#### Introduction

For the installation of the component and its remaining dependencies, we will create a docker-compose file that allows us to configure containers from the images needed to install our entire environment. This setup ensures the proper functioning of the Blockchain Connector along with its dependencies, which will also be installed.


#### Component Overview

In the docker-compose.yml file, the definition of services is structured collectively under the services section, following the specified header in the document:

```yaml
version: "3.8"
services:
  # Following components will be defined here
```

## `broker-adapter`
- **Description:** The main, component providing simplified interactions with the Context Broker.
- **Image:** `in2kizuna/broker-adapter:v1.0.0`
- **Ports:** `8083:8080`
- **Links:** Connected to `Context-Broker`.
- **Networks:** Connected to `local_network`.

**Docker Compose Configuration:**
```yaml
mkt-broker-adapter:
  container_name: broker-adapter
  image: in2kizuna/broker-adapter:v1.0.0
  hostname: broker-adapter
  ports:
    - "8083:8080"
  environment:
      - "BROKER_EXTERNALDOMAIN=https://example.com/scorpio/ld"
      - "BROKER_INTERNALDOMAIN=http://scorpio:9090"
      - "BROKER-ADAPTER_OPENAPI_URL=http://localhost:8080"
  links:
    - scorpio
```

## `mkt-context-broker`
For now, we are working with the implementation of the [Scorpio](https://github.com/ScorpioBroker/ScorpioBroker) as a Context Broker. The Scorpio is a Context Broker that implements the NGSI-LD API specification. It is a component of the FIWARE platform, developed by Red Hat, which is used to manage context information, facilitating communication between components.

We are applied the following configuration to the Scorpio Context Broker: [java based all-in-one runner](https://raw.githubusercontent.com/ScorpioBroker/ScorpioBroker/development-quarkus/compose-files/docker-compose-java-aaio-kafka.yml)

## Configuration
In addition to the `docker-compose.yml` file, it is necessary to implement the required configuration for proper functionality. You can add this configuration to the `docker-compose.yml` file or create a separate file for it. In this case, we will create a `broker-adapter-custom-config.yml` file.

**Example `broker-adapter-custom-config.yml`:**

```yaml
# Context Broker Configuration
broker:
  externalDomain: https://<example.com>/orion-ld
  internalDomain: http://mkt-context-broker:1026
```
This section configures the Context Broker with both external and internal domains. The external domain is where the Context Broker is accessible from outside, and the internal domain is its address within the environment.

## Usage

### Starting the Broker Adapter
Run the docker-compose command to start the Broker Adapter:
``` bash
docker-compose up -d
```

Subsequently, within Docker, wait for all components to initialize properly. Once everything has initialized successfully (in case of any issues, double-check the endpoint addresses in the configuration file), the Broker Adapter will be ready to use.

To get a good collection of Broker Adapter operations and examples we recommend to have a look at our [test-suite](tests/api-test.json). It is Postman based.

### Broker Adapter Swagger Documentation

The Broker Adapter provides a Swagger documentation page that can be accessed at `http://<domain>/swagger-ui.html`. This page provides a detailed description of the API endpoints, including the request and response bodies, as well as the possible responses.

## Contribution

## License

You can find the license information in the [LICENSE](LICENSE.md) file.

## Project/Component Status
This project is in version 1.0.0 of the MVP (Minimum Viable Product) for the Broker Adapter at 2023/12/04.

## Contact

If you have any questions or require additional information, please don't hesitate to contact us:

- IN2, Ingeniería de la Información
- [www.in2.es](https://in2.es) 
- [info@in2.es](mailto:info@in2.es)

## Acknowledgments


## Creation Date and Last Update
This project was created on July 07, 2023, and last updated on December 4, 2023.