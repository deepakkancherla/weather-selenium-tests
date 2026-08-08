# Weather Favorites Selenium tests

## Purpose

This repository contains the end-to-end browser tests for the Weather Favorites application. It is independent of the application source repository so it can follow a separate QA review, release, and execution process.

The documentation is written so product owners, managers, developers, and testers can understand what is covered without reading Java code.

## What this repository will contain

- Human-readable test cases with permanent IDs
- Java, Selenium, JUnit 5, and Maven test code
- Page objects and reusable page components
- Test-data builders and cleanup utilities
- Environment configuration without secrets
- Smoke and regression suite definitions
- Pipeline workflows
- Test reports, screenshots, browser logs, and other failure evidence

## Documentation map

| Document | Audience | Purpose |
|---|---|---|
| [Test strategy](docs/TEST-STRATEGY.md) | Everyone | Scope, priorities, risks, and testing approach |
| [Test catalog](docs/TEST-CATALOG.md) | Everyone | Every currently planned test, organized by user flow |
| [Change impact guide](docs/CHANGE-IMPACT-GUIDE.md) | Developers and QA | What must be checked or updated after each type of change |
| [Test-data guide](docs/TEST-DATA-GUIDE.md) | QA and developers | How users and favorites are safely created and removed |
| [Pipeline guide](docs/PIPELINE-GUIDE.md) | Everyone | When tests run and what happens after a pass or failure |
| [Failure guide](docs/FAILURE-GUIDE.md) | Everyone | How to classify and investigate failed tests |
| [Traceability matrix](docs/TRACEABILITY.md) | Product and QA | Mapping between user flows, requirements, and test cases |

## Test suites

| Suite | Meaning | Intended runtime |
|---|---|---|
| Smoke | A small set proving the most important journeys work | Every application deployment |
| Regression | All functional end-to-end scenarios | Nightly and before a release |
| Authentication | Account creation, login, validation, and logout | When authentication changes |
| Weather | Search and weather-display behavior | When API/display behavior changes |
| Favorites | Save, retain, and remove favorite cities | When persistence changes |

## Planned commands

These commands will become active after the framework is implemented:

```powershell
mvn test -DbaseUrl=http://localhost:5173 -Denvironment=local -Dbrowser=chrome -Dheadless=false
mvn test -DbaseUrl=https://test.example.com -Denvironment=test -Dbrowser=chrome -Dheadless=true -Dgroups=smoke
```

## Test-case lifecycle

Every test case has one of these states:

- **Planned:** agreed coverage that has not been automated.
- **Automated:** implemented and running in the expected pipeline.
- **Blocked:** cannot run because of a recorded dependency or defect.
- **Retired:** behavior was intentionally removed; history remains in Git.

Tests are not silently deleted. When behavior changes, the test case, implementation, and traceability entry are updated together.

## Repository rules

- Each automated test includes its test-case ID in its display name.
- Tests must not depend on execution order.
- Each test owns or safely resets its data.
- Secrets must not be committed.
- Page objects perform UI actions; test classes describe behavior and assertions.
- Fixed sleeps are prohibited; tests wait for observable conditions.
- A retry cannot be used to hide a product defect or unstable test.
- Production data must never be created, modified, or deleted by this suite.

## Current status

The repository, test strategy, and initial test catalog have been created. Test implementation has not started.

