module org.drombler.fx.core.action {
    exports org.drombler.fx.core.action;

    requires org.drombler.acp.core.action;
    requires org.drombler.acp.core.action.spi;
    requires org.drombler.commons.action.fx;
    requires org.drombler.commons.fx.graphics;
    requires org.drombler.commons.fx.controls;
    requires org.apache.commons.lang3;
    requires osgi.cmpn;
}