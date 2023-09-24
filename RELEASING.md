# Releasing

- Update 
  - the version in `gradle.properties` to a non-SNAPSHOT version. 
  - `CHANGELOG.md` for the upcoming release.
  - `README.md` with the new version.
  - `git commit -am "Prepare for release X.Y.Z"` (where X.Y.Z is the new version)
  - `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version)
- Publish the version
  - Run`./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository` locally
  - Create a new release in the releases tab on GitHub
  - Verify the version is published
    - https://repo.maven.apache.org/maven2/io/github/detekt/sarif4k/sarif4k/
    - https://repo1.maven.org/maven2/io/github/detekt/sarif4k/sarif4k/
  - Visit [Sonatype Nexus](https://oss.sonatype.org/) to debug if there is anything wrong.
- Update 
  - `gradle.properties` to the next SNAPSHOT version (X.Y.Z)
  - `git commit -am "Prepare next development version."`
  - `git push && git push --tags`
- Wait for the [publish-snapshot.yml](.github/workflows/publish-snapshot.yml) action to complete.