# GitHub Actions pipeline guide

The workflow is named **Selenium Tests** and lives in `.github/workflows/selenium.yml`. It runs on GitHub-hosted Linux using Java 21 and a headless browser.

## When it runs

| Trigger | Suite | Purpose |
|---|---|---|
| Push to `main` | Smoke | Quickly verify published automation changes |
| Pull request | Smoke | Prevent a broken framework change from merging |
| Every day at 08:00 UTC | Regression (all implemented tests) | Find broader regressions |
| Run workflow button | Person chooses suite/browser/URL | Investigation or release check |
| `weather-app-deployed` repository event | Smoke unless sender specifies another suite | Cross-repository deployment integration |

## What happens in one run

1. GitHub checks out this Selenium repository.
2. It installs Temurin Java 21 and restores the Maven cache.
3. It chooses the suite, browser, and deployed application URL.
4. Maven compiles the framework.
5. Selenium starts Chrome or Firefox in headless mode.
6. Tests create isolated Firebase users, use the UI, and clean the users up.
7. Maven returns success only when every selected test passes.
8. GitHub publishes a readable summary and uploads JUnit reports plus failure evidence for 14 days.

## View pipeline tests in GitHub

Repository owner `deepakkancherla` already has access.

1. Open `https://github.com/deepakkancherla/weather-selenium-tests`.
2. Select **Actions**.
3. Select **Selenium Tests** on the left.
4. Select a run to see each step and its logs.
5. On the run's Summary page, download `selenium-results-<run number>` under **Artifacts**.

An artifact contains `surefire-reports` for all tests and `evidence` screenshots/page HTML when a test fails.

## Start a manual run in GitHub

1. Open **Actions** > **Selenium Tests**.
2. Select **Run workflow**.
3. Keep branch `main`.
4. Choose a suite and browser.
5. Enter the Vercel or preview URL to test.
6. Select the green **Run workflow** button.

Use `smoke` for a quick check. Use `regression` for every implemented case. Feature choices are `registration`, `authentication`, `weather`, `favorites`, and `session`.

## Start or inspect a run from PowerShell

```powershell
gh workflow run selenium.yml --repo deepakkancherla/weather-selenium-tests -f suite=smoke -f browser=chrome -f base_url=https://weather-app-nine-vert-81.vercel.app
gh run list --repo deepakkancherla/weather-selenium-tests --workflow selenium.yml
gh run watch RUN_ID --repo deepakkancherla/weather-selenium-tests --exit-status
```

## Repository configuration

The workflow understands these GitHub Actions repository variables:

| Variable | Purpose |
|---|---|
| `TEST_BASE_URL` | Default shared test deployment used for automatic runs |
| `FIREBASE_WEB_API_KEY` | Public Firebase web configuration key used by disposable-user setup |

Repository variables are under **Settings > Secrets and variables > Actions > Variables**. Private credentials, if ever added, belong under **Secrets**, never Variables or source code.

## Trigger from the separate application repository

Cross-repository triggers require explicit credentials because GitHub does not allow one private repository's default token to start another repository automatically. The application pipeline should send a `repository_dispatch` event named `weather-app-deployed` with:

```json
{
  "event_type": "weather-app-deployed",
  "client_payload": {
    "base_url": "https://deployed-preview.example.com",
    "suite": "smoke",
    "application_sha": "the-application-commit"
  }
}
```

Use an organization-approved GitHub App or fine-grained token stored as an application-repository secret. Do not copy a personal token into either repository. Until that credential is approved, the push, pull-request, nightly, and manual triggers work independently.

## Reading pass or failure

- Green check: every selected test passed and cleanup completed.
- Red X: open **Run selected Selenium tests**, note the case ID, then download the artifact.
- Cancelled: a newer run on the same branch replaced an older one, or a person cancelled it.
- No artifact: setup failed before Maven created results; inspect Java/checkout/configuration steps.

Follow `FAILURE-GUIDE.md` before deciding whether to change the application, test, data, or environment.

