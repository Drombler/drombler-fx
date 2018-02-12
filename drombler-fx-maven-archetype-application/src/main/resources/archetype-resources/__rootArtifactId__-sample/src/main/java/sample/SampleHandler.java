#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample;

import org.drombler.acp.core.data.AbstractDataHandler;
import org.drombler.acp.core.data.BusinessObjectHandler;


@BusinessObjectHandler(icon = "sample.png")
public class SampleHandler extends AbstractDataHandler<String> {

    private final SampleServiceClient client = new SampleServiceClient();
    private Sample sample;

    public SampleHandler(Sample sample) {
        this.sample = sample;
        this.sample.nameProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                throw new IllegalStateException("The name is the unique key of the sample and must noch change once set!");
            }
            getPropertyChangeSupport().firePropertyChange(UNIQUE_KEY_PROPERTY_NAME, oldValue, newValue);
            getPropertyChangeSupport().firePropertyChange(TITLE_PROPERTY_NAME, oldValue, newValue);
            getPropertyChangeSupport().firePropertyChange(TOOLTIP_TEXT_PROPERTY_NAME, oldValue, newValue);
        });
    }

    public Sample getSample() {
        if (isDirty()) {
            sample = client.getSample(sample.getName());
            markClean();
        }
        return sample;
    }

    @Override
    public String getUniqueKey() {
        return sample.getName();
    }

    public void save() {
        client.saveSample(sample);
    }

    @Override
    public String getTitle() {
        return sample.getName();
    }

    @Override
    public String getTooltipText() {
        return sample.getName();
    }

}
