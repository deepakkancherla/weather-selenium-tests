# Test catalog

## How to read this document

Each test has a permanent ID. **Smoke** tests run after every deployment. **Regression** tests run nightly and before releases. Status is initially **Planned** until code is implemented and running in CI.

## Section 1: Account registration

### REG-001 — Create an account with valid information

- Priority: Critical
- Suite: Smoke, Authentication
- Status: Planned
- Given: The visitor is not signed in and the email is not registered.
- When: The visitor enters a valid email, enters an acceptable password, confirms it, and submits the form.
- Then: The account is created and the user reaches the signed-in home page.

### REG-002 — Reject an email that is already registered

- Priority: High
- Suite: Regression, Authentication
- Status: Planned
- Given: An account already exists for the email address.
- When: The visitor tries to register with that email.
- Then: The account is not duplicated and an understandable message is shown.

### REG-003 — Validate an invalid email address

- Priority: Medium
- Suite: Regression, Authentication
- Status: Planned
- Given: The registration page is open.
- When: The visitor enters incorrectly formatted email text.
- Then: Registration does not proceed and the email problem is explained.

### REG-004 — Validate password requirements

- Priority: High
- Suite: Regression, Authentication
- Status: Planned
- Given: The registration page is open.
- When: The visitor enters a password that does not meet the documented rule.
- Then: Registration does not proceed and the password rule is explained.

### REG-005 — Require matching password confirmation

- Priority: Medium
- Suite: Regression, Authentication
- Status: Planned
- Given: The registration page is open.
- When: Password and confirmation are different.
- Then: Registration does not proceed and the mismatch is explained.

## Section 2: Login and access

### AUTH-001 — Log in with valid credentials

- Priority: Critical
- Suite: Smoke, Authentication
- Status: Planned
- Given: A registered user is signed out.
- When: The user enters the correct email and password.
- Then: The signed-in home page appears.

### AUTH-002 — Reject an incorrect password

- Priority: High
- Suite: Regression, Authentication
- Status: Planned
- Given: A registered user is signed out.
- When: The user enters the correct email and an incorrect password.
- Then: Access is denied without revealing sensitive account information.

### AUTH-003 — Require mandatory login fields

- Priority: Medium
- Suite: Regression, Authentication
- Status: Planned
- Given: The login page is open.
- When: The user submits without completing the required fields.
- Then: Login does not proceed and required fields are identified.

### AUTH-004 — Protect signed-in pages from signed-out visitors

- Priority: Critical
- Suite: Smoke, Authentication
- Status: Planned
- Given: The visitor is signed out.
- When: The visitor opens a protected page URL directly.
- Then: The visitor is sent to login and protected information is not displayed.

### AUTH-005 — Log out

- Priority: Critical
- Suite: Smoke, Authentication
- Status: Planned
- Given: A user is signed in.
- When: The user chooses to log out.
- Then: The login page appears and protected pages can no longer be accessed without signing in again.

## Section 3: City search and weather

### WEATHER-001 — Search for a valid city

- Priority: Critical
- Suite: Smoke, Weather
- Status: Planned
- Given: A user is signed in and predictable weather data is available.
- When: The user searches for a known city.
- Then: The correct city, current conditions, unit, and forecast are displayed.

### WEATHER-002 — Select the intended city when names are similar

- Priority: High
- Suite: Regression, Weather
- Status: Planned
- Given: More than one location matches the search text.
- When: The user chooses one result.
- Then: Weather is shown for the selected city, region, and country.

### WEATHER-003 — Handle a city with no result

- Priority: Medium
- Suite: Regression, Weather
- Status: Planned
- Given: A user is signed in.
- When: The search text has no matching location.
- Then: A clear empty-state message appears and no old result is presented as the new result.

### WEATHER-004 — Require search input

- Priority: Medium
- Suite: Regression, Weather
- Status: Planned
- Given: The search control is empty.
- When: The user attempts to search.
- Then: No request is submitted and the required input is identified.

### WEATHER-005 — Show progress while weather loads

- Priority: Low
- Suite: Regression, Weather
- Status: Planned
- Given: A weather response is deliberately delayed in the test environment.
- When: The user starts a search.
- Then: A loading indication is shown and is removed when the result appears.

### WEATHER-006 — Recover from a weather-service error

- Priority: High
- Suite: Regression, Weather
- Status: Planned
- Given: The test weather provider returns a controlled error.
- When: The user searches for a city.
- Then: The application explains that weather is temporarily unavailable and allows another attempt.

## Section 4: Favorite cities

### FAV-001 — Save a city as a favorite

- Priority: Critical
- Suite: Smoke, Favorites
- Status: Planned
- Given: A signed-in user has searched for a city that is not already saved.
- When: The user chooses to save it.
- Then: The city appears once in the user's favorites.

### FAV-002 — Prevent duplicate favorites

- Priority: Medium
- Suite: Regression, Favorites
- Status: Planned
- Given: The city is already a favorite.
- When: The user attempts to save the same city again.
- Then: Only one favorite entry exists.

### FAV-003 — Retain favorites after a new login

- Priority: High
- Suite: Regression, Favorites
- Status: Planned
- Given: A user has saved a favorite and logged out.
- When: The same user logs in again.
- Then: The previously saved city is still visible.

### FAV-004 — Remove a favorite

- Priority: High
- Suite: Smoke, Favorites
- Status: Planned
- Given: A signed-in user has a saved favorite.
- When: The user removes it and confirms if confirmation is required.
- Then: The city disappears from favorites and remains removed after the page is refreshed.

### FAV-005 — Keep each user's favorites private

- Priority: Critical
- Suite: Regression, Favorites
- Status: Planned
- Given: Two users have different saved favorites.
- When: Each user signs in separately.
- Then: Each user sees only their own favorites.

### FAV-006 — Show an empty favorites state

- Priority: Low
- Suite: Regression, Favorites
- Status: Planned
- Given: A signed-in user has no favorites.
- When: The favorites area is opened.
- Then: A helpful empty-state message is displayed.

## Section 5: Session and general usability

### SESSION-001 — Keep the user signed in after refresh

- Priority: High
- Suite: Regression
- Status: Planned
- Given: A user is signed in.
- When: The browser refreshes the page.
- Then: The user remains signed in and can continue.

### SESSION-002 — Handle an expired session safely

- Priority: High
- Suite: Regression
- Status: Planned
- Given: The user's session has expired in a controlled test setup.
- When: The user attempts a protected action.
- Then: The user is asked to sign in again and no protected action is completed.

### UI-001 — Use the application with the keyboard for primary flows

- Priority: Medium
- Suite: Regression
- Status: Planned
- Given: A visitor or user uses keyboard navigation.
- When: Registration, login, search, and logout controls are used without a mouse.
- Then: Focus is visible, the order is logical, and each primary action can be completed.

### UI-002 — Display an understandable unexpected-error state

- Priority: Medium
- Suite: Regression
- Status: Planned
- Given: A controlled unexpected error occurs.
- When: The affected page is displayed.
- Then: The user sees a helpful message and can navigate or retry without seeing technical details.

## Updating this catalog

When behavior changes:

1. Find all affected test IDs.
2. Update the plain-language steps and expected result.
3. Update priority or suite if risk changed.
4. Update the Selenium implementation in the same automation pull request.
5. Update the traceability matrix.
6. Retain removed scenarios in Git history and mark them **Retired** before deletion from active execution.

