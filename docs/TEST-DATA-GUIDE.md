# Test-data guide

## What data the tests use

| Data | Source | Is it shared? | Cleanup |
|---|---|---|---|
| User email/password | Generated uniquely for one test | No | User deletes itself through Firebase Auth REST API |
| Favorite cities | Written by that signed-in user through the UI | No | Removed when the Firebase user is deleted |
| Weather/location | Application's deterministic `weatherMode=mock` response | Read-only shared fixture | None needed |
| Application URL/browser | Runtime setting | Shared configuration | None needed |

The suite never uses either owner's personal Gmail account. A generated email looks like `selenium+auth001-a1b2c3d4@example.com`. The random suffix prevents two pipeline jobs from using the same account.

## Lifecycle of one account test

1. `TestDataFactory` creates a unique email and strong password in memory.
2. Most tests create the account directly through Firebase Auth REST, because registration itself is not what those tests are measuring.
3. Registration tests create the account through the visible UI.
4. Selenium waits until the application reports that Firestore favorite loading is complete.
5. Selenium performs only the user behavior under test.
6. If the test fails, the framework saves a screenshot and page HTML.
7. `BaseUiTest` signs in through Firebase REST and calls the Firebase delete-account endpoint.
8. The browser closes even if cleanup encounters a problem.

The REST client authenticates as the disposable user. It does not use Firebase Admin or a service-account key.

## Source files

| File | Responsibility |
|---|---|
| `TestDataFactory.java` | Unique email and password format |
| `TestUser.java` | Email/password value object |
| `FirebaseTestDataClient.java` | Create, sign in, and delete disposable users |
| `BaseUiTest.java` | Track every test user and invoke cleanup |
| `TestConfig.java` | Firebase web API key and application URL |

## Rules for adding data

- Never commit a real person's email, password, access token, service account, or private key.
- Never make one test depend on data created by another test.
- Create the minimum data needed for the scenario.
- Register through the UI only when registration is the behavior being tested.
- Add cleanup at the same time as setup.
- Do not run these write tests against a production Firebase project.
- Use mock weather for functional assertions; do not expect an exact live temperature.

## If the application data changes

| Change | Update | Then run |
|---|---|---|
| Password rule | `TestDataFactory`, affected REG/AUTH cases and catalog | `authentication`, then `smoke` |
| Firebase project/API key | `FIREBASE_WEB_API_KEY` repository variable; do not hard-code a private credential | `smoke` |
| Authentication provider | `FirebaseTestDataClient` and auth page object | `authentication`, then `regression` |
| Favorite document format/security rule | Data cleanup logic if needed and all FAV cases | `favorites`, then `smoke` |
| Weather response fields | App mock fixture and weather assertions | `weather`, then `smoke` |

## Cleanup failure

If an account remains after a failed run, its `selenium+<case-id>-<random>` email makes it recognizable. Check the pipeline log for the originating case and fix cleanup before allowing repeated buildup. Do not manually delete broad collections or real-user records.
