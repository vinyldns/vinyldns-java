# Maintainers

## Table of Contents

* [Release](#release)

## Release

Make sure you have `mvn`, on mac, this is `brew install maven`

The following guide was used to setup releases to Sonatype: https://central.sonatype.org/pages/apache-maven.html 

To release to Sonatype, a `settings.xml` is needed in [one of two locations](https://maven.apache.org/settings.html), 
like so:

```xml
<settings>
    <servers>
        <server>
            <id>ossrh</id>
            <username>...</username>
            <password>...</password>
        </server>
    </servers>
    <profiles>
        <profile>
            <id>ossrh</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <vinyldns-fork>your-github-username</vinyldns-fork>
                <gpg.executable>gpg</gpg.executable>
                <gpg.keyname>F6D171DC24C6EB30FCAC1E85AEF7D1D58E3C1B9A</gpg.keyname>
            </properties>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-gpg-plugin</artifactId>
                        <version>1.5</version>
                        <executions>
                            <execution>
                                <id>sign-artifacts</id>
                                <phase>verify</phase>
                                <goals>
                                    <goal>sign</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                    </plugins>
              </build>
        </profile>
    </profiles>
</settings>
```

To run the release, execute `bin/release.sh`

For a full release, use the flag `--full`, otherwise, only a `SNAPSHOT` will be released to the 
Sonatype staging endpoint

> Note, you will need the passphrase handy for the key F6D171DC24C6EB30FCAC1E85AEF7D1D58E3C1B9A
