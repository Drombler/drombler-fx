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
import java.io.IOException;
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
import javafx.collections.SetChangeListener;
import javafx.collections.SetChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.drombler.acp.core.commons.util.context.SimpleContext;
import org.drombler.acp.core.docking.EditorDocking;
import org.drombler.acp.core.standard.action.Savable;
import org.drombler.fx.core.commons.fx.fxml.FXMLLoaders;
import org.drombler.fx.core.docking.DockablePane;

@EditorDocking(areaId = "center")
public class SampleEditorPane extends DockablePane {

    private final SimpleContext context = new SimpleContext();
    private final Sample sample;
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
//    private boolean initialized = false;
    private ObjectProperty<ColoredCircle> coloredCircle = new SimpleObjectProperty<>(this, "coloredCircle");
    private final ObservableSet<ColoredRectangle> coloredRectangles = FXCollections.observableSet(EnumSet.noneOf(
            ColoredRectangle.class));

    public SampleEditorPane(Sample sample) throws IOException {
        // Set a writeable context
        setContext(context);

        loadFXML();
        this.sample = sample;

        // Add the sample to the context, so Viewers can see it
        context.add(sample);

        // Add a ColoredCircleManager to the context to enable the ColoredCircle actions.
        context.add(new ColoredCircleManager() {
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
        context.add(new ColoredRectangleManager() {
            @Override
            public ObservableSet<ColoredRectangle> getColoredRectangles() {
                return coloredRectangles;
            }
        });

        initColoredRectangleImageViewsMap();

        nameField.setText(sample.getName());
        coloredCircle.set(sample.getColoredCircle());
        coloredCircleImageView.setImage(sample.getColoredCircle().getImage());
        coloredRectangles.addAll(sample.getColoredRectangles());
        initColoredRectangleImageViews();

        titleProperty().bind(nameField.textProperty());

        // Mark this Editor as modified if any control has been modified
        nameField.textProperty().addListener(new ModifiedListener());
        coloredCircle.addListener(new ModifiedListener());
        coloredRectangles.addListener(new SetChangeListener<ColoredRectangle>() {
            @Override
            public void onChanged(Change<? extends ColoredRectangle> change) {
                if (change.wasAdded()) {
                    setColoredRectangleImage(change.getElementAdded());
                } else if (change.wasRemoved()) {
                    coloredRectangleImageViews.get(change.getElementRemoved()).setImage(null);
                }
                markModified();
            }
        });

//        initialized = true;
    }

    private void loadFXML() throws IOException {
        FXMLLoaders.loadRoot(this);
    }

    private void initColoredRectangleImageViewsMap() {
        coloredRectangleImageViews.put(ColoredRectangle.RED, redRectangleImageView);
        coloredRectangleImageViews.put(ColoredRectangle.YELLOW, yellowRectangleImageView);
        coloredRectangleImageViews.put(ColoredRectangle.BLUE, blueRectangleImageView);
    }

    public Sample getSample() {
        return sample;
    }

    private void markModified() {
//        if (initialized && context.find(SampleSavable.class) == null) {
        if (context.find(SampleSavable.class) == null) {
            context.add(new SampleSavable());
        }
    }

    private void setColoredRectangleImage(ColoredRectangle coloredRectangle) {
        coloredRectangleImageViews.get(coloredRectangle).setImage(coloredRectangle.getImage());
    }

    private void initColoredRectangleImageViews() {
        for (ColoredRectangle coloredRectangle : ColoredRectangle.values()) {
            if (coloredRectangles.contains(coloredRectangle)) {
                setColoredRectangleImage(coloredRectangle);
            }
        }
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
            System.out.println("Save " + getDisplayString(Locale.getDefault()));
            sample.setName(nameField.getText());
            sample.setColoredCircle(coloredCircle.get());
            sample.getColoredRectangles().addAll(coloredRectangles);
            sample.getColoredRectangles().retainAll(coloredRectangles);
            // Write to file/ db ...
            context.remove(this);
        }

        @Override
        public String getDisplayString(Locale inLocale) {
            return "Sample: " + nameField.getText();
        }
    }
}