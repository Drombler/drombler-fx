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
import java.util.Locale;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.collections.SetChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
    private boolean initialized = false;
    private ObjectProperty<ColoredCircle> coloredCircle = new SimpleObjectProperty<>(this, "coloredCircle");

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
            }
        });

        // Add a ColoredRectangleManager to the context to enable the ColoredRectangle actions.
        context.add(new ColoredRectangleManager() {
            @Override
            public ObservableSet<ColoredRectangle> getColoredRectangles() {
                return SampleEditorPane.this.sample.getColoredRectangles();
            }
        });

        // Keep this control in sync with the Sample
        nameField.textProperty().bindBidirectional(sample.nameProperty());
        coloredCircle.bindBidirectional(sample.coloredCircleProperty());
        titleProperty().bind(sample.nameProperty());

        // Mark this Editor as modified if any control has been modified
        nameField.textProperty().addListener(new ModifiedListener());
        coloredCircle.addListener(new ModifiedListener());
        sample.getColoredRectangles().addListener(new SetChangeListener<ColoredRectangle>() {

            @Override
            public void onChanged(Change<? extends ColoredRectangle> change) {
                markModified();
            }
        });

        initialized = true;
    }

    private void loadFXML() throws IOException {
        FXMLLoaders.loadRoot(this);
    }

    public Sample getSample() {
        return sample;
    }

    private void markModified() {
        if (initialized && context.find(SampleSavable.class) == null) {
            context.add(new SampleSavable());
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
            // Write to file/ db ...
            context.remove(this);
        }

        @Override
        public String getDisplayString(Locale inLocale) {
            return "Sample: " + nameField.getText();
        }
    }
}
