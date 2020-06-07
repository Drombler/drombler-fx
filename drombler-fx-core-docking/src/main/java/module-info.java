module org.drombler.fx.core.docking {
    exports org.drombler.fx.core.docking;

    requires org.drombler.fx.core.application;
    requires transitive org.drombler.acp.core.data;
    requires org.drombler.acp.core.docking.spi;
    requires org.drombler.acp.core.context;
    requires transitive org.drombler.commons.action.command;
    requires org.drombler.commons.client.core;
    requires transitive org.drombler.commons.docking.fx;
    requires org.drombler.commons.docking.context;
    requires org.drombler.commons.docking.fx.context;
    requires org.apache.felix.framework;
    requires org.slf4j;
    requires osgi.cmpn;
}