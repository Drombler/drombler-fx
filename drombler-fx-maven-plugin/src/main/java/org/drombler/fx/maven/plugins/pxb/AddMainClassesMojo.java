/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.maven.plugins.pxb;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.drombler.fx.maven.plugins.pxb.util.FXUtils;

/**
 *
 * @goal add-main-classes
 *
 * @phase prepare-package
 */
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
