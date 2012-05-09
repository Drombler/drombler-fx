/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.docking.DockablePane;
import org.richclientplatform.core.docking.spi.DockablePreferences;
import org.richclientplatform.core.docking.spi.DockablePreferencesManager;

/**
 *
 * @author puce
 */
@Component
@Service
public class DockablePreferencesManagerImpl implements DockablePreferencesManager<DockablePane> {

    private final Map<Class<?>, DockablePreferences> defaultDockablePreferencesMap = new HashMap<>();
    private final Map<DockablePane, DockablePreferences> dockablePreferencesMap = new HashMap<>();

    @Override
    public DockablePreferences getDockablePreferences(DockablePane dockable) {
        if (dockablePreferencesMap.containsKey(dockable)) {
            return dockablePreferencesMap.get(dockable);
        } else {
            return defaultDockablePreferencesMap.get(dockable.getClass());
        }
    }

    @Override
    public void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences) {
        defaultDockablePreferencesMap.put(dockableClass, dockablePreferences);
    }

    @Override
    public void registerDockablePreferences(DockablePane dockable, DockablePreferences dockablePreferences) {
        dockablePreferencesMap.put(dockable, dockablePreferences);
    }

    @Override
    public DockablePreferences unregisterDockablePreferences(DockablePane dockable) {
        return dockablePreferencesMap.remove(dockable);
    }

    @Override
    public void reset() {
        dockablePreferencesMap.clear();
    }
}
