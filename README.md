# Weather Favorites Selenium tests

This separate repository tests the deployed [Weather Favorites application](https://weather-app-nine-vert-81.vercel.app) as a user would: it opens a real browser, creates or signs in a disposable Firebase user, searches for weather, manages favorites, and signs out.

No application source code is stored here. This separation lets the QA automation have its own reviewers, history, releases, and GitHub Actions pipeline.

## What works today

- Java 21, Maven, Selenium 4, and JUnit 5 framework
- Chrome and Firefox driver creation through Selenium Manager
- Page objects using stable `data-testid` selectors
- Unique Firebase users created by each account test and deleted afterward
- Deterministic mock weather for repeatable UI assertions
- Smoke, full regression, and feature-specific suites
- Screenshot and HTML page capture when a test fails
- GitHub Actions on every change, nightly, manually, or from an application deployment event

## Run it

From PowerShell in this repository:

```powershell
.\scripts\run-tests.ps1 -Suite smoke -Browser chrome -Headless $true
```

To watch the browser locally:

```powershell
.\scripts\run-tests.ps1 -Suite smoke -Browser chrome -Headless $false
```

`regression` runs every implemented test. Other valid suites are `registration`, `authentication`, `weather`, `favorites`, and `session`.

## Documentation map

| Document | Plain-language purpose |
|---|---|
| [Installation](docs/INSTALLATION.md) | What must be installed and how to verify it |
| [Test cases](docs/TEST-CATALOG.md) | What each test proves and whether it is automated |
| [Test data](docs/TEST-DATA-GUIDE.md) | Where accounts/weather come from and how cleanup works |
| [Pipeline](docs/PIPELINE-GUIDE.md) | When GitHub runs tests and how anyone with access can run/view them |
| [Cross-repository CI/CD](docs/CROSS-REPOSITORY-CICD.md) | How Vercel deployments trigger Selenium and return results to application commits |
| [Changes](docs/CHANGE-IMPACT-GUIDE.md) | Exactly what to update when the application changes |
| [Failures](docs/FAILURE-GUIDE.md) | How to investigate a red test without hiding defects |
| [Strategy](docs/TEST-STRATEGY.md) | Scope, priorities, and testing principles |
| [Traceability](docs/TRACEABILITY.md) | Which user requirement maps to which test ID |

## Source layout

| Folder | Meaning |
|---|---|
| `src/test/java/.../tests` | Readable scenarios and assertions |
| `src/test/java/.../pages` | Browser actions and selectors for each screen |
| `src/test/java/.../data` | Unique test-user creation and cleanup |
| `src/test/java/.../driver` | Browser configuration |
| `src/test/java/.../support` | Failure screenshots and page source |
| `.github/workflows` | GitHub Actions pipeline |
| `docs` | Non-technical and contributor guides |

## Important rule

When intended behavior changes, update the catalog, test implementation, and traceability together. Do not change a failing expectation merely to make the pipeline green; first confirm that the product requirement really changed.
