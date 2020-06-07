package org.drombler.fx.maven.plugin.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModuleNameUtilsTest {
    @Nested
    class CalculateAutomaticModuleName {

        @Test
        public void subset_noDuplicate() {
            String automaticModuleName = ModuleNameUtils.calculateAutomaticModuleName("com.mycompany.test", "test-application");
            assertEquals("com.mycompany.test.application", automaticModuleName);
        }

        @Test
        public void incompleteSubset_duplicate() {
            String automaticModuleName = ModuleNameUtils.calculateAutomaticModuleName("com.mycompany.test.foo", "test-application");
            assertEquals("com.mycompany.test.foo.test.application", automaticModuleName);
        }

        @Test
        public void noSubset_noDuplicate() {
            String automaticModuleName = ModuleNameUtils.calculateAutomaticModuleName("com.mycompany.foo", "test-application");
            assertEquals("com.mycompany.foo.test.application", automaticModuleName);
        }
    }
}