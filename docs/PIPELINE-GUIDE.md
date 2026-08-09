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

1. Open [weather-selenium-tests Actions](https://github.com/deepakkancherla/weather-selenium-tests/actions).
2. Select **Selenium Tests** from the workflow list on the left.
3. Select a run from the center of the page.
4. Select the job name, such as **regression / chrome**.
5. Expand **Run selected Selenium tests** to read the Maven output for every test class.
6. Return to the run's **Summary** page to download `selenium-results-<run number>` under **Artifacts**.

The Maven output shows the number of executed, failed, errored, and skipped tests. A successful full run ends with output similar to:

```text
Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Start a manual run in GitHub

1. Open **Actions** > **Selenium Tests**.
2. Select **Run workflow** on the right side of the page.
3. Keep branch `main`.
4. Choose the required test suite.
5. Choose `chrome` or `firefox`.
6. Enter the Vercel or preview URL to test. Normally keep `https://weather-app-nine-vert-81.vercel.app`.
7. Select the green **Run workflow** button.

The available suite choices are:

| Suite | What it runs | When to use it |
|---|---|---|
| `smoke` | Five critical user journeys | Quick deployment or framework check |
| `regression` | Every implemented automated test | Release validation and full checks |
| `registration` | Account creation and registration validation | Registration changes |
| `authentication` | Login, incorrect credentials, required fields, and logout | Authentication changes |
| `weather` | City search and weather display | Search or weather changes |
| `favorites` | Save, retain, remove, and empty favorites | Firebase/Favorites changes |
| `session` | Signed-in session after browser refresh | Session changes |

After selecting **Run workflow**, refresh the page after a few seconds. The new run appears at the top. Its status uses these symbols:

- Yellow circle: still running.
- Green check: all selected tests passed.
- Red X: at least one selected test failed.
- Grey/cancelled: the run was manually cancelled or replaced by a newer run.

## Download reports, screenshots, and page output

1. Open a completed workflow run.
2. Open its **Summary** page.
3. Scroll to **Artifacts** near the bottom.
4. Download `selenium-results-<run number>`.
5. Extract the downloaded ZIP file.

The extracted artifact contains:

| Folder | Contents |
|---|---|
| `surefire-reports` | JUnit XML plus readable text results for every test class |
| `evidence` | A PNG screenshot and HTML page source for each failed test |

Screenshots and HTML evidence are generated only when a test fails. A successful run normally contains the JUnit reports without failure screenshots. Artifacts are retained for 14 days.

For a failure, open the `.txt` report matching the failed class, such as `com.weatherlab.tests.FavoriteTests.txt`. The corresponding files under `evidence` use the failed method name, for example:

```text
fav004_userCanRemoveSavedCity__.png
fav004_userCanRemoveSavedCity__.html
```

The PNG shows what the browser displayed at failure time. The HTML preserves the page structure for selector and application-state investigation.

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
