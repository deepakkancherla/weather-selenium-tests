# Traceability matrix

This table helps non-technical readers see how user needs connect to test cases. **Partial** means at least one listed case is automated and at least one remains planned.

| User need | Test cases | Primary suite | Current coverage |
|---|---|---|---|
| A visitor can create an account | REG-001 to REG-005 | Authentication | Partial: REG-001, REG-003 to REG-005 automated |
| A registered user can sign in | AUTH-001 to AUTH-003 | Authentication | Automated |
| Signed-out visitors cannot see private pages | AUTH-004 | Smoke | Planned |
| A user can securely sign out | AUTH-005 | Smoke | Automated |
| A user can find weather for a city | WEATHER-001 to WEATHER-004 | Weather | Partial: WEATHER-001 and WEATHER-004 automated |
| Loading and service problems are understandable | WEATHER-005, WEATHER-006, UI-002 | Weather/Regression | Planned |
| A user can save and remove favorite cities | FAV-001, FAV-002, FAV-004, FAV-006 | Favorites | Partial: FAV-001, FAV-004, FAV-006 automated |
| Favorites remain available later | FAV-003 | Favorites | Automated |
| Users cannot see another user's favorites | FAV-005 | Regression | Planned |
| Sessions behave safely across refresh and expiry | SESSION-001, SESSION-002 | Regression | Partial: SESSION-001 automated |
| Primary journeys support keyboard use | UI-001 | Regression | Planned |

## Updating traceability

When a user need is added, changed, or removed:

1. Update its plain-language description.
2. Add or update the relevant test IDs in the catalog.
3. Update this table in the same pull request.
4. Change coverage to **Automated** only after the tests run in the intended pipeline.
