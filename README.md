# configuration-properties
<a href="https://github.com/amsa-code/configuration-properties/actions/workflows/ci.yml"><img src="https://github.com/amsa-code/configuration-properties/actions/workflows/ci.yml/badge.svg"/></a><br/>

Java code migrated from *amsa-code/amsa-java/parent/amsa-util* for reading configuration properties files.

The original code has been trimmed to the core classes in use in *amsa-code* and the API simplified significantly. This library is not a drop-in replacement for *amsa-util* configuration classes, it has breaking changes and also includes a couple of minor bug fixes (like using UTC timezone instead of the platform default in parsing of datetimes in configuration).

## Features
* supports encrypted fields
* supports resolving configuration (resolves nested references to other fields)
* loads configuration from file system, classpath, system properties, java.util.Map
* uses slf4j for logging
* compatible with Java 8, 11, 17 (see [ci.yml](.github/workflows/ci.yml))
* dependabot enabled with automerge for passing dependabot PRs (see [ci.yml](.github/workflows/ci.yml))
* Enforced 100% unit test coverage
* Enforced Spotbugs and PMD checks

## How to use
Add this to your poml.xml:
```xml
<dependency>
    <groupId>au.gov.amsa.er</groupId>
    <artifactId>configuration-properties</artifactId>
    <version>VERSION_HERE</version>
</dependency>
```

## How to build
```bash
mvn clean install
```

Note that to complete the build there must be 100% code coverage and spotbugs and pmd maven plugins must pass checks.

## How to deploy to amsa-maven-private

Run 
```bash
./release.sh <VERSION>
```
You do of course need a relevant entry in the `servers` section of your `.m2/settings.xml`. Something like:

```xml
<servers>
...
  <server>
    <id>amsa-maven-private</id>
    <username>amsa</username>
    <password>ENCRYPTED_PASSWORD</password>
  </server>
</servers>
```
After running the script please update [Releases](../../releases) (convert the new tag into a release).


 
