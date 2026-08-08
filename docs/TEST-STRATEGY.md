# Test strategy

## Plain-language goal

The tests answer a simple question: can a real user complete the application's most important tasks in a supported browser?

The suite does not attempt to prove every technical detail through the browser. Application code should also have fast unit, component, API, and security-rule tests in the application repository.

## In scope

- Account registration using email and password
- Login and logout
- Form validation and understandable error messages
- City search
- Weather-result display
- Empty, loading, and external-service error states
- Adding, retaining, and removing favorite cities
- Access control for signed-out users
- Basic Chrome execution in CI
- Capturing useful evidence on failure

## Out of scope initially

- Google, Facebook, or other social login automation
- Password-reset email delivery
- Pixel-perfect visual testing
- Load and performance testing
- Penetration testing
- Mobile native applications
- Destructive production testing
- Exhaustive browser combinations

These items can be added later with their own requirements and tools.

## Coverage levels

| Level | Location | Purpose |
|---|---|---|
| Unit/component | Application repository | Verify small pieces quickly and precisely |
| API/contract | Application repository | Verify Firebase and weather-data mappings |
| Selenium smoke | This repository | Prove critical journeys after deployment |
| Selenium regression | This repository | Cover broader user behavior on a schedule |

Keeping most technical checks below the browser level makes the pipeline faster and easier to diagnose.

## Priorities

- **Critical:** failure prevents normal use or deployment promotion.
- **High:** important feature is broken but another part of the app remains usable.
- **Medium:** incorrect validation, empty state, or secondary behavior.
- **Low:** minor usability or compatibility scenario.

## Browser plan

1. Use Chrome for every pull-request and deployment smoke run.
2. Add Firefox after the Chrome suite is stable.
3. Run the browser matrix weekly or before important releases.
4. Add other browsers only when a real support requirement exists.

## External weather data

Live weather changes constantly and the free service has no uptime guarantee. Most functional tests will use predictable stubbed responses supplied through the application test configuration. A small, separately identified integration check may call the real Open-Meteo service.

Tests may verify that a temperature, unit, city, and forecast are displayed. They must not expect a hard-coded live temperature.

## Entry criteria

Testing can begin when:

- A testable deployment or local application is available.
- Required acceptance criteria are documented.
- Stable test IDs exist for important controls.
- Test credentials or test-data APIs are available.
- The application version can be identified.

## Exit criteria

A deployment can progress when:

- All required smoke tests pass.
- No unresolved critical defect exists.
- Failures have enough evidence to investigate.
- The result records the application and automation versions.
- Any approved exception is documented with an owner and expiry date.

## Measures to track

- Pass rate by suite
- Number of product defects found
- Number and age of flaky tests
- Average smoke-suite duration
- Time required to identify a failure cause
- Percentage of critical journeys automated

Pass rate alone is not a measure of product quality; skipped and unreliable tests must be visible.

