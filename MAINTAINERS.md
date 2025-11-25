# Maintainers

## Table of Contents

* [Release](#release)

## Release

### Central Portal and credentials

- Make sure you have `mvn` installed (on macOS, `brew install maven`).
- Ensure you have access to the `io.vinyldns` namespace in the Sonatype Central Portal (`https://central.sonatype.com/`).

For releases, we use a **Central Portal user token** and a shared GPG key.

The Central Portal token should be added to your `~/.m2/settings.xml`. For example,:

```xml
<settings>
  <servers>
    <server>
      <id>central</id>
      <username>CENTRAL_TOKEN_USERNAME</username>
      <password>CENTRAL_TOKEN_PASSWORD</password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>vinyldns-release</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <gpg.executable>gpg</gpg.executable>
        <gpg.keyname>7B4A1BE6CDBE6FB3D3B405AFF44DCC5464427A0F</gpg.keyname>
        <gpg.passphrase><![CDATA[KEY_PASSPHRASE]]></gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>
```

The GPG key material and KEY_PASSPHRASE are stored in a secrets manager; ask the project maintainers how to retrieve and import them locally before releasing.

### Prepare a release

1. Make sure `master` contains all changes you want in the release.
2. Update the version in `pom.xml` from `X.Y.Z-SNAPSHOT` to the new release version, for example:
   - `0.9.4-SNAPSHOT` -> `0.9.5`
3. Update the version shown in the dependency snippet in `README.md` to match the new release version.
4. Commit the version bump on `master`, e.g. `git commit -am "Release 0.9.5"` and push.
5. Create and push a git tag for the release:
   - `git tag 0.9.5`
   - `git push origin 0.9.5`

### Proxies

Make sure you are not behind any corporate proxies as that may cause issues with the release

### Github SSH

Releases require pushing commits and tags to the upstream repository. Make sure you can push to GitHub (typically via SSH) for your account on github.com.

### Run

After preparing the release and pushing the tag:

1. On a machine with the GPG key imported and Central credentials configured in `~/.m2/settings.xml`, check out the tagged commit (or `master` at that commit).
2. For a dry run (no publish), execute `bin/release.sh`. This will perform a signed build only (`mvn -P release clean verify`).
3. To perform an actual release, execute `bin/release.sh --full`. This will:
   - Build and sign the artifacts with the `release` Maven profile.
   - Publish the signed artifacts to Maven Central via the Central Publishing Maven plugin (via `mvn -P release clean deploy`).

You can validate the release went through at `https://central.sonatype.com/artifact/io.vinyldns/vinyldns-java/versions`.

You can validate the release went through at `https://central.sonatype.com/artifact/io.vinyldns/vinyldns-java/versions`.

### Post

* Create a GitHub Release for the new tag (for example `0.9.5`), including release notes that summarize the changes.
* Optionally bump `pom.xml` on `master` to the next `-SNAPSHOT` version (for example `0.9.6-SNAPSHOT`) in a follow-up commit to resume development.
