# Maintainers

## Table of Contents

* [Release](#release)

## Release

Make sure you have `mvn`, on mac, this is `brew install maven`

The following guide was used to setup releases to Sonatype: https://central.sonatype.org/pages/apache-maven.html

To release to Sonatype, a `settings.xml` in `~/.m2` https://maven.apache.org/settings.html, like so:

```xml
<settings>
    <servers>
        <server>
            <id>ossrh</id>
            <username>SONATYPE_USERNAME</username>
            <password>SONATYPE_PASSWORD</password>
        </server>
    </servers>
    <profiles>
        <profile>
            <id>ossrh</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>

            <properties>
		<vinyldns-fork>GITHUB_USERNAME</vinyldns-fork>
                <gpg.executable>gpg</gpg.executable>
		<gpg.keyname>F6D171DC24C6EB30FCAC1E85AEF7D1D58E3C1B9A</gpg.keyname>
                <gpg.passphrase>KEY_PASSPHRASE</gpg.passphrase>
            </properties>
       </profile>
    </profiles>
</settings>
```

The following information must be provided:

* SONATYPE_USERNAME - oss.sonatype.org login for io.vinyldns
* SONATYPE_USERNAME - oss.sonatype.org password for io.vinyldns
* GITHUB_USERNAME - Github username that your fork is published to
* KEY_PASSPHRASE - passphrase for key F6D171DC24C6EB30FCAC1E85AEF7D1D58E3C1B9A

To run the release, execute `bin/release.sh`

For a full release, use the flag `--full`, otherwise, only a `SNAPSHOT` will be released to the Sonatype staging endpoint

> Note, you will need the passphrase handy for the key F6D171DC24C6EB30FCAC1E85AEF7D1D58E3C1B9A
