# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.0] - unreleased
- Migrated to WebFlux and reactive programming.
- Summarized the controllers and service classes - Entities and Subscriptions.
- Changed endpoints to be more RESTFul.
- New version on endpoints - v2.
- Added new MessageUtils.
- Added new specific Global Error Handler functions.
- Added Postman test-suite for the new functionalities.
- Added Prometheus metrics.
- Added Checkstyle verification.
- Added Swagger documentation.
- Added OWASP dependency check - static analysis.
- Added GitHub Actions for CI/CD.
- Added SonarCloud for code quality.

## [1.1.0] - 2023-12-08
### Added
- Add support for GitHub Actions.

## [1.0.1] - 2023-12-01
### Changed
- Fix default initialization of RetrievalInfoContentTypeDTO attribute.
- Update the README.md file with the new version.

## [1.0.0] - 2023-11-21
- Fix bug in the subscription.

## [1.0.0] - Release
### Added
 - Publish entities to a given Context Broker.
 - Get entities from a given Context Broker.
 - Update entities from a given Context Broker.
 - Delete entities from a given Context Broker.
 - Make a subscription for a topic to the Context Broker.
 - Update a subscription for a topic to the Context Broker.

[release]:
- [v1.1.0](https://github.com/in2workspace/broker-adapter/releases/tag/v1.1.0)
- [v1.0.1](https://github.com/in2workspace/broker-adapter/releases/tag/v1.0.1)
- [v1.0.0](https://github.com/in2workspace/broker-adapter/releases/tag/v1.0.0)
