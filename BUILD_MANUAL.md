This project is build via maven. The build is configurable using maven profiles.

Types of profiles:
-
* Grouped profiles - profiles that form a "Profile group". Exactly one profile of a group must be chosen.
If no profile or multiple profiles of a group are chosen, the build fails immediately.
* Standalone profiles - optional profiles that don't belong to any group. Those profiles enable additional
build features.

Existing profiles:
-

* Profile groups:
    * Environmental group:
        * local-machine-env - prepares project modules to be deployed on local machine.
        * docker-env - prepares project modules to be deployed in separate docker containers. 
        This profile is enabled by default.

* Standalone profiles:
    * debug - enables additional project scanning and validation.
    * javadocs - generates java documentation using javadoc tool.
    * integration-test - runs integration tests. 
    This profile requires docker enabled.
    * meteo-plugin - rebuilds the additional meteo plugin module and adds it to the core classpath. 
    * docker-run - runs the application in docker environment for development/testing purposes. 
    This profile requires docker enabled.
 