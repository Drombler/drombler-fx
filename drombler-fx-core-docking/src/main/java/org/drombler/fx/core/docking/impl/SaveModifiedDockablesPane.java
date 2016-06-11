package org.drombler.fx.core.docking.impl;

import java.util.Iterator;
import java.util.List;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class SaveModifiedDockablesPane extends BorderPane {


    private final EmptyProperty empty = new EmptyProperty();

    @FXML
    private ListView<FXDockableEntry> modifiedDockablesListView;

    public SaveModifiedDockablesPane(List<FXDockableEntry> modifiedDockableEntries) {
        FXMLLoaders.loadRoot(this);
        modifiedDockablesListView.setCellFactory((ListView<FXDockableEntry> listView) -> new DockableEntryListCell());
        modifiedDockablesListView.getItems().setAll(modifiedDockableEntries);
        modifiedDockablesListView.getItems().addListener((ListChangeListener.Change<? extends FXDockableEntry> c) -> {
            if (modifiedDockablesListView.getItems().isEmpty()) {
                empty.set(true);
            }
        });
        if (modifiedDockablesListView.getItems().isEmpty()) {
            empty.set(true);
        }
        modifiedDockablesListView.getSelectionModel().select(0);
        modifiedDockablesListView.requestFocus();
    }

    public boolean isEmpty() {
        return emptyProperty().get();
    }

    public ReadOnlyBooleanProperty emptyProperty() {
        return empty;
    }

    @FXML
    private void save(ActionEvent event) {
        Node selectedDockable = modifiedDockablesListView.getSelectionModel().getSelectedItem().getDockable();
        Contexts.find(selectedDockable, Savable.class).save();
        if (Contexts.find(selectedDockable, Savable.class) == null) {
            removeSelectedFromList();
        }
    }

    private void removeSelectedFromList() {
        modifiedDockablesListView.getItems().remove(modifiedDockablesListView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void discard(ActionEvent event) {
        removeSelectedFromList();
    }

    @FXML
    private void saveAll(ActionEvent event) {
        for (Iterator<FXDockableEntry> iterator = modifiedDockablesListView.getItems().iterator(); iterator.hasNext();) {
            Node dockable = iterator.next().getDockable();
            Contexts.find(dockable, Savable.class).save();
            if (Contexts.find(dockable, Savable.class) == null) {
                iterator.remove();
            }
        }
    }

    @FXML
    private void discardAll(ActionEvent event) {
        modifiedDockablesListView.getItems().clear();
    }

    private class DockableEntryListCell extends ListCell<FXDockableEntry> {

        @Override
        protected void updateItem(FXDockableEntry item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                setTooltip(null);
            } else {
                FXDockableData dockableData = item.getDockableData();
                setText(dockableData.getTitle());
                setGraphic(dockableData.getGraphicFactory().createGraphic(16)); // TODO: avoid creating new graphic nodes (dockableData.getGraphic() cannot be used since it already has a parent (Tab))
                setTooltip(dockableData.getTooltip());
            }
        }

    }

    private class EmptyProperty extends ReadOnlyBooleanPropertyBase {

        private boolean empty = false;

        @Override
        public final boolean get() {
            return empty;
        }

        private void set(boolean newValue) {
            if (empty != newValue) {
                empty = newValue;
                fireValueChangedEvent();
            }
        }

        @Override
        public Object getBean() {
            return SaveModifiedDockablesPane.this;
        }

        @Override
        public String getName() {
            return "empty";
        }
    }
}