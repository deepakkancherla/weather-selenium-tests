param(
    [ValidateSet("smoke", "regression", "registration", "authentication", "weather", "favorites", "session")]
    [string]$Suite = "smoke",
    [ValidateSet("chrome", "firefox")]
    [string]$Browser = "chrome",
    [string]$BaseUrl = "https://weather-app-nine-vert-81.vercel.app",
    [bool]$Headless = $true
)

$arguments = @(
    "--batch-mode",
    "--no-transfer-progress",
    "test",
    "-DbaseUrl=$BaseUrl",
    "-Dbrowser=$Browser",
    "-Dheadless=$($Headless.ToString().ToLower())"
)

if ($Suite -ne "regression") {
    $arguments += "-Dgroups=$Suite"
}

& mvn @arguments
exit $LASTEXITCODE

