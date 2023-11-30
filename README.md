# Broker Adapter

## Introduction
The Broker Adapter is one of the components used by any service that wishes to interact with Broker. It is a RESTful API that offers a wide range of endpoints for interacting with various operations supported by NGSI-LD. Additionally, it provides a subscription service that can configure subscriptions on a given server, allowing the creation of multiple subscriptions as needed.


#### Technologies
The Broker Adapter component is constructed using the following technologies:

- Java 17: The core programming language for building the connector.
- Spring Boot 3.x: A framework that simplifies the development of Java-based applications, providing a robust and efficient platform.
- Gradle: A build automation tool that manages the project's dependencies and facilitates the build process.

## Functionalities
The key functionalities of the Broker Adapter include:

- **Interaction with Context Broker:** The adapter provides endpoints for fetching, persisting, updating and deleting data from the Context Broker.

- **CRUD Operations:** Users can perform various CRUD operations, such as persist (POST), update (PATCH), retrieve (GET) and delete (DELETE) entities.
  - **Create:** Users can create new entities, specifying the entity type and attributes.
  - **Read:** Users can retrieve entities, specifying the entity ID.
  - **Update:** Users can update entities, specifying the entity type, ID and attributes.
  - **Delete:** Users can delete entities, specifying the entity ID.

- **Manage subscriptions to New Entities:** Enables one or various subscriptions to new entities, allowing the user to specify the entity types to which they wish to subscribe. Also, the user can specify where they wish to receive notifications for new entities.

## Installation
### Prerequisites
To install the blockchain connector, the following prerequisites are required:
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

## `mkt-broker-adapter`
- **Description:** The main, component providing simplified interactions with the Context Broker.
- **Image:** `in2kizuna/broker-adapter:v1.0.0-SNAPSHOT`
- **Ports:** `8083:8080`
- **Volumes:** Binds `./marketplace1-config.yml` to `/src/main/resources/external-config.yml`.
- **Links:** Connected to `mkt-context-broker`.
- **Networks:** Connected to `local_network`.

**Docker Compose Configuration:**
```yaml
mkt-broker-adapter:
  container_name: mkt-broker-adapter
  image: in2kizuna/broker-adapter:v1.0.0-SNAPSHOT
  ports:
    - "8083:8080"
  volumes:
    - ./marketplace-config.yml:/src/main/resources/external-config.yml
  links:
    - mkt-context-broker
  networks:
    - local_network
```

## `mkt-context-broker`
- **Description:** Manages context information, facilitating communication between components.
- **Image:** `fiware/orion-ld:latest`
- **Command:** Configured with MongoDB host and port.
- **Ports:** `1027:1026`
- **Links:** Connected to `mkt-mongo`.
- **Networks:** Connected to `local_network`.

**Docker Compose Configuration:**
```yaml
mkt-context-broker:
  container_name: mkt-context-broker
  image: fiware/orion-ld:latest
  restart: always
  command: "-dbhost mkt-mongo -port 1026"
  ports:
    - "1027:1026"
  links:
    - mkt-mongo
  networks:
    - local_network
```

## Configuration
In addition to the `docker-compose.yml` file, it is necessary to create a configuration file that includes, at a minimum, the following fields for proper functionality. The example configuration provided aligns with the previously defined components in the Docker Compose setup.

**Example `marketplace-config.yml`:**

```yaml
# Context Broker Configuration
broker:
  externalDomain: https://<example.com>/orion-ld
  internalDomain: http://mkt-context-broker:1026
```
This section configures the Context Broker with both external and internal domains. The external domain is where the Context Broker is accessible from outside, and the internal domain is its address within the environment.

## Usage

After implementing both the `docker-compose.yml` and configuration files, the next step is to compose the Docker services with the command:

`docker-compose up -d`

Subsequently, within Docker, wait for all components to initialize properly. Once everything has initialized successfully (in case of any issues, double-check the endpoint addresses in the configuration file), the Broker Adapter will be ready to use.

Users can utilize tools like POSTMAN for entity/subscription management, ensuring that the entity type matches the type established for subscription beforehand.

### Broker Adapter Endpoints

#### 1. Delete Entity

- **Endpoint:** `http://<domain>/api/v1/delete/<id>` (DELETE)
- **Description:** Deletes the entity with the specified ID.

#### 2. Subscribe to Entities

- **Endpoint:** `http://<domain>/api/v1/subscribe` (POST)
- **Description:** Subscribes to specified entity types. Notifications will be redirected to the provided URI.
- **Request Body Example:**
  ```json
  {
    "id": "example_subscription_id",
    "type": "Subscription",
    "notification-endpoint-uri": "http://example.com/notifications",
    "entities": ["Type1", "Type2"]
  }
  ```

#### 3. Retrieve Entity by ID

- **Endpoint:** `http://<domain>/api/v1/entities/<id>` (GET)
- **Description:** Retrieves the entity with the specified ID.

#### 4. Publish Entity

- **Endpoint:** `http://<domain>/api/v1/publish` (POST)
- **Description:** Publishes the specified entity provided in the request body.

#### 5. Update Entity by ID

- **Endpoint:** `http://<domain>/api/v1/update/<id>` (PATCH)
- **Description:** Updates the entity with the specified ID, replacing it with the body provided in the request.

## Contribution

## License


## Project/Component Status
This project is in version 1.0.0 of the MVP (Minimum Viable Product) for the Broker Adapter at 12/04/2023.

## Contact

For any inquiries or further information, feel free to reach out to us:

- **Email:** [info@in2.es](mailto:info@in2.es)
- **Name:** IN2, Ingeniería de la Información
- **Website:** [https://in2.es](https://in2.es)

## Acknowledgments


## Creation Date and Last Update
This project was created on July 07, 2023, and last updated on December 4, 2023.