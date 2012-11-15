/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.application.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.drombler.acp.core.action.spi.ApplicationToolBarContainerProvider;
import org.drombler.acp.core.action.spi.MenuBarMenuContainerProvider;
import org.drombler.acp.core.action.spi.MenuItemRootContainer;
import org.drombler.acp.core.action.spi.ToolBarContainer;
import org.drombler.fx.core.action.MenuBarMenuContainer;
import org.drombler.fx.core.commons.javafx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class ApplicationPane extends GridPane implements MenuBarMenuContainerProvider<MenuItem, Menu>, ContentPaneProvider,
        ApplicationToolBarContainerProvider<ToolBar, Node>, Initializable {

    private final MenuItemRootContainer<MenuItem, Menu> menuBarMenuContainer;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ToolBarContainerPane toolBarContainerPane;
    @FXML
    private BorderPane contentPane;

//    private final ActionTracker actionTracker;
    public ApplicationPane() throws IOException {
//        actionTracker = new ActionTracker(bundleContext, new ExtensionTrackerListener<List<ActionDescriptor>>() {
//
//            @Override
//            @SuppressWarnings("unchecked")
//            public void addingExtension(Bundle bundle, BundleEvent be, List<ActionDescriptor> actionDescriptors) {
//                ObservableList<MenuItem> menuItems = controller.menuBar.getMenus().get(0).getItems();
//                for (ActionDescriptor actionDescriptor : actionDescriptors) {
//                    try {
//                        MenuItem menuItem = new MenuItem(actionDescriptor.getDisplayName());
//                        menuItem.setOnAction((EventHandler<ActionEvent>) actionDescriptor.getListenerClass().newInstance());
//                        menuItems.add(menuItems.size() - 1, menuItem);
//                    } catch (InstantiationException | IllegalAccessException | ClassCastException ex) {
//                        Logger.getLogger(ApplicationPane.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//
//            @Override
//            public void removedExtension(Bundle bundle, BundleEvent be, List<ActionDescriptor> t) {
//
//            }
//        });
//        FXMLLoader loader = new FXMLLoader();
//        Class<?> type = ApplicationPane.class;
//        loader.setClassLoader(type.getClassLoader());
//        loader.setResources(ResourceBundle.getBundle(type.getPackage().getName() + ".Bundle"));
//        Pane root = (Pane) loader.load(type.getResourceAsStream(type.getSimpleName() + ".fxml"));
//        controller = (ApplicationPane.Controller) loader.getController();
        load();
        menuBarMenuContainer = new MenuBarMenuContainer(menuBar);
//
//
//
//        Menu fooMenu = new Menu("Foo");
////        Image iconImage = loadIcon("save16.gif");
//
//        SaveHandler saveHandler = new SaveHandler();
//        saveHandler.setDisplayName("_Save");
//        saveHandler.setAccelerator(KeyCombination.keyCombination("Shortcut+H"));
////        saveHandler.setIcon(iconImage);
//
//        MenuItem saveItem = new MenuItem();
//        saveItem.setMnemonicParsing(true);
//        saveItem.textProperty().bind(saveHandler.displayNameProperty());
//        saveItem.acceleratorProperty().bind(saveHandler.acceleratorProperty());
//        saveItem.setOnAction(saveHandler);
//        saveItem.disableProperty().bind(saveHandler.disabledProperty());
////
////        if (saveHandler.getIcon() != null) {
////            saveItem.setGraphic(new ImageView(saveHandler.getIcon()));
////        }
//        fooMenu.getItems().add(saveItem);
//
//        menuBarMenuContainer.addMenu("foo", PositionableMenuItemAdapter.wrapMenuItem(fooMenu, 1));
//
//


//        actionTracker.open();
    }
    private void load() throws IOException {
        FXMLLoaders.loadRoot(this);
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuBar.useSystemMenuBarProperty();
    }

    @Override
    public BorderPane getContentPane() {
        return contentPane;
    }

    @Override
    public MenuItemRootContainer<MenuItem, Menu> getMenuBarMenuContainer() {
        return menuBarMenuContainer;
    }

    @Override
    public ToolBarContainer<ToolBar, Node> getApplicationToolBarContainer() {
        return toolBarContainerPane;
    }

//    private static class SaveHandler implements EventHandler<ActionEvent> {
//
//        private final StringProperty displayName = new SimpleStringProperty(this, "displayName", null);
//        private final ObjectProperty<KeyCombination> accelerator = new SimpleObjectProperty<>(this, "accelerator", null);
//        private final BooleanProperty disabled = new SimpleBooleanProperty(this, "disabled", false);
//        private final ObjectProperty<Image> icon = new SimpleObjectProperty<>(this, "icon", null);
//
//        public StringProperty displayNameProperty() {
//            return displayName;
//        }
//
//        public final String getDisplayName() {
//            return displayNameProperty().get();
//        }
//
//        public final void setDisplayName(String displayName) {
//            displayNameProperty().set(displayName);
//        }
//
//        public final KeyCombination getAccelerator() {
//            return acceleratorProperty().get();
//        }
//
//        public final void setAccelerator(KeyCombination keyCombination) {
//            acceleratorProperty().set(keyCombination);
//        }
//
//        public ObjectProperty<KeyCombination> acceleratorProperty() {
//            return accelerator;
//        }
//
//        public final boolean isDisabled() {
//            return disabledProperty().get();
//        }
//
//        public final void setDisabled(boolean disabled) {
//            disabledProperty().set(disabled);
//        }
//
//        public BooleanProperty disabledProperty() {
//            return disabled;
//        }
//
//        public final Image getIcon() {
//            return iconProperty().get();
//        }
//
//        public final void setIcon(Image icon) {
//            iconProperty().set(icon);
//        }
//
//        public ObjectProperty<Image> iconProperty() {
//            return icon;
//        }
//
//        @Override
//        public void handle(ActionEvent arg0) {
//            System.out.println("Save");
//        }
//    }
}