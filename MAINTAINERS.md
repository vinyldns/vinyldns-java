# Maintainers

## Table of Contents

* [Release](#release)

## Release

### Central Portal and credentials

- Make sure you have `mvn` installed (on macOS, `brew install maven`).
- Ensure you have access to the `io.vinyldns` namespace in the Sonatype Central Portal (`https://central.sonatype.com/`).

For releases, we use a **Central Portal user token** and a shared GPG key.

The Central Portal will show you a snippet when you generate a token; adapt it to something like:

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
        <gpg.keyname>RELEASE_KEY_ID</gpg.keyname>
        <gpg.passphrase><![CDATA[KEY_PASSPHRASE]]></gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>
```

The GPG key material and passphrase are stored in a secrets manager; ask the project maintainers how to retrieve and import them locally before releasing.

### Proxies

Make sure you are not behind any corporate proxies as that may cause issues with the release

### Github SSH

The release process makes commits and tags in the upstream repository. Make sure you have ssh setup for your account on github.com

### Run

To run the release, execute `bin/release.sh --full`. This will:

1. Build and sign the artifacts with the `release` Maven profile.
2. Publish the signed artifacts to Maven Central via the Central Publishing Maven plugin.

Without `--full`, the script only performs a signed build (`mvn -P release clean verify`) and does **not** publish anything.

You can validate the release went through at `https://central.sonatype.com/artifact/io.vinyldns/vinyldns-java/versions`.

### Post

* Your fork's master should have a commit added to it, make a pr to github.com/vinyldns/vinyldns-java to sync up versions
* Make a release on github.com/vinyldns/vinyldns-java
