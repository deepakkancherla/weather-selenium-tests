# Test failure guide

## Purpose

A failed automated test is evidence, not an automatic conclusion that the application is defective. This guide explains how to identify the likely cause consistently.

## First checks

1. Read the test ID and plain-language expected result.
2. Confirm the application and automation commit versions.
3. Review the screenshot, error message, browser log, and application log.
4. Confirm that setup completed and the correct environment was tested.
5. Compare the observed behavior with the accepted requirement.

## Failure categories

### Product defect

The application does not follow the accepted behavior. Record a defect with the test ID, versions, environment, steps, and evidence. Do not weaken the test to make the pipeline pass.

### Automation defect

The application is correct but the test uses an incorrect locator, wait, action, assertion, or assumption. Update the smallest relevant framework layer and add evidence that the corrected test detects both passing and failing behavior appropriately.

### Test-data defect

Setup produced missing, shared, invalid, or stale data. Correct the builder, fixture, setup, or cleanup mechanism. Check whether other tests use the same data path.

### Environment defect

The application, browser, emulator, network, certificate, or configuration was unavailable or incorrect. Record the incident separately from functional product failures.

### External-service defect

The live weather provider was unavailable or changed unexpectedly. Controlled CI scenarios should use stubs. A live-integration failure should not be confused with failure of a stubbed functional test.

### Requirement mismatch

The team has different understandings of correct behavior. Pause behavioral test changes until the product owner or requester clarifies and records the intended result.

## Retry policy

A single retry may be allowed only for an identified temporary infrastructure category. The original failure must remain visible. Functional assertion failures are not automatically retried.

Repeated pass-on-retry results indicate a flaky test or environment problem that needs tracked corrective work.

## Minimum defect information

- Test-case ID and title
- Application version
- Automation version
- Environment and browser
- Time and pipeline run ID
- Expected and actual behavior
- Screenshot and relevant logs
- Test data identifier, excluding secrets
- Reproduction status

