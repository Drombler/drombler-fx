/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.References;
import org.javafxplatform.core.startup.ApplicationContentProvider;

/**
 *
 * @author puce
 */
@Component(immediate = true)
@References({
    @Reference(name = "contentPaneProvider", referenceInterface = ContentPaneProvider.class),
    @Reference(name = "applicationContentProvider", referenceInterface = ApplicationContentProvider.class)
})
public class ContentHandler {

    private BorderPane contentPane;
    private Node content;

    protected void bindContentPaneProvider(ContentPaneProvider contentPaneProvider) {
        contentPane = contentPaneProvider.getContentPane();
        if (content != null) {
            initContentPane();
        }
    }

    private void initContentPane() {
        contentPane.setCenter(content);
    }

    protected void unbindContentPaneProvider(ContentPaneProvider contentPaneProvider) {
        uninitContentPane();
        contentPane = null;
    }

    private void uninitContentPane() {
        //TODO needed?
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                contentPane.setCenter(null);
            }
        });
    }

    protected void bindApplicationContentProvider(ApplicationContentProvider applicationContentProvider) {
        content = applicationContentProvider.getContentPane();
        if (contentPane != null) {
            initContentPane();
        }
    }

    protected void unbindApplicationContentProvider(ApplicationContentProvider applicationContentProvider) {
        if (contentPane != null) {
            uninitContentPane();
        }
        content = null;
    }
}
