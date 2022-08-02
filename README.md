# configuration-properties
<a href="https://github.com/amsa-code/configuration-properties/actions/workflows/ci.yml"><img src="https://github.com/amsa-code/configuration-properties/actions/workflows/ci.yml/badge.svg"/></a><br/>

Java code migrated from amsa-code/amsa-java/parent/amsa-util for reading configuration properties files.

The original code has been trimmed to the core classes in use in amsa-code and the API simplified significantly. This library is not a drop-in replacement for amsa-util configuration classes, it has breaking changes.

## How to build
```bash
mvn clean install
```

Note that to complete the build there must be 100% code coverage and spotbugs and pmd maven plugins must pass checks.

## How to deploy to amsa-maven-private

 
