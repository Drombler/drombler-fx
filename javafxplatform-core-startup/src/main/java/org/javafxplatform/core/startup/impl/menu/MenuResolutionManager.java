/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.osgi.framework.ServiceReference;
import org.richclientplatform.core.action.processing.MenuDescriptor;
import org.richclientplatform.core.action.processing.MenuEntryDescriptor;


/**
 *
 * @author puce
 */
public class MenuResolutionManager {

    private final Map<String, MenuResolutionManager> unresolvedMenuItemContainers = new HashMap<>();
    private final Map<String, List<MenuDescriptor>> unresolvedMenuDescriptors = new HashMap<>();
    private final Map<String, List<ServiceReference<MenuEntryDescriptor>>> unresolvedMenuEntryDescriptors = new HashMap<>();

    public void addUnresolvedMenu(String pathId, MenuDescriptor menuDescriptor) {
        if (!unresolvedMenuDescriptors.containsKey(pathId)) {
            unresolvedMenuDescriptors.put(pathId, new ArrayList<MenuDescriptor>());
        }
        unresolvedMenuDescriptors.get(pathId).add(menuDescriptor);
    }

    public boolean containsUnresolvedMenus(String pathId) {
        return unresolvedMenuDescriptors.containsKey(pathId);
    }

    public List<MenuDescriptor> removeUnresolvedMenus(String pathId) {
        return unresolvedMenuDescriptors.remove(pathId);
    }

    public MenuResolutionManager getMenuResolutionManager(String pathId) {
        if (!unresolvedMenuItemContainers.containsKey(pathId)) {
            unresolvedMenuItemContainers.put(pathId, new MenuResolutionManager());
        }
        return unresolvedMenuItemContainers.get(pathId);
    }

    public void addUnresolvedMenuEntry(String pathId, ServiceReference<MenuEntryDescriptor> menuEntryDescriptor) {
        if (!unresolvedMenuEntryDescriptors.containsKey(pathId)) {
            unresolvedMenuEntryDescriptors.put(pathId, new ArrayList<ServiceReference<MenuEntryDescriptor>>());
        }
        unresolvedMenuEntryDescriptors.get(pathId).add(menuEntryDescriptor);
    }

    public List<ServiceReference<MenuEntryDescriptor>> removeUnresolvedMenuEntries(String pathId) {
        return unresolvedMenuEntryDescriptors.remove(pathId);
    }

    public boolean containsUnresolvedMenuEntries(String pathId) {
        return unresolvedMenuEntryDescriptors.containsKey(pathId);
    }
}
