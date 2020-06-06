module org.drombler.fx.core.application {
    exports org.drombler.fx.core.application;

    requires javafx.graphics;
    requires org.drombler.acp.core.commons;
    requires org.drombler.acp.startup.main;
    requires transitive org.drombler.commons.fx.graphics;
    requires osgi.cmpn;
    requires org.slf4j;
}