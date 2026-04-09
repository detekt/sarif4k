# Releasing

1. Keep `gradle.properties` on the next `-SNAPSHOT` version on `main`.
2. Create and push an annotated release tag from the commit you want to publish.
   - Example: `git tag -a 0.7.0 -m "Version 0.7.0"`
   - Example: `git push origin 0.7.0`
3. Wait for the [Release workflow](.github/workflows/release.yml) to start, then approve the `release` environment deployment.
   - Configure required reviewers for the `release` environment in GitHub settings to enforce the second-maintainer approval requested in issue #230.
4. The workflow will:
   - build sarif4k with the tag version
   - publish the artifacts to Sonatype and close/release the staging repository using the repository secrets
   - create the GitHub release entry for the tag
5. Verify the version is published.
   - https://central.sonatype.com/artifact/io.github.detekt.sarif4k/sarif4k
   - https://repo.maven.apache.org/maven2/io/github/detekt/sarif4k/sarif4k/
   - https://repo1.maven.org/maven2/io/github/detekt/sarif4k/sarif4k/
6. Visit [Sonatype Central Portal](https://central.sonatype.com/) to debug if anything goes wrong.

Pushes to `main` continue to publish the snapshot version via the same [Release workflow](.github/workflows/release.yml).
