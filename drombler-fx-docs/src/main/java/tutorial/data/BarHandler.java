/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package tutorial.data;

import org.drombler.acp.core.data.AbstractDataHandler;
import org.drombler.acp.core.data.BusinessObjectHandler;

/**
 *
 * @author puce
 */
@BusinessObjectHandler(icon = "bar.png")
public class BarHandler extends AbstractDataHandler<String> {

    private final Bar bar;

    public BarHandler(Bar bar) {
        this.bar = bar;
    }

    @Override
    public String getTitle() {
        return bar.getId();
    }

    @Override
    public String getTooltipText() {
        return bar.getId();
    }

    @Override
    public String getUniqueKey() {
        return bar.getId();
    }

}
