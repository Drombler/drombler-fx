#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import ${package}.sample.ColoredCircle;
import ${package}.sample.ColoredCircleManager;
import ${package}.sample.ColoredRectangle;
import ${package}.sample.ColoredRectangleManager;
import ${package}.sample.Sample;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.drombler.acp.core.docking.EditorDocking;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.docking.Deselect;
import org.drombler.commons.docking.DockableDataSensitive;
import org.drombler.commons.docking.Select;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.fx.core.docking.FXDockableDataUtils;



@EditorDocking(contentType = SampleHandler.class)
public class SampleEditorPane extends GridPane implements LocalContextProvider, DockableDataSensitive<FXDockableData> {

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final SimpleContext context = new SimpleContext(contextContent);
    private final SampleHandler sampleHandler;
    @FXML
    private TextField nameField;
    @FXML
    private ImageView coloredCircleImageView;
    @FXML
    private ImageView redRectangleImageView;
    @FXML
    private ImageView yellowRectangleImageView;
    @FXML
    private ImageView blueRectangleImageView;
    private final Map<ColoredRectangle, ImageView> coloredRectangleImageViews = new EnumMap<>(ColoredRectangle.class);
    private ObjectProperty<ColoredCircle> coloredCircle = new SimpleObjectProperty<>(this, "coloredCircle");
    private final ObservableSet<ColoredRectangle> coloredRectangles = FXCollections.observableSet(EnumSet.noneOf(
            ColoredRectangle.class));
    private FXDockableData dockableData;

    public SampleEditorPane(SampleHandler sampleHandler) {
        loadFXML();
        this.sampleHandler = sampleHandler;

        // Add a ColoredCircleManager to the context to enable the ColoredCircle actions.
        contextContent.add(new ColoredCircleManager() {
            @Override
            public ColoredCircle getColoredCircle() {
                return coloredCircle.get();
            }

            @Override
            public void setColoredCircle(ColoredCircle coloredCircle) {
                SampleEditorPane.this.coloredCircle.set(coloredCircle);
                coloredCircleImageView.setImage(coloredCircle.getImage());
            }
        });

        // Add a ColoredRectangleManager to the context to enable the ColoredRectangle actions.
        contextContent.add((ColoredRectangleManager) () -> coloredRectangles);

        initColoredRectangleImageViewsMap();

        configureEditorContent(sampleHandler.getSample());
        initColoredRectangleImageViews();

        // Mark this Editor as modified if any control has been modified
        nameField.textProperty().addListener(new ModifiedListener());
        coloredCircle.addListener(new ModifiedListener());
        coloredRectangles.addListener((Change<? extends ColoredRectangle> change) -> {
            if (change.wasAdded()) {
                setColoredRectangleImage(change.getElementAdded());
            } else if (change.wasRemoved()) {
                    coloredRectangleImageViews.get(change.getElementRemoved()).setImage(null);
                }
            markModified();
        });

    }

    private void configureEditorContent(Sample sample) {
        nameField.setText(sample.getName());
        configureNameField(sample);
        coloredCircle.set(sample.getColoredCircle());
        coloredCircleImageView.setImage(sample.getColoredCircle().getImage());
        coloredRectangles.addAll(sample.getColoredRectangles());
    }

    private void configureNameField(Sample sample) {
        nameField.setEditable(sample.getName() == null);
        nameField.setFocusTraversable(sample.getName() == null);
    }

    private void loadFXML() {
        FXMLLoaders.loadRoot(this, ResourceBundleUtils.getPackageResourceBundle(SampleEditorPane.class));
    }

    @Override
    public Context getLocalContext() {
        return context;
    }

    @Override
    public void setDockableData(FXDockableData dockableData) {
        this.dockableData = dockableData;

        FXDockableDataUtils.configureDockableData(dockableData, sampleHandler, "Sample");
        nameField.setText(this.dockableData.getTitle());
    }

    private void initColoredRectangleImageViewsMap() {
        coloredRectangleImageViews.put(ColoredRectangle.RED, redRectangleImageView);
        coloredRectangleImageViews.put(ColoredRectangle.YELLOW, yellowRectangleImageView);
        coloredRectangleImageViews.put(ColoredRectangle.BLUE, blueRectangleImageView);
    }

    private void markModified() {
        if (context.find(SampleSavable.class) == null) {
            // Add a SampleSavable to the context to enable the Save and the "Save All" actions.
            contextContent.add(new SampleSavable());
        }
    }

    private void setColoredRectangleImage(ColoredRectangle coloredRectangle) {
        coloredRectangleImageViews.get(coloredRectangle).setImage(coloredRectangle.getImage());
    }

    private void initColoredRectangleImageViews() {
        Arrays.stream(ColoredRectangle.values())
                .filter(coloredRectangles::contains)
                .forEach(this::setColoredRectangleImage);
    }

    @Select
    public void select() {
        System.out.println("Selected sample: " + dockableData.getTitle());
    }

    @Deselect
    public void deselect() {
        System.out.println("Deselected sample: " + dockableData.getTitle());
    }

    private class ModifiedListener implements ChangeListener<Object> {

        @Override
        public void changed(ObservableValue<?> ov, Object t, Object t1) {
            markModified();
        }
    }

    private class SampleSavable implements Savable {

        @Override
        public void save() {
            updateSample(sampleHandler.getSample());
            sampleHandler.save();
            configureNameField(sampleHandler.getSample());
            FXDockableDataUtils.configureDockableData(dockableData, sampleHandler, "Sample");

            contextContent.remove(this);
        }

        private void updateSample(Sample sample) {
            sample.setName(nameField.getText());
            sample.setColoredCircle(coloredCircle.get());
            sample.getColoredRectangles().addAll(coloredRectangles);
            sample.getColoredRectangles().retainAll(coloredRectangles);
        }

        @Override
        public String getDisplayString(Locale inLocale) {
            return "Sample: " + nameField.getText();
        }
    }
}
