package org.drombler.fx.core.settings;

import javafx.scene.Node;
import org.drombler.acp.core.settings.spi.CustomSettingsCategoryDescriptor;
import org.drombler.acp.core.settings.spi.ParentOnlySettingsCategoryDescriptor;
import org.drombler.acp.core.settings.spi.SettingsCategoryContainer;
import org.drombler.commons.settings.fx.SettingsCategory;
import org.drombler.commons.settings.fx.SettingsPane;

/**
 *
 * @author puce
 */
public class FXSettingsCategoryContainer implements SettingsCategoryContainer<Node> {

    private final SettingsPane settingsPane = new SettingsPane();

    @Override
    public void addCategory(ParentOnlySettingsCategoryDescriptor settingsCategoryDescriptor) {
        SettingsCategory settingsCategory = new SettingsCategory();
        settingsCategory.setId(settingsCategoryDescriptor.getId());
        settingsCategory.setDisplayName(settingsCategoryDescriptor.getDisplayName());
        settingsCategory.setDisplayDescription(settingsCategoryDescriptor.getDisplayDescription());
    }

    @Override
    public void addCategory(CustomSettingsCategoryDescriptor<? extends Node> settingsCategoryDescriptor) {
        SettingsCategory settingsCategory = new SettingsCategory();
        settingsCategory.setId(settingsCategoryDescriptor.getId());
        settingsCategory.setDisplayName(settingsCategoryDescriptor.getDisplayName());
        settingsCategory.setDisplayDescription(settingsCategoryDescriptor.getDisplayDescription());
        settingsCategory.setContentPaneType(settingsCategoryDescriptor.getContentPaneClass());
    }

}
