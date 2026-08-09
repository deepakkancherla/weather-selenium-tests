# Installation and setup

## Required software

| Install | Minimum | Why |
|---|---:|---|
| Git | Current supported version | Download and version the repository |
| Java Development Kit | 21 | Compile and run the tests |
| Apache Maven | 3.8.8 | Download dependencies and execute JUnit |
| Google Chrome | Current stable | Default test browser |
| GitHub account | Repository access | View and manually run pipeline tests |
| GitHub CLI (`gh`) | Current version | Optional; view/run pipelines from PowerShell |

Firefox is optional. Node.js and Firebase CLI are needed only if you also run the application locally; they are not required when testing the Vercel deployment.

Selenium Manager discovers the matching browser driver automatically. Do not copy `chromedriver.exe` into this repository.

## Verify Windows installation

```powershell
git --version
java --version
javac --version
mvn --version
gh auth status
```

Java, `javac`, and Maven should all report Java 21. Open Chrome once normally to confirm it is installed.

## Download and run

```powershell
git clone https://github.com/deepakkancherla/weather-selenium-tests.git
cd weather-selenium-tests
.\scripts\run-tests.ps1 -Suite smoke -Browser chrome -Headless $true
```

Maven downloads dependencies on the first run, so the first run is slower and needs Maven Central access. A successful run ends with `BUILD SUCCESS`.

## Useful commands

```powershell
# All implemented tests
.\scripts\run-tests.ps1 -Suite regression

# Only login/account tests with a visible browser
.\scripts\run-tests.ps1 -Suite authentication -Headless $false

# A different application deployment
.\scripts\run-tests.ps1 -Suite smoke -BaseUrl https://your-preview.example.com
```

The equivalent direct Maven command is:

```powershell
mvn test -Dgroups=smoke -DbaseUrl=https://weather-app-nine-vert-81.vercel.app -Dbrowser=chrome -Dheadless=true
```

## Configuration

| PowerShell parameter | Maven property | Environment variable | Default |
|---|---|---|---|
| `BaseUrl` | `baseUrl` | `TEST_BASE_URL` | Vercel application URL |
| `Browser` | `browser` | `TEST_BROWSER` | `chrome` |
| `Headless` | `headless` | `TEST_HEADLESS` | `true` |
| n/a | `firebaseApiKey` | `FIREBASE_WEB_API_KEY` | App public Firebase web API key |

Firebase's web API key identifies the project; it is not an administrator password. The tests authenticate as the disposable user they create and can delete only that user. Organization secrets must still stay in GitHub secrets or approved environment variables.

## Reports

- JUnit XML/text: `target/surefire-reports`
- Failure screenshots and page HTML: `target/evidence`

`target` is ignored by Git because evidence is generated for each run and can contain page content.

## Common problems

- `mvn` is not recognized: install Maven and add its `bin` directory to `PATH`.
- Maven reports the wrong Java: correct `JAVA_HOME` to JDK 21 according to organization policy.
- Browser cannot start: update Chrome and rerun; Selenium Manager resolves a compatible driver.
- Dependency download is blocked: ask IT for approved Maven Central/proxy access.
- Every generated login fails: confirm Firebase Email/Password authentication is enabled and the project matches the app.
