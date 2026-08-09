# Cross-repository CI/CD guide

## Plain-language flow

The application and Selenium tests have separate repositories, but their pipelines exchange the deployed URL, application commit, environment, and final result.

```text
Successful Vercel deployment
  -> weather-app sends weather-app-deployed event
  -> Selenium tests exact deployment URL
  -> Selenium uploads reports and failure evidence
  -> Selenium marks application commit Passed or Failed
```

## Information received from the application

| Payload value | Example | Purpose |
|---|---|---|
| `base_url` | Vercel deployment URL | Exact application deployment Selenium opens |
| `suite` | `smoke` | Tests selected for deployment verification |
| `application_sha` | Git commit SHA | Application version receiving the result |
| `application_ref` | Branch or commit reference | Identifies the originating change |
| `application_environment` | `preview` or `production` | Distinguishes pre-merge and live checks |
| `status_context` | `selenium/preview` | Name displayed on the application commit |

## Required Selenium repository secrets

### `APP_STATUS_TOKEN`

Create a fine-grained GitHub credential restricted to `deepakkancherla/weather-app` with:

- Repository permission: **Commit statuses — Read and write**

Store it under **weather-selenium-tests > Settings > Secrets and variables > Actions > New repository secret** with the name `APP_STATUS_TOKEN`.

This token reports Pending, Passed, Failed, or Error on the application commit. It must never be stored in source code, repository variables, workflow inputs, logs, or artifacts.

### `VERCEL_AUTOMATION_BYPASS_SECRET` (needed when Preview protection is enabled)

In Vercel, open the project's Deployment Protection settings and create a Protection Bypass for Automation secret. Store the value as the `VERCEL_AUTOMATION_BYPASS_SECRET` GitHub Actions repository secret in this Selenium repository.

The framework adds the secret only when opening the deployment and asks Vercel to set a browser bypass cookie. Production tests continue to work if this optional secret is absent and the deployment is public.

## Test result returned to the application

| Result | Application commit status |
|---|---|
| Tests running | Pending |
| Every selected test passed | Success |
| At least one test failed or errored | Failure |
| Workflow cancelled | Error |

The status links directly to the Selenium Actions run. That run contains Maven logs, JUnit reports, and screenshots/page HTML for failures.

## Changing the application and tests together

1. Push the application branch and wait for its Vercel Preview URL.
2. Create a Selenium branch for required test changes.
3. Update the test catalog, page object, test, data, and traceability as applicable.
4. From the Selenium branch, manually run the affected suite against the Preview URL.
5. Run Smoke after the feature suite passes.
6. Merge compatible Selenium changes before or together with the application change.
7. Automatic Production Smoke validates the deployed application after merge.

Repository-dispatch events run the Selenium workflow from its default branch. They cannot automatically use an unmerged Selenium branch, which is why step 4 is required for coordinated breaking changes.

## Troubleshooting

- No Selenium run after Vercel succeeds: inspect the application dispatch workflow and `SELENIUM_REPO_TOKEN`.
- Selenium starts but cannot report status: inspect `APP_STATUS_TOKEN` and its Commit statuses permission.
- Preview opens a Vercel authentication page: configure `VERCEL_AUTOMATION_BYPASS_SECRET`.
- Application status stays Pending: open the linked Selenium run and inspect the final callback step.
- Tests fail: download the run artifact and follow `FAILURE-GUIDE.md` before changing expectations.

