# Pipeline guide

## Plain-language overview

The pipeline is an automatic quality gate. After the application is built and placed in a test environment, it asks this repository to run the appropriate browser tests. It then preserves the result and evidence.

## Pipeline types

### Application deployment smoke test

Runs after an application version is deployed to a test environment.

1. The application pipeline deploys a specific commit.
2. It triggers this repository with the application URL and commit SHA.
3. The Selenium pipeline starts Chrome in headless mode.
4. It creates disposable test data.
5. It runs the Smoke suite.
6. It cleans up the data.
7. It returns pass or fail and publishes evidence.
8. The application pipeline promotes only when its required quality gate passes.

### Automation pull-request validation

Runs when the framework, test cases, or test data change.

1. Compile the automation project.
2. Run framework/unit checks that do not need the application.
3. When a compatible environment is available, run affected Selenium tests.
4. Require review for test-case behavior changes.

### Nightly regression

Runs all active regression tests against the shared test environment. It reports product defects, test defects, environment problems, and external-service problems separately.

### Manual run

Allows an authorized person to select the environment, browser, suite, and application URL for troubleshooting or release validation.

## Runtime inputs

| Input | Example | Meaning |
|---|---|---|
| Base URL | `https://test.example.com` | Application deployment to test |
| Environment | `test` | Selects safe non-secret settings |
| Application SHA | `a81c23f` | Exact application version |
| Browser | `chrome` | Browser used for the run |
| Headless | `true` | Runs without a visible window in CI |
| Suite/tag | `smoke` | Tests selected for this run |
| Run ID | Pipeline-generated | Connects data, logs, and reports |

## Required result evidence

Every run should retain:

- Test result and duration
- Application commit/version
- Automation commit/version
- Environment and browser
- Screenshot on failure
- Page source on failure, with sensitive data removed
- Browser console output
- Application or emulator logs when available
- Human-readable HTML report

## Pass behavior

- Mark the quality gate successful.
- Publish the report.
- Allow the next deployment stage when all other gates also pass.

## Failure behavior

- Mark the gate failed.
- Upload evidence even when cleanup also fails.
- Do not automatically rewrite, delete, or disable the failing test.
- Follow the failure guide to identify ownership.
- Rerun only when there is evidence of an infrastructure-related interruption.

## Suggested schedule

| Trigger | Tests | Browser |
|---|---|---|
| Every test deployment | Smoke | Chrome |
| Automation pull request | Framework checks and affected tests | Chrome |
| Nightly | Full regression | Chrome |
| Weekly or pre-release | Full regression | Chrome and Firefox |

## Security

- Use the CI secret store for credentials.
- Give the pipeline access only to the test environment.
- Do not print secrets in command lines or reports.
- Restrict deployment-trigger permissions to trusted workflows.
- Review third-party pipeline actions before use and pin approved versions according to organizational policy.

