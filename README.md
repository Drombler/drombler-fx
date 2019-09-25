Drombler FX
===========

The modular application framework for *JavaFX* based on:

 - **OSGi**: *OSGi* is the de facto standard for writing modular software in *Java*.

 - **Maven (POM-first)**: *Drombler FX* applications can be build with *Maven*. The build follows the standard POM-first approach (the OSGi meta data will be generated for you). A custom Maven Plugin will help you to easily create *JavaFX* applications with Maven. A custom Maven Archetype will help you to get started.

- **Declarative programming model**: Annotations can be used at many places to register elements such as menus, toolbars and GUI components.

- **Drombler ACP**: *Drombler FX* is a *JavaFX* specific extension for *Drombler ACP* providing *JavaFX*-based implementations for the abstract definitions. Read more about *Drombler ACP* here: [Drombler ACP](../drombler-acp)

- **Drombler Commons**: *Drombler Commons* is a collection of reusable libraries and frameworks. They ship with *OSGi* meta data but don’t require an *OSGi* environment. Read more about *Drombler Commons* here: [Drombler Commons](../drombler-commons)

*Drombler FX* uses [Apache Felix](http://felix.apache.org) as its OSGi container by default.

As an application framework it makes sure *JavaFX* and *OSGi* will get started properly and it provides the main window.

See the [documentation](https://www.drombler.org/drombler-fx) for a tutorial, the Javadoc and information about the provided Maven modules (available from Maven Central).

## Build the project from sources
```bash
mvn clean install
```
Please note that the develop branch (SNAPSHOT version) of the project might depend on SNAPSHOT versions of other projects.

If you don't want to build the dependent projects as well, please make sure to define a proxy in your [Maven Repository Manager](https://maven.apache.org/repository-management.html) to the following Maven Repository: https://oss.sonatype.org/content/repositories/snapshots/ and include it in your [single group](https://help.sonatype.com/repomanager3/formats/maven-repositories#MavenRepositories-ConfiguringApacheMaven).
