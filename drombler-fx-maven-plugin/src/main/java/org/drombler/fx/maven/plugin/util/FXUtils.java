/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.maven.plugin.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author puce
 */
public class FXUtils {

    public static Map<String, String> getManifestEntries(String mainClass) {
        Map<String, String> manifestEntries = new LinkedHashMap<>(1);
        manifestEntries.put("Main-Class", mainClass);
        return manifestEntries;
    }


}
