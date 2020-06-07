package org.drombler.fx.maven.plugin.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ModuleNameUtils {
    public static final String GROUP_ID_DELIMITER_REGEX = "\\.";
    public static final String ARTIFACT_ID_DELIMITER_REGEX = "-";
    public static final String MODULE_NAME_DELIMITER = ".";

    private ModuleNameUtils() {
    }

    public static String calculateAutomaticModuleName(String groupId, String artifactId) {
        String[] groupIdParts = groupId.split(GROUP_ID_DELIMITER_REGEX);
        String[] artifactIdParts = artifactId.split(ARTIFACT_ID_DELIMITER_REGEX);

        List<String> automaticModuleNamePrefixParts = new ArrayList<>(Arrays.asList(groupIdParts));
        String automaticModuleNameSuffix = String.join(MODULE_NAME_DELIMITER, artifactIdParts);

        if (automaticModuleNamePrefixParts.contains(artifactIdParts[0])) {
            int startIndex = automaticModuleNamePrefixParts.indexOf(artifactIdParts[0]);
            String duplicateParts = String.join(MODULE_NAME_DELIMITER, automaticModuleNamePrefixParts.subList(startIndex, automaticModuleNamePrefixParts.size()));
            if (automaticModuleNameSuffix.startsWith(duplicateParts)) {
                return String.join(MODULE_NAME_DELIMITER, automaticModuleNamePrefixParts.subList(0, startIndex))
                        + MODULE_NAME_DELIMITER + automaticModuleNameSuffix;
            }
        }
        return String.join(MODULE_NAME_DELIMITER, groupIdParts) + MODULE_NAME_DELIMITER + automaticModuleNameSuffix;
    }

}
