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
package org.drombler.fx.core.docking.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Orientation;
import org.drombler.acp.core.docking.spi.DockablePreferences;
import org.drombler.acp.core.docking.spi.DockablePreferencesManager;
import org.drombler.fx.core.docking.DockablePane;
import org.drombler.fx.core.docking.impl.skin.DockingPaneSkin;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author puce
 */
public class DockingPaneTest {

    private static final String TEST1 = "test1";
    private static final String TEST2 = "test2";
    private static final String TEST3 = "test3";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private static final String CENTER = "center";
    private final DockingPane dockingPane = new DockingPane();
    private final DockablePreferencesManager<DockablePane> dockablePreferencesManager = new DockablePreferencesManagerImpl();

//     static {
//        SimpleControlLauncher.main(new String[]{});
//    }
     
    @Before
    public void setUp() {
        dockingPane.setDockablePreferencesManager(dockablePreferencesManager);
        dockingPane.setSkin(new DockingPaneSkin(dockingPane));
    }

    /**
     * Test of addDockingArea method, of class DockingPane.
     */
    @Test
    public void testAddDockingArea1() {
        System.out.println("addDockingArea1");

        addDockingAreaNonPermanent(20, TEST1, 10);

        assertShortPath(TEST1, new ShortPathPart(10, 0));

        // No Exception should be thrown
        removeDockable(TEST1, 0);

        assertShortPath(TEST1);
    }

    @Test
    public void testAddDockingArea2() {
        System.out.println("addDockingArea2");
        addDockingAreaNonPermanent(20, TEST1, 10);

        assertShortPath(TEST1, new ShortPathPart(10, 0));

        addDockingAreaNonPermanent(40, TEST2, 10);

        assertShortPath(TEST2, new ShortPathPart(40, 1));

        assertShortPath(TEST1, new ShortPathPart(20, 1));

        // No Exception should be thrown
        removeDockable(TEST1, 0);

        assertShortPath(TEST1);

        assertShortPath(TEST2, new ShortPathPart(10, 0));

        // No Exception should be thrown
        removeDockable(TEST2, 0);

        assertShortPath(TEST2);
    }

    @Test
    public void testAddDockingArea3() {
        System.out.println("addDockingArea3");
        addDockingAreaNonPermanent(10, TEST1, 20);

        assertShortPath(TEST1, new ShortPathPart(20, 0));

        addDockingAreaNonPermanent(10, TEST2, 40);

        assertShortPath(TEST2, new ShortPathPart(40, 0));

        assertShortPath(TEST1, new ShortPathPart(20, 0));


        // No Exception should be thrown
        removeDockable(TEST2, 0);

        assertShortPath(TEST2);

        assertShortPath(TEST1, new ShortPathPart(20, 0));

        // No Exception should be thrown
        removeDockable(TEST1, 0);

        assertShortPath(TEST1);
    }

    @Test
    public void testAddDockingArea4() {
        System.out.println("addDockingArea4");
        addDockingAreaNonPermanent(20, TEST1, 10);

        assertShortPath(TEST1, new ShortPathPart(10, 0));

        addDockingAreaNonPermanent(40, TEST2, 10);

        assertShortPath(TEST2, new ShortPathPart(40, 1));

        assertShortPath(TEST1, new ShortPathPart(20, 1));

        addDockingAreaNonPermanent(20, TEST3, 30);

        assertShortPath(TEST3, new ShortPathPart(30, 0));

        assertShortPath(TEST1,
                new ShortPathPart(10, 0),
                new ShortPathPart(20, 1));

        assertShortPath(TEST2,
                new ShortPathPart(10, 0),
                new ShortPathPart(40, 1));

        // No Exception should be thrown
        removeDockable(TEST2, 0);

        assertShortPath(TEST2);

        assertShortPath(TEST1, new ShortPathPart(10, 0));

        assertShortPath(TEST3, new ShortPathPart(30, 0));

        // No Exception should be thrown
        removeDockable(TEST1, 0);

        assertShortPath(TEST1);

        assertShortPath(TEST3, new ShortPathPart(30, 0));

        // No Exception should be thrown
        removeDockable(TEST3, 0);

        assertShortPath(TEST3);
    }

    @Test
    public void testAddDockingArea5() {
        System.out.println("addDockingArea5");
        addDockingAreaNonPermanent(20, LEFT, 20, 20);
        addDockingAreaNonPermanent(20, RIGHT, 20, 80);
        addDockingAreaNonPermanent(20, TOP, 20, 40, 20);
        addDockingAreaNonPermanent(20, BOTTOM, 20, 40, 80);

        assertShortPath(LEFT,
                new ShortPathPart(20, 1));

        assertShortPath(RIGHT,
                new ShortPathPart(80, 1));

        assertShortPath(TOP,
                new ShortPathPart(40, 1),
                new ShortPathPart(20, 2));

        assertShortPath(BOTTOM,
                new ShortPathPart(40, 1),
                new ShortPathPart(80, 2));

        // No Exception should be thrown
        removeDockable(LEFT, 0);

        assertShortPath(LEFT);

        assertShortPath(RIGHT,
                new ShortPathPart(80, 1));

        assertShortPath(TOP,
                new ShortPathPart(40, 1),
                new ShortPathPart(20, 2));

        assertShortPath(BOTTOM,
                new ShortPathPart(40, 1),
                new ShortPathPart(80, 2));

        // No Exception should be thrown
        removeDockable(RIGHT, 0);

        assertShortPath(RIGHT);

        assertShortPath(TOP,
                new ShortPathPart(20, 2));

        assertShortPath(BOTTOM,
                new ShortPathPart(80, 2));

        // No Exception should be thrown
        removeDockable(TOP, 0);

        assertShortPath(TOP);

        assertShortPath(BOTTOM,
                new ShortPathPart(20, 0));

        // No Exception should be thrown
        removeDockable(BOTTOM, 0);

        assertShortPath(BOTTOM);
    }

    @Test
    public void testAddDockingArea6() {
        System.out.println("addDockingArea6");
        addDockingAreaPermanent(20, CENTER, 20, 40, 50);
        addDockingAreaNonPermanent(20, RIGHT, 20, 80);
        addDockingAreaNonPermanent(20, TOP, 20, 40, 20);

        assertShortPath(CENTER,
                new ShortPathPart(40, 1),
                new ShortPathPart(50, 2));

        assertShortPath(RIGHT,
                new ShortPathPart(80, 1));

        assertShortPath(TOP,
                new ShortPathPart(40, 1),
                new ShortPathPart(20, 2));

        // No Exception should be thrown
        removeDockable(TOP, 0);

        assertShortPath(TOP);

        assertShortPath(CENTER,
                new ShortPathPart(40, 1));

        assertShortPath(RIGHT,
                new ShortPathPart(80, 1));

        // No Exception should be thrown
        removeDockable(RIGHT, 0);

        assertShortPath(RIGHT);

        assertShortPath(CENTER,
                new ShortPathPart(20, 0));

        addDockablePane(CENTER);

        assertShortPath(CENTER,
                new ShortPathPart(20, 0));

        // No Exception should be thrown
        removeDockable(CENTER, 0);

        assertShortPath(CENTER,
                new ShortPathPart(20, 0));
    }

    /**
     * Issue #18: http://sourceforge.net/p/drombler/tickets/18/
     */
    @Test
    public void testAddDockingArea7() {
        System.out.println("addDockingArea7");
        addDockingAreaPermanent(20, CENTER, 20, 40, 50);
        addDockingAreaNonPermanentEmpty(20, TOP, 20, 40, 20);
        addDockingAreaNonPermanent(20, BOTTOM, 20, 40, 80);
        addDockingAreaNonPermanentEmpty(20, LEFT, 20, 20);
        addDockingAreaNonPermanentEmpty(20, RIGHT, 20, 80);

        assertShortPath(CENTER,
                new ShortPathPart(50, 2));

        assertShortPath(LEFT);

        assertShortPath(RIGHT);

        assertShortPath(TOP);

        assertShortPath(BOTTOM,
                new ShortPathPart(80, 2));

        // No Exception should be thrown
        removeDockable(BOTTOM, 0);

        assertShortPath(BOTTOM);

        assertShortPath(CENTER,
                new ShortPathPart(20, 0));

    }

    private void addDockingAreaNonPermanent(int position, String areaId, Integer... path) {
        addDockingAreaNonPermanentEmpty(position, areaId, path);

        addDockablePane(areaId);
    }

    private void addDockingAreaPermanent(int position, String areaId, Integer... path) {
        dockingPane.addDockingArea(Arrays.asList(path), new DockingAreaPane(areaId, position, true));
    }

//    private List<Integer> getDockingAreaPath(String dockingAreaId) {
//        DockingAreaPane dockingArea = dockingPane.getDockingArea(dockingAreaId);
//        DockingSplitPane parentSplitPane = dockingArea.getParentSplitPane();
//        List<Integer> path = new ArrayList<>();
//        while (parentSplitPane != null) {
//            path.add(parentSplitPane.getPosition());
//            parentSplitPane = parentSplitPane.getParentSplitPane();
//        }
//        path.remove(path.size() - 1);
//        Collections.reverse(path);
//        return path;
//    }
    private List<ShortPathPart> getDockingAreaShortPath(String dockingAreaId) {
        DockingAreaPane dockingArea = dockingPane.getDockingArea(dockingAreaId);
        return dockingArea.getShortPath();
    }

    private void removeDockable(String dockingAreaId, int dockableIndex) {
        DockingAreaPane dockingArea = dockingPane.getDockingArea(dockingAreaId);
        dockingArea.removeDockable(dockableIndex);
    }

    private void addDockingAreaNonPermanentEmpty(int position, String areaId, Integer... path) {
        dockingPane.addDockingArea(Arrays.asList(path), new DockingAreaPane(areaId, position, false));
    }

    private void addDockablePane(String areaId) {
        DockablePane dockablePane = new DockablePane();
        DockablePreferences dockablePreferences = new DockablePreferences();
        dockablePreferences.setAreaId(areaId);
        dockablePreferences.setPosition(10);
        dockablePreferencesManager.registerDockablePreferences(dockablePane, dockablePreferences);
        dockingPane.addDockable(dockablePane);
    }

    private void assertShortPath(String dockingAreaId, ShortPathPart... parts) {
        List<ShortPathPart> path = getDockingAreaShortPath(dockingAreaId);
        assertEquals(Arrays.asList(parts), path);
    }
}
