/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl.menu;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.References;
import org.javafxplatform.core.startup.impl.MenuBarProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.richclientplatform.core.action.ActionListener;
import org.richclientplatform.core.action.processing.ActionDescriptor;
import org.richclientplatform.core.action.processing.ActionsMap;
import org.richclientplatform.core.action.processing.MenuDescriptor;
import org.richclientplatform.core.action.processing.MenuEntryDescriptor;

/**
 *
 * @author puce
 */
@Component(immediate = true)
@References({
    @Reference(name = "menuBarProvider", referenceInterface = MenuBarProvider.class),
    @Reference(name = "menuDescriptor", referenceInterface = MenuDescriptor.class,
    cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC),
    @Reference(name = "menuEntryDescriptor", referenceInterface = MenuEntryDescriptor.class,
    cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC)})
public class MenuHandler {

    private static final String ROOT_PATH_ID = "";
    private static final int ICON_SIZE = 16;
    private static final boolean SMOOTH_ICON = false;
    private MenuItemContainer rootContainer;
    private final MenuResolutionManager menuResolutionManager = new MenuResolutionManager();

    protected void bindMenuBarProvider(MenuBarProvider menuBarProvider) {
        rootContainer = new MenuBarMenuContainer(menuBarProvider.getMenuBar());
        resolveUnresolvedItems(menuResolutionManager, ROOT_PATH_ID);
    }

    protected void unbindMenuBarProvider(MenuBarProvider menuBarProvider) {
        rootContainer = null;
    }

    public void bindMenuDescriptor(MenuDescriptor menuDescriptor) {
        MenuItemContainer parentContainer = getParent(menuDescriptor.getPath());
        if (parentContainer != null) {
            Menu menu = new Menu(menuDescriptor.getDisplayName());
            menu.setMnemonicParsing(true);
            parentContainer.addMenu(menuDescriptor.getId(),
                    MenuItemWrapper.wrapMenuItem(menu, menuDescriptor.getPosition()));
            MenuResolutionManager manager = getMenuResolutionManager(menuDescriptor.getPath());
            resolveUnresolvedItems(manager, menuDescriptor.getId());
        } else {
            registerUnresolvedMenu(menuDescriptor);
        }
    }

    public void unbindMenuDescriptor(MenuDescriptor menuDescriptor) {
    }

    @SuppressWarnings("unchecked")
    public void bindMenuEntryDescriptor(ServiceReference<MenuEntryDescriptor> serviceReference) {
        Bundle bundle = serviceReference.getBundle();
        BundleContext context = bundle.getBundleContext();
        MenuEntryDescriptor menuEntryDescriptor = context.getService(serviceReference);

        MenuItemContainer parentContainer = getParent(menuEntryDescriptor.getPath());
        if (parentContainer != null) {
            ActionsMap actionsMap = new ActionsMap();
            ActionDescriptor action = actionsMap.getAction(menuEntryDescriptor.getActionId(), context);
            final MenuItem menuItem = new MenuItem(action.getDisplayName());
            menuItem.setMnemonicParsing(true);
            if (action.getAccelerator() != null && !action.getAccelerator().equals("")) {
                menuItem.setAccelerator(KeyCombination.keyCombination(action.getAccelerator()));
            }
            Object listener = action.getListener();
            if (listener instanceof ActionListener) {
                listener = new ActionEventHandler((ActionListener) listener);
            }
            menuItem.setOnAction((EventHandler<ActionEvent>) listener);

//            BufferedImage image;
            if (action.getIcon() != null && !action.getIcon().equals("")) {
                InputStream imageInputStream = getImageInputStream(action);
                if (imageInputStream != null) {
                    try (InputStream is = imageInputStream) {
                        //                        image = ImageIO.read(is);
                        //                        ImageIO.write(image, "gif", new File("target/" + action.getIcon()));
                        //                    } catch (IOException ex) {
                        //                        Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
                        //                    }
                        //                    Platform.runLater(new Runnable() {
                        //
                        //                        @Override
                        //                        public void run() {
                        //                            try (InputStream is = imageInputStream) {
                        //                            try{
                        menuItem.setGraphic(new ImageView(new Image(is, ICON_SIZE, ICON_SIZE, true,
                                SMOOTH_ICON)));
                    } catch (Exception ex) {
                        Logger.getLogger(MenuHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //                        }
                    //                    });
                }
            }
//        if (parentContainer.isSupportingItems()) {
            parentContainer.addMenuItem(MenuItemWrapper.wrapMenuItem(menuItem, menuEntryDescriptor.getPosition()));
//        }
        } else {
            registerUnresolvedMenuEntry(serviceReference);
        }
    }

    public void unbindMenuEntryDescriptor(MenuEntryDescriptor menuEntryDescriptor) {
    }

//    public void addingSeparator(List<String> path, int index) {
//        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
//        MenuItemContainer parentContainer = getParent(path);
////        if (parentContainer.isSupportingItems()) {
//        parentContainer.addMenuItem(separatorMenuItem, index);
////        }
//    }
    private MenuItemContainer getParent(List<String> path) {
        MenuItemContainer parentContainer = rootContainer;
        for (String pathId : path) {
            if (parentContainer != null) {
                parentContainer = parentContainer.getMenuContainer(pathId);
            } else {
                break;
            }
        }
        return parentContainer;
    }

    private void registerUnresolvedMenu(MenuDescriptor menuDescriptor) {
        MenuItemContainer parentContainer = rootContainer;
        MenuResolutionManager container = this.menuResolutionManager;
//        MenuItemContainer lastResolvedParentContainer = parentContainer;
        String firstUnresolvedPathId = ROOT_PATH_ID;
        if (parentContainer != null) { // rootContainer is available
            container = container.getMenuResolutionManager(firstUnresolvedPathId);
            for (String pathId : menuDescriptor.getPath()) {
                parentContainer = parentContainer.getMenuContainer(pathId);
                if (parentContainer != null) {
                    container = container.getMenuResolutionManager(pathId);
                } else {
                    firstUnresolvedPathId = pathId;
                    break;
                }
            }
        }
        container.addUnresolvedMenu(firstUnresolvedPathId, menuDescriptor);
    }

    private void resolveUnresolvedItems(MenuResolutionManager container, String pathId) {
        if (container.containsUnresolvedMenus(pathId)) {
            for (MenuDescriptor menuDescriptor : container.removeUnresolvedMenus(pathId)) {
                bindMenuDescriptor(menuDescriptor);
            }
        }
        if (container.containsUnresolvedMenuEntries(pathId)) {
            for (ServiceReference<MenuEntryDescriptor> serviceReference : container.removeUnresolvedMenuEntries(pathId)) {
                bindMenuEntryDescriptor(serviceReference);
            }
        }
    }

    private MenuResolutionManager getMenuResolutionManager(List<String> path) {
        MenuResolutionManager manager = menuResolutionManager.getMenuResolutionManager(ROOT_PATH_ID);
        for (String pathId : path) {
            manager = manager.getMenuResolutionManager(pathId);
        }
        return manager;
    }

    private void registerUnresolvedMenuEntry(ServiceReference<MenuEntryDescriptor> serviceReference) {
        MenuEntryDescriptor menuEntryDescriptor = serviceReference.getBundle().getBundleContext().getService(
                serviceReference);

        MenuItemContainer parentContainer = rootContainer;
        MenuResolutionManager container = this.menuResolutionManager;
//        MenuItemContainer lastResolvedParentContainer = parentContainer;
        String firstUnresolvedPathId = ROOT_PATH_ID;
        if (parentContainer != null) { // rootContainer is available
            container = container.getMenuResolutionManager(firstUnresolvedPathId);
            for (String pathId : menuEntryDescriptor.getPath()) {
                parentContainer = parentContainer.getMenuContainer(pathId);
                if (parentContainer != null) {
                    container = container.getMenuResolutionManager(pathId);
                } else {
                    firstUnresolvedPathId = pathId;
                    break;
                }
            }
        }
        container.addUnresolvedMenuEntry(firstUnresolvedPathId, serviceReference);
    }

    private InputStream getImageInputStream(ActionDescriptor action) {
        String icon = action.getIcon();
        String[] iconNameParts = icon.split("\\.");
        if (iconNameParts.length > 0) {
            StringBuilder sb = new StringBuilder(iconNameParts[0]);
            sb.append(ICON_SIZE);
            for (int i = 1; i < iconNameParts.length; i++) {
                sb.append(".");
                sb.append(iconNameParts[i]);
            }
            icon = sb.toString();
        }
        return action.getListener().getClass().getResourceAsStream(icon);
    }
}
