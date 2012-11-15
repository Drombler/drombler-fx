/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.maven.plugins.pxb.util;

import org.drombler.fx.maven.plugin.util.FXUtils;
import com.sun.javafx.tools.ant.FXJar;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author puce
 */
public class FXUtilsTest {

    public FXUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of copyMainClasses method, of class FXUtils.
     */
    @Test
    public void testCopyMainClasses() throws Exception { 
       System.out.println("copyMainClasses");
        Path targetDir = Files.createTempDirectory(null);
//        URI uri = FXJar.class.getResource("/resources/classes").toURI();

//        for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
//            if (provider.getScheme().equalsIgnoreCase("jar")) {
//                Path path = provider.getPath(uri);
//                System.out.println(path); 
//           }
//        }
        FXUtils.copyMainClasses(targetDir);
    }}

