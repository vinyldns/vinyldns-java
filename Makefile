SHELL=bash

# Check that the required version of make is being used
REQ_MAKE_VER:=3.82
ifneq ($(REQ_MAKE_VER),$(firstword $(sort $(MAKE_VERSION) $(REQ_MAKE_VER))))
   $(error The version of MAKE $(REQ_MAKE_VER) or higher is required; you are running $(MAKE_VERSION))
endif

.ONESHELL:

all: package test

.PHONY: clean
clean:
	mvn clean --file pom.xml

.PHONY: test
test: clean
	mvn test --file pom.xml

.PHONY: package
package: clean
	mvn package --file pom.xml

.PHONY: deploy
deploy: package
	mvn --batch-mode deploy --file pom.xml

