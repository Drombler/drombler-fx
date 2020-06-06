module org.drombler.fx.maven.plugin {
    requires maven.artifact;
    requires maven.core;
    requires maven.plugin.api;
    requires maven.plugin.annotations;
    requires maven.dependency.plugin;
    requires maven.project;
    requires maven.pax.plugin;
    requires org.drombler.fx.startup.main;
    requires maven.model;
    requires org.drombler.commons.client.startup.main;
    requires plexus.utils;
    requires org.softsmithy.lib.core;

}