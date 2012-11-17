/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
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
