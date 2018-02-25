package org.drombler.fx.core.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.scene.Node;
import org.drombler.acp.core.settings.spi.CustomSettingsCategoryDescriptor;
import org.drombler.acp.core.settings.spi.ParentOnlySettingsCategoryDescriptor;
import org.drombler.acp.core.settings.spi.SettingsCategoryContainer;
import org.drombler.commons.settings.fx.SettingsCategory;
import org.drombler.commons.settings.fx.SettingsPane;
import org.osgi.service.component.annotations.Component;
import org.softsmithy.lib.util.PositionableAdapter;
import org.softsmithy.lib.util.Positionables;

/**
 *
 * @author puce
 */
@Component
public class FXSettingsCategoryContainer implements SettingsCategoryContainer<Node> {

    private final SettingsPane settingsPane = new SettingsPane();

    private final Map<CategoryId, List<SettingsCategory>> subCategoriesMap = new HashMap<>();
    private final Map<CategoryId, List<PositionableAdapter<SettingsCategory>>> positionableSubCategoriesMap = new HashMap<>();
    private final Map<CategoryId, List<PositionableAdapter<SettingsCategory>>> unresolvedSubCategoriesMap = new HashMap<>();

    public FXSettingsCategoryContainer() {
        CategoryId rootId = new CategoryId(Collections.emptyList());
        subCategoriesMap.put(rootId, settingsPane.getTopCategories());
        positionableSubCategoriesMap.put(rootId, new ArrayList<>());
    }

    
    @Override
    public void addCategory(ParentOnlySettingsCategoryDescriptor settingsCategoryDescriptor) {
        SettingsCategory settingsCategory = new SettingsCategory();
        settingsCategory.setId(settingsCategoryDescriptor.getId());
        settingsCategory.setDisplayName(settingsCategoryDescriptor.getDisplayName());
        settingsCategory.setDisplayDescription(settingsCategoryDescriptor.getDisplayDescription());

        addCategory(settingsCategoryDescriptor.getPath(), new PositionableAdapter<>(settingsCategory, settingsCategoryDescriptor.getPosition()));
    }

    @Override
    public void addCategory(CustomSettingsCategoryDescriptor<? extends Node> settingsCategoryDescriptor) {
        SettingsCategory settingsCategory = new SettingsCategory();
        settingsCategory.setId(settingsCategoryDescriptor.getId());
        settingsCategory.setDisplayName(settingsCategoryDescriptor.getDisplayName());
        settingsCategory.setDisplayDescription(settingsCategoryDescriptor.getDisplayDescription());
        settingsCategory.setContentPaneType(settingsCategoryDescriptor.getContentPaneClass());

        addCategory(settingsCategoryDescriptor.getPath(), new PositionableAdapter<>(settingsCategory, settingsCategoryDescriptor.getPosition()));
    }

    private void addCategory(List<String> path, PositionableAdapter<SettingsCategory> settingsCategory) {
        CategoryId parentId = new CategoryId(path);
        if (subCategoriesMap.containsKey(parentId)) {
            addCategory(parentId, settingsCategory);
        } else {
            addUnresolvedCategory(parentId, settingsCategory);
        }
    }

    private void addCategory(CategoryId parentId, PositionableAdapter<SettingsCategory> settingsCategory) {
        List<PositionableAdapter<SettingsCategory>> orderedSettingsCategories = positionableSubCategoriesMap.get(parentId);
        int insertionPoint = Positionables.getInsertionPoint(orderedSettingsCategories, settingsCategory);
        orderedSettingsCategories.add(insertionPoint, settingsCategory);
        subCategoriesMap.get(parentId).add(insertionPoint, settingsCategory.getAdapted());
        
        CategoryId id = registerCategory(parentId, settingsCategory);
        resolveUnresolvedCategories(id);
    }

    private CategoryId registerCategory(CategoryId parentId, PositionableAdapter<SettingsCategory> settingsCategory) {
        CategoryId id = parentId.resolve(settingsCategory.getAdapted().getId());
        subCategoriesMap.put(id, settingsCategory.getAdapted().getSubCategories());
        positionableSubCategoriesMap.put(id, new ArrayList<>());
        return id;
    }

    private void addUnresolvedCategory(CategoryId parentId, PositionableAdapter<SettingsCategory> settingsCategory) {
        if (!unresolvedSubCategoriesMap.containsKey(parentId)) {
            unresolvedSubCategoriesMap.put(parentId, new ArrayList<>());
        }
        unresolvedSubCategoriesMap.get(parentId).add(settingsCategory);
    }

    private void resolveUnresolvedCategories(CategoryId id) {
        if (unresolvedSubCategoriesMap.containsKey(id)) {
            List<PositionableAdapter<SettingsCategory>> unresolvedSubCategories = unresolvedSubCategoriesMap.remove(id);
            unresolvedSubCategories.forEach(subCategory -> addCategory(id, subCategory));
        }
    }

    private static class CategoryId {

        private final List<String> fullQualifiedIdPath;

        public CategoryId(List<String> fullQualifiedIdPath) {
            this.fullQualifiedIdPath = fullQualifiedIdPath;
        }
        
        public boolean isRoot(){
            return fullQualifiedIdPath.isEmpty();
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + Objects.hashCode(this.fullQualifiedIdPath);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CategoryId)) {
                return false;
            }

            CategoryId other = (CategoryId) obj;
            return Objects.equals(this.fullQualifiedIdPath, other.fullQualifiedIdPath);
        }

        public CategoryId resolve(String id) {
            List<String> path = new ArrayList<>(this.fullQualifiedIdPath);
            path.add(id);

            return new CategoryId(path);
        }

    }
}
