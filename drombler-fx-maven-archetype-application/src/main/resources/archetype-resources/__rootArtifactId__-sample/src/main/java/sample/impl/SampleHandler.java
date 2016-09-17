#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import ${package}.sample.Sample;
import org.drombler.acp.core.data.AbstractDataHandler;
import org.drombler.acp.core.data.BusinessObjectHandler;

/**
 *
 * @author puce
 */


@BusinessObjectHandler(icon = "sample.png")
public class SampleHandler extends AbstractDataHandler<String> {

    private final Sample sample;

    public SampleHandler(Sample sample) {
        this.sample = sample;
    }

    /**
     * @return the sample
     */
    public Sample getSample() {
        return sample;
    }

    @Override
    public String getUniqueKey() {
        return sample.getName();
    }

}
