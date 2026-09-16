# Cucumber SOAP REST Assured Framework

BDD test automation framework for a SOAP/XML API, built with Rest Assured, Cucumber and TestNG.

It currently covers the Users, Products and Orders services of the `SandboxService` SOAP endpoint. A Rewards service is next on the list.

## Stack

- Java 17
- Maven
- Rest Assured (XML)
- Cucumber 7 + TestNG
- Allure reporting
- java-faker for test data

## Project layout

```
src/main/java/api/base        - shared request builder / sender
src/main/java/api/endpoints   - one class per SOAP operation
src/main/java/dataBuilders    - builds request XML from templates
src/main/java/dto             - request data holders
src/main/java/utils           - env loading, faker, date, xml helpers
src/test/java/stepDefinitions - Cucumber step definitions
src/test/java/runners         - TestNG/Cucumber runners (Sanity, Smoke, Regression)
src/test/resources/features   - .feature files
src/test/resources/requestXmls- XML request templates
envs/                         - environment config (.env.qa)
```

## Setup

1. Install JDK 17 and Maven.
2. Copy the env template and adjust if needed:

```
cp envs/.env.qa.example envs/.env.qa
```

`envs/.env.qa` is git-ignored, so local values never get committed.

## Running tests

All suites are wired into `src/test/resources/testng.xml`. Run the full thing with:

```
mvn test
```

Run a single runner:

```
mvn test -Dtest=RegressionTests
mvn test -Dtest=SanityTests
mvn test -Dtest=SmokeTests
```

### Filtering by tag

Each scenario is tagged by module (`@user`, `@product`, `@order`) and by suite (`@sanity`, `@smoke`, `@regression`). Override the tags a runner picks up with `cucumber.filter.tags`:

```
mvn test -Dtest=RegressionTests "-Dcucumber.filter.tags=@regression and @order"
mvn test -Dtest=RegressionTests "-Dcucumber.filter.tags=@sanity and @user"
```

### Reports

- Allure results are written to `allure-results/`. View them with `allure serve allure-results` (requires the Allure CLI).
- TestNG's own HTML report is written to `test-output/`.

## CI

`.github/workflows/tests.yml` runs the suite in GitHub Actions. It's manual-trigger only for now (Actions tab → SOAP API Tests → Run workflow), with two inputs:

- **suite** - `sanity` or `regression`
- **module** - `all`, `user`, `product` or `order`

Those combine into a `cucumber.filter.tags` expression, so e.g. suite=`sanity` + module=`order` runs only sanity-tagged order scenarios. Results are uploaded as workflow artifacts (Allure results + TestNG report).

## Authentication

`BaseApi` has `setBasicAuth(username, password)` and `setBearerAuth(token)` available to any endpoint class that needs them - neither is wired up yet since the current services don't require auth. The Rewards API (in progress) will be the first to use one of these, most likely bearer token, once its auth flow is confirmed.
