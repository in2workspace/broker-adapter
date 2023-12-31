# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.0](https://github.com/in2workspace/broker-adapter/releases/tag/v2.0.0) - 2023-12-22
### Added
- New MessageUtils.
- New specific Global Error Handler functions.
- New Postman test-suite for the new functionalities.
- New Prometheus metrics.
- New Checkstyle verification.
- New Swagger documentation.
- New OWASP dependency check - static analysis.
- Support for GitHub Actions for CI/CD.
- Support for SonarCloud for code quality.
### Changed
- New version on endpoints - v2.
- Migrated to WebFlux and reactive programming.
- Summarized the controllers and service classes - Entities and Subscriptions.
- Changed endpoints to be more RESTFul.
### Fixed
- Fixed the bug in the notification process.

## [1.1.0](https://github.com/in2workspace/broker-adapter/releases/tag/v1.1.0) - 2023-12-08
### Added
- Add support for GitHub Actions.

## [1.0.1](https://github.com/in2workspace/broker-adapter/releases/tag/v1.0.1) - 2023-12-01
### Changed
- Fix default initialization of RetrievalInfoContentTypeDTO attribute.
- Update the README.md file with the new version.

### Fixed
- Fix bug in the subscription.

## [1.0.0](https://github.com/in2workspace/broker-adapter/releases/tag/v1.0.0) - Release
### Added
 - Publish entities to a given Context Broker.
 - Get entities from a given Context Broker.
 - Update entities from a given Context Broker.
 - Delete entities from a given Context Broker.
 - Make a subscription for a topic to the Context Broker.
 - Update a subscription for a topic to the Context Broker.
