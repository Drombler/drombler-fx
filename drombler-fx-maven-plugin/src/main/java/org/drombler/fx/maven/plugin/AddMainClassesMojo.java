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
package org.drombler.fx.maven.plugin;

import java.io.File;
import java.io.IOException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.drombler.fx.maven.plugin.util.FXUtils;

/**
 *
 * @goal add-main-classes
 *
 * @phase prepare-package
 */
// TODO: NOT USED YET!
public class AddMainClassesMojo extends AbstractMojo {

    /**
     * @parameter expression="${pxb.classesDirectory}" default-value="${project.build.outputDirectory}" @required
     */
    private File classesDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            FXUtils.copyMainClasses(classesDirectory.toPath());
        } catch (IOException ex) {
            throw new MojoExecutionException("Adding JavaFX main classes failed!", ex);
        }
     
    }
}
