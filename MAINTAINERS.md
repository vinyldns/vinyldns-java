# Maintainers

## Table of Contents

* [Release](#release)

## Release

### Sonatype credentials

Follow https://github.com/vinyldns/vinyldns/blob/master/MAINTAINERS.md#sonatype-credentials to get vinyldns-sonatype-key.asc imported to your local

### Maven settings

Make sure you have `mvn`, on mac, this is `brew install maven`

To release to Sonatype, make a `settings.xml` in `~/.m2` https://maven.apache.org/settings.html, like so:

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
		<gpg.keyname>7B4A1BE6CDBE6FB3D3B405AFF44DCC5464427A0F</gpg.keyname>
                <gpg.passphrase><![CDATA[KEY_PASSPHRASE]]></gpg.passphrase>
            </properties>
       </profile>
    </profiles>
</settings>
```

The following information must be provided:

* SONATYPE_USERNAME - oss.sonatype.org login for io.vinyldns
* SONATYPE_USERNAME - oss.sonatype.org password for io.vinyldns
* GITHUB_USERNAME - Github username that your fork is published to
* KEY_PASSPHRASE - passphrase for key 7B4A1BE6CDBE6FB3D3B405AFF44DCC5464427A0F

### Proxies

Make sure you are not behind any corporate proxies as that may cause issues with the release

### Github SSH

The release process makes a commit to your fork with the updated version and tag. Make sure you have ssh setup for your account on github.com

### Run

To run the release, execute `bin/release.sh`

For a full release, use the flag `--full`, otherwise, only a `SNAPSHOT` will be released to the Sonatype staging endpoint

> Note, you will need the passphrase handy for the key 7B4A1BE6CDBE6FB3D3B405AFF44DCC5464427A0F

You can validate the release went through at https://oss.sonatype.org/#nexus-search;quick~io.vinyldns

### Post

* Your fork's master should have a commit added to it, make a pr to github.com/vinyldns/vinyldns-java to sync up versions
* Make a release on github.com/vinyldns/vinyldns-java
