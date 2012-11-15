/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action.impl;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author puce
 */
class ToggleGroupManager {

    private final Map<String, ToggleGroup> toggleGroups = new HashMap<>();

    public void configureToggle(Toggle toggle, String toggleGroupId) {
        if (StringUtils.isNotEmpty(toggleGroupId)) {
            toggle.setToggleGroup(getToggleGroup(toggleGroupId));
        }
    }

    private ToggleGroup getToggleGroup(String toggleGroupId) {
        if (!toggleGroups.containsKey(toggleGroupId)) {
            ToggleGroup toggleGroup = new ToggleGroup();
            toggleGroups.put(toggleGroupId, toggleGroup);
        }
        return toggleGroups.get(toggleGroupId);
    }
}
