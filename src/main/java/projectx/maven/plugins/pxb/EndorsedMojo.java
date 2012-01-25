/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectx.maven.plugins.pxb;

import java.nio.file.Paths;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 *
 * @goal endorsed
 *
 * @phase package
 */
public class EndorsedMojo extends AbstractMojo {

    /**
     * The Maven project.
     *
     * @parameter default-value="${project}" @required @readonly
     */
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
//        getLog().debug(System.getenv("JAVAFX_HOME"));
//        DefaultArtifact artifact = new DefaultArtifact("javafx", "jfxrt", "2.0", "system", "jar", null, new DefaultArtifactHandler("jar"));
//        artifact.setFile(Paths.get(System.getenv("JAVAFX_HOME"), "rt", "lib", "jfxrt.jar").toFile());
//        project.getDependencyArtifacts().add(artifact);
        System.getProperties().setProperty("java.endorsed.dirs", Paths.get(System.getenv("JAVAFX_HOME"), "rt", "lib").toString());

    }
}
