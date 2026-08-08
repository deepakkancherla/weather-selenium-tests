# Test-data guide

## Plain-language principle

Every test should start from a known situation and leave the environment clean. One test must not depend on another test having run first.

## Data categories

### Fixed reference data

Examples are city names, expected units, validation boundaries, and controlled weather responses. These can be kept as reviewed JSON resources or Java builders because they contain no secrets or personal data.

### Generated user data

Accounts should use unique, recognizable addresses, for example:

```text
selenium+AUTH-001+<run-id>@example.test
```

The run ID makes parallel execution safe and makes abandoned test data identifiable.

### Secrets

Passwords, service credentials, tokens, and cloud configuration that grants access must be stored in CI secrets or approved local environment variables. They must never appear in Git, reports, screenshots, or console logs.

## Setup order

1. Generate a unique run ID.
2. Create the required user through a test-support API, Firebase emulator, or approved administration API.
3. Create only the favorites needed by the test.
4. Open the browser and perform the behavior under test.
5. Capture evidence if the test fails.
6. Delete the user's favorites and account through the setup API.
7. Record cleanup failures separately so hidden data buildup is visible.

Selenium should not navigate through unrelated screens to prepare a test. Registration is performed through the UI only in registration tests.

## Local and CI data

Firebase Auth and Firestore emulators provide disposable data. Each CI job should start from a clean emulator state and may load a small approved baseline fixture.

## Deployed test-environment data

- Use a dedicated non-production Firebase project.
- Prefix or tag all generated records with the automation run ID.
- Use least-privilege credentials for setup and cleanup.
- Run scheduled cleanup for abandoned automation records.
- Never reuse a human employee's account.

## Production

The functional suite must not create, modify, or delete production data. A future production smoke check should be read-only unless explicit safeguards and approval are designed.

## External weather fixtures

Controlled fixtures should cover:

- A successful city and weather response
- Multiple cities with the same name
- No matching city
- A delayed response
- Service unavailable or timeout
- Missing optional weather fields

Live conditions change, so a Selenium test must not assert an exact live temperature or forecast.

## Data-change checklist

- [ ] Test-data format matches the application contract.
- [ ] Existing fixtures are still meaningful.
- [ ] New boundary or error data has been added where required.
- [ ] Setup and cleanup both support the new schema.
- [ ] Parallel tests cannot share or overwrite records.
- [ ] Logs and reports do not expose secrets.

