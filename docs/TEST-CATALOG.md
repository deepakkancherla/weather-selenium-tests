# Test cases by user flow

This is the business-readable inventory of browser tests. Each ID is permanent and appears in the Java method name. **Automated** means code exists and runs in GitHub Actions. **Planned** means the scenario is agreed but still needs a safe, deterministic implementation.

## 1. Account registration

| ID | What the person does | Expected result | Priority / suite | Status |
|---|---|---|---|---|
| REG-001 | Enters a new valid email, password, and matching confirmation | Account is created and the weather dashboard opens | Critical / Smoke | Automated |
| REG-002 | Tries to create an account with an email that already exists | No duplicate account; a helpful message appears | High / Authentication | Planned |
| REG-003 | Enters text that is not a valid email | Registration stops and explains the email problem | Medium / Authentication | Automated |
| REG-004 | Enters a password shorter than six characters | Registration stops and explains the password rule | High / Authentication | Automated |
| REG-005 | Enters two different passwords | Registration stops and explains that they do not match | Medium / Authentication | Automated |

## 2. Login and access

| ID | What the person does | Expected result | Priority / suite | Status |
|---|---|---|---|---|
| AUTH-001 | Signs in with a registered email and correct password | Dashboard opens | Critical / Smoke | Automated |
| AUTH-002 | Uses the right email and a wrong password | Access is denied without revealing sensitive details | High / Authentication | Automated |
| AUTH-003 | Submits the empty login form | Login stops and identifies the missing/invalid email | Medium / Authentication | Automated |
| AUTH-004 | Opens protected information while signed out | Login is shown and protected information remains hidden | Critical / Authentication | Planned; app is a single route, so a direct protected URL does not yet exist |
| AUTH-005 | Opens the profile menu and signs out | Login page returns | Critical / Smoke | Automated |

## 3. City search and weather

| ID | What the person does | Expected result | Priority / suite | Status |
|---|---|---|---|---|
| WEATHER-001 | Searches for Austin | Austin weather and a temperature appear | Critical / Smoke | Automated with mock weather |
| WEATHER-002 | Searches where several places have the same name | The selected city, region, and country are used | High / Weather | Planned; needs multiple-location fixture |
| WEATHER-003 | Searches for a place with no match | A clear no-result message appears | Medium / Weather | Planned; needs no-result fixture |
| WEATHER-004 | Submits an empty search | Message says to enter a city | Medium / Weather | Automated |
| WEATHER-005 | Searches while the provider is deliberately slow | Loading state appears and then clears | Low / Weather | Planned; needs delayed fixture |
| WEATHER-006 | Searches while the weather provider deliberately fails | Temporary-error message appears and retry remains possible | High / Weather | Planned; needs error fixture |

The automated weather tests use `?weatherMode=mock`. This confirms the UI reliably without asserting a live temperature that changes every hour.

## 4. Favorite cities

| ID | What the person does | Expected result | Priority / suite | Status |
|---|---|---|---|---|
| FAV-001 | Saves the displayed Austin weather | Austin appears once in favorites | Critical / Smoke | Automated |
| FAV-002 | Attempts to save an already saved city | Only one entry exists | Medium / Favorites | Planned |
| FAV-003 | Saves Austin, signs out, then signs back in | Austin is still saved | High / Favorites | Automated |
| FAV-004 | Removes saved Austin | Austin disappears and the empty state returns | High / Favorites | Automated |
| FAV-005 | Two different users view favorites | Each sees only their own data | Critical / Favorites | Planned |
| FAV-006 | A new account opens favorites | Helpful empty state appears | Low / Favorites | Automated |

## 5. Session and general usability

| ID | What the person does | Expected result | Priority / suite | Status |
|---|---|---|---|---|
| SESSION-001 | Refreshes while signed in | Session remains active and dashboard returns | High / Regression | Automated |
| SESSION-002 | Uses an intentionally expired session | Login is required and no protected action completes | High / Regression | Planned; needs expired-token control |
| UI-001 | Completes primary flows using only a keyboard | Focus is visible and order/actions are usable | Medium / Regression | Planned |
| UI-002 | Encounters a controlled unexpected error | Helpful recovery message appears without technical details | Medium / Regression | Planned; needs error fixture |

## Automated source map

| Section | Java class |
|---|---|
| Registration | `RegistrationTests` |
| Login/logout | `AuthenticationTests` |
| Search/weather | `WeatherSearchTests` |
| Favorites | `FavoriteTests` |
| Session | `SessionTests` |

## When a case changes

1. Confirm the product behavior was intentionally changed, rather than assuming the test is wrong.
2. Update the row above, keeping its permanent ID.
3. Update the matching Java method and, if needed, the page object or test-data helper.
4. Update `TRACEABILITY.md` if requirement coverage changed.
5. Run the affected feature suite, then Smoke.
6. Put all related updates in the same pull request so reviewers see the complete change.

