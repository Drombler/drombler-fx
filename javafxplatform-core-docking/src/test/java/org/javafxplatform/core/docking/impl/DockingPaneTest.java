/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.Parent;
import org.javafxplatform.core.docking.DockablePane;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.richclientplatform.core.docking.processing.DockingAreaDescriptor;

/**
 *
 * @author puce
 */
public class DockingPaneTest {
    public static final String TEST1 = "test1";
    private DockingPane dockingPane;
    
    public DockingPaneTest() {
    }
    
    @Before
    public void setUp() {
        dockingPane = new DockingPane();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addDockingArea method, of class DockingPane.
     */
    @Test
    public void testAddDockingArea() {
        System.out.println("addDockingArea");
        DockingAreaDescriptor dockingAreaDescriptor = new DockingAreaDescriptor();
        dockingAreaDescriptor.setId(TEST1);
        dockingAreaDescriptor.setPosition(20);
        dockingAreaDescriptor.setPath(Arrays.asList(20));
        
        dockingPane.addDockingArea(dockingAreaDescriptor);
        DockablePane dockablePane = new DockablePane();
        dockingPane.addDockable(TEST1, dockablePane);
        DockingAreaPane dockingArea = dockingPane.getDockingArea(TEST1);
        DockingSplitPane parentSplitPane = dockingArea.getParentSplitPane();
        List<Integer> path = new ArrayList<>();
        while (parentSplitPane != null){
            path.add(parentSplitPane.getPosition());
            parentSplitPane = parentSplitPane.getParentSplitPane();
        }
        Collections.reverse(path);
        assertEquals(Arrays.asList(0), path);
    }

}
