# Change impact guide

## Purpose

This guide answers: “Something changed in the application—what else must we check?”

## Quick reference

| Application change | Test cases to review | Automation area to review | Data/configuration to review |
|---|---|---|---|
| Registration fields | REG-* | Registration page object | User builder and validation inputs |
| Password rules | REG-004, REG-005, AUTH-* | Registration/login actions | Valid and invalid password sets |
| Login form or flow | AUTH-* | Login page object and session helper | Test users and authentication setup |
| Route protection | AUTH-004, AUTH-005, SESSION-* | Navigation/session helpers | Base URL and session fixtures |
| City search input/results | WEATHER-001 to WEATHER-004 | Search component/page object | City fixtures and geocoding stubs |
| Weather fields or units | WEATHER-001, WEATHER-005, WEATHER-006 | Weather result component | Weather API fixtures |
| Favorite controls | FAV-001, FAV-002, FAV-004 | Favorites component/page object | Favorite builders and cleanup |
| Favorite storage/schema | FAV-* | Test-support API/database client | Seed and cleanup formats |
| User privacy rules | AUTH-004, FAV-005 | Login and favorites flows | Two-user datasets and Firebase rules |
| Error messages | Relevant negative cases | Assertion text or message component | Error fixtures/stubs |
| Test IDs/selectors | All using changed controls | Relevant page object only | None unless behavior also changed |
| Deployment URL | Smoke tests | Runtime configuration | Environment configuration/secrets |
| Browser support | Broad regression | Driver factory/capabilities | Pipeline browser matrix |

## Step-by-step review

### 1. Understand the user-visible change

Write one sentence describing what a user could do before and what the user can do afterward. If this cannot be explained clearly, the requirement needs clarification before tests are changed.

### 2. Find affected test cases

Search the test catalog by feature section and permanent ID. Consider successful behavior, validation, errors, permissions, saved data, and logout/session behavior.

### 3. Identify the smallest automation layer to change

- Locator changed only: update the page object.
- Input values changed: update the test-data builder or JSON data.
- User-visible result changed: update the test assertion and test catalog.
- Workflow changed: update the test steps and page actions.
- Database/API changed: update setup and cleanup clients, then verify isolation.
- Environment changed: update runtime configuration, not test code.

### 4. Decide whether this is a product defect or intended behavior

A failing test should not automatically be rewritten. Compare the result with the accepted requirement. Change the test only when the required behavior intentionally changed or the previous test was incorrect.

### 5. Prove compatibility

Run the affected automation branch against the matching application deployment. Record both commit SHAs in the result.

### 6. Update documentation

Update the test catalog, traceability table, and any test-data or pipeline instructions affected by the change.

## Pull-request checklist

- [ ] Work-item or requirement is linked.
- [ ] Affected test IDs are listed.
- [ ] Intended behavior is distinguished from a defect.
- [ ] Page objects use stable test IDs.
- [ ] Test data remains independent and disposable.
- [ ] No secret or real-user information is committed.
- [ ] Relevant smoke tests pass against the intended application version.
- [ ] Failure evidence was reviewed.
- [ ] Documentation and traceability are current.

