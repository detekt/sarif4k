# Releasing

1. Change the version in `gradle.properties` to a non-SNAPSHOT version.
2. Update the `CHANGELOG.md` for the impending release.
3. Update the `README.md` with the new version.
4. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version)
5. `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version)
6. Update the `gradle.properties` to the next SNAPSHOT version (X.Y.Z)
7. `git commit -am "Prepare next development version."`
8. `git push && git push --tags`
9. Create a new release in the releases tab on GitHub
10. Wait for the [publish-release.yml](.github/workflows/publish-release.yml) action to complete.
11. Visit [Sonatype Nexus](https://oss.sonatype.org/) and promote the artifact.