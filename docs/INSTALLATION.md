# Installation and setup

## Who this guide is for

This guide helps a new contributor prepare a computer to develop and run the Selenium framework. The framework has not been implemented yet, so commands marked **planned** will become active as code is added.

## Accounts and access

| Access | Purpose |
|---|---|
| GitHub account | Read the repositories, contribute changes, and view pipelines |
| Application test environment | Run browser tests against a deployed application |
| Dedicated test credentials | Create and remove non-production test users and favorites |
| Firebase access, if assigned | Use the emulator or approved test project for test-data setup |

A contributor should receive only the access needed for their role. Personal or production accounts must not be used as automation test accounts.

## Software to install

| Software | Why it is needed | Required? |
|---|---|---|
| Git | Downloads and versions the automation project | Yes |
| GitHub CLI (`gh`) | Publishes changes and helps inspect GitHub workflows | Yes for publishing |
| Java Development Kit 21 | Compiles and runs the planned Java test framework | Yes |
| Apache Maven 3.9 or newer | Downloads declared Java dependencies and runs tests | Yes |
| Google Chrome | Primary browser for local and CI smoke tests | Yes |
| Mozilla Firefox | Secondary browser compatibility coverage | Optional initially |
| Node.js current LTS and npm | Starts the application and Firebase emulators when running the complete system locally | Required for full local runs |
| Firebase CLI | Starts local Auth and Firestore emulators | Required for full local runs |
| Visual Studio Code or IntelliJ IDEA | Edits Java, configuration, and documentation | Recommended |

Use organization-approved distributions and installers on a managed company computer. For Java, an approved OpenJDK 21 distribution is sufficient.

## Verify the installation on Windows

Open PowerShell and run each command separately:

```powershell
git --version
gh --version
java --version
javac --version
mvn --version
node --version
npm --version
npx firebase-tools --version
```

Chrome can be verified by opening it normally. Selenium Manager will handle compatible browser-driver discovery when the framework creates a browser session, so a manually downloaded `chromedriver.exe` should not be committed or configured unless an organizational environment specifically requires it.

## Important Java check

The `java --version`, `javac --version`, and `mvn --version` output should all refer to the intended JDK 21 installation. If Maven reports a different Java home, correct the machine's approved Java configuration before troubleshooting Selenium.

## Authenticate GitHub CLI

Run:

```powershell
gh auth login -h github.com
gh auth status
```

Use the organization-approved authentication method and authorize organization SSO when required. Never commit a GitHub token.

## Planned framework dependencies

Maven will download these from the repository's future `pom.xml`; they should not be manually copied into the project:

- Selenium Java
- JUnit 5
- AssertJ
- Jackson for structured test data
- A reporting library selected during implementation

Exact versions will be pinned in `pom.xml` and updated through reviewed changes.

## Planned local test flow

Once the application and framework exist:

1. Start the application and Firebase emulators from the application repository.
2. Confirm the application URL opens in a browser.
3. Run the Selenium tests from this repository.
4. Review the console result and generated report.
5. Stop local services when finished.

The planned test command is:

```powershell
mvn test -DbaseUrl=http://localhost:5173 -Denvironment=local -Dbrowser=chrome -Dheadless=false
```

The command will become authoritative only after the framework configuration is implemented and verified.

## CI installation behavior

Contributors do not manually install tools on a GitHub Actions runner. The workflow will:

1. Check out the automation repository.
2. Install the declared Java version.
3. Restore the Maven dependency cache.
4. Make Chrome available.
5. Start or connect to the intended application environment.
6. Run the selected test suite.
7. Upload reports and failure evidence.

## Secrets and local configuration

- Store CI credentials in GitHub Actions secrets or the organization's approved secret manager.
- Store approved local-only values in an ignored file or environment variables.
- Commit an example file containing names and descriptions, but never real secret values.
- Do not include passwords or tokens in Maven commands, logs, screenshots, or reports.

## Common company-computer restrictions

Package registries, browsers, PowerShell scripts, Java distributions, GitHub, or Firebase may be controlled by organizational policy. Use approved mirrors and request IT access for proxy, certificate, SSO, or installation problems. Do not bypass security controls to make a test run.

## Ready-to-run checklist

- [ ] Git works and this repository can be accessed.
- [ ] GitHub CLI is authenticated when publishing is required.
- [ ] Java 21 and `javac` work.
- [ ] Maven uses Java 21.
- [ ] Chrome opens successfully.
- [ ] The application base URL is known.
- [ ] Test-data access is approved.
- [ ] Required secrets exist outside Git.
- [ ] No production account or data will be used.
