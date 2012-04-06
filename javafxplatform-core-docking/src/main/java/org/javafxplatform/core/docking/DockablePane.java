/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import org.javafxplatform.core.docking.skin.Stylesheets;
import org.richclientplatform.core.docking.Dockable;

/**
 *
 * @author puce
 */
public class DockablePane extends Control implements Dockable {

    private static final String DEFAULT_STYLE_CLASS = "dockable-pane";
    private final StringProperty title = new SimpleStringProperty(this, "titleProperty", "");
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>(this, "content", null);

    public DockablePane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

//    @Override
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public Node getContent() {
        return content.get();
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    @Override
    public void open() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void requestActive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
