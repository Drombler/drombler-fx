#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample;

import java.util.EnumSet;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;



public class Sample {

    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final ObjectProperty<ColoredCircle> coloredCircle = new SimpleObjectProperty<>(this, "coloredCircle",
            ColoredCircle.RED);
    private final ObservableSet<ColoredRectangle> coloredRectangles = FXCollections.observableSet(EnumSet.noneOf(
            ColoredRectangle.class));

    public Sample() {
        this(null);
    }

    public Sample(String name) {
        setName(name);
    }

    public final ColoredCircle getColoredCircle() {
        return coloredCircleProperty().get();
    }

    public final void setColoredCircle(ColoredCircle coloredCircle) {
        coloredCircleProperty().set(coloredCircle);
    }

    public ObjectProperty<ColoredCircle> coloredCircleProperty() {
        return coloredCircle;
    }

    public ObservableSet<ColoredRectangle> getColoredRectangles() {
        return coloredRectangles;
    }

    public final String getName() {
        return nameProperty().get();
    }

    public final void setName(String name) {
        nameProperty().set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }
}
