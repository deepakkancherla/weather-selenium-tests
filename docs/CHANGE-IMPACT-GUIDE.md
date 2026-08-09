# What to update when the application changes

Start with one question: **Did the intended user behavior change, or did only the implementation change?** A failing test is not permission to rewrite the expectation. Confirm intended behavior with the product owner first.

## Exact change map

| Application change | Test IDs to review | Selenium file to change | Data/config to check | First suite to run |
|---|---|---|---|---|
| Login/register field, label, or button selector | REG-*, AUTH-* | `pages/LoginPage.java` | None unless accepted values changed | `authentication` |
| Password/email rules or message | REG-003..005, AUTH-002..003 | Corresponding test assertion; `LoginPage` only for actions | `TestDataFactory.java` | `authentication` |
| Successful login destination | REG-001, AUTH-001, AUTH-005 | `LoginPage.java`, `DashboardPage.java` | None | `smoke` |
| Profile menu or logout | AUTH-005 | `DashboardPage.java` | None | `authentication` |
| Search input/button/results | WEATHER-001..004 | `DashboardPage.java` | Mock-weather behavior | `weather` |
| Weather content or units | WEATHER-001, 005, 006 | Weather assertions/page object | App mock fixture | `weather` |
| Save/remove favorite control | FAV-001..004, 006 | `DashboardPage.java` | Existing Firebase user cleanup | `favorites` |
| Favorites storage/security rules | FAV-* | Usually tests stay; update data client only if cleanup changes | Firebase rules/project | `favorites` |
| Firebase project/auth configuration | REG-001, AUTH-*, FAV-*, SESSION-* | `TestConfig.java` only if configuration names change | GitHub Actions variables | `smoke` |
| Application URL | All | No Java change | `TEST_BASE_URL`, workflow manual input | `smoke` |
| Browser support | All | `DriverFactory.java` | Workflow browser choice | `smoke`, then `regression` |
| A `data-testid` value | Every case using that control | Relevant page object only | None | Affected feature |

## Four common examples

### A button is renamed but behavior is unchanged

If only visible text changes and the stable `data-testid` remains the same, the page object normally needs no change. Update an assertion only if it intentionally verifies that visible wording.

### A `data-testid` changes

Search the repository for the old value, change it in `LoginPage.java` or `DashboardPage.java`, run that feature suite, then run Smoke. Do not copy the selector into each test class.

### Validation behavior changes

Keep the permanent case ID, update its plain-language row in `TEST-CATALOG.md`, change the matching test input/expectation, and update `TRACEABILITY.md` if coverage changed. Run Authentication and then Smoke.

### A new user flow is added

1. Add permanent case IDs and expected outcomes to the catalog.
2. Add only reusable actions to a page object.
3. Add independent test methods whose names include the IDs.
4. Add data creation and cleanup together.
5. Add appropriate JUnit tags.
6. Update traceability.
7. Run the feature suite and full regression before review.

## Commands after a change

```powershell
# Example: login changed
.\scripts\run-tests.ps1 -Suite authentication -Headless $true
.\scripts\run-tests.ps1 -Suite smoke -Headless $true

# Before merging a larger behavioral change
.\scripts\run-tests.ps1 -Suite regression -Headless $true
```

Then push a branch or open a pull request. GitHub automatically runs Smoke and keeps its results under Actions.

## Review checklist

- [ ] Intended behavior was confirmed; this is not hiding a product defect.
- [ ] Every affected permanent test ID was reviewed.
- [ ] Page selectors remain in page objects, not copied into tests.
- [ ] Test data is unique and cleanup supports the new behavior.
- [ ] No real user data, token, password, or private key was committed.
- [ ] Catalog and traceability describe the new behavior.
- [ ] Affected feature suite passed.
- [ ] Smoke passed against the matching application deployment.
- [ ] Full regression ran when the change crossed several sections.

