package org.drombler.fx.maven.plugins.pxb;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.archiver.MavenArchiver;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.jar.AbstractJarMojo;
import org.apache.maven.plugin.jar.JarMojo;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.jar.Manifest;
import org.codehaus.plexus.archiver.jar.ManifestException;
import org.ops4j.pax.construct.util.ReflectMojo;
import org.drombler.fx.maven.plugins.pxb.util.FXUtils;

/**
 * @extendsPlugin jar
 *
 * @extendsGoal jar
 *
 * @goal pxb
 *
 * @phase package
 */
// TODO: NOT USED YET!
public class PxbMojo extends JarMojo {

    /**
     * The archive configuration to use. See <a href="http://maven.apache.org/shared/maven-archiver/index.html">Maven
     * Archiver Reference</a>.
     *
     * @parameter
     */
    private MavenArchiveConfiguration archive = new MavenArchiveConfiguration();
//    private String destFile;
//    private Application app;
//    private Platform platform;
//    private Resources resources;
//    private List<FileSet> filesets;
//    //private Manifest manifest;
//    private PackagerLib packager;
//    private CreateJarParams createJarParams;
    //
    /**
     * @parameter expression="${pxb.classesDirectory}" default-value="${project.build.outputDirectory}" @required
     */
    private File classesDirectory;
//    //TODO: finaleName useful? (Not respected when deploying to a Maven repository.)
//    /**
//     * @parameter expression="${pxb.finalName}"
//     * default-value="${project.build.finalName}" @required
//     */
//    private String finalName;
//    /**
//     * @parameter expression="${pxb.outputDirectory}"
//     * default-value="${project.build.directory}" @required
//     */
//    private File outputDirectory;
    /**
     * @parameter expression="${pxb.mainClass}" @required
     */
    private String mainClass;
    /**
     * The Maven project.
     *
     * @parameter default-value="${project}" @required @readonly
     */
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException {
        ReflectMojo reflectAbstractJarMojo = new ReflectMojo(this, AbstractJarMojo.class);
        reflectAbstractJarMojo.setField("archive", archive);
        reflectAbstractJarMojo.setField("project", project);
        ReflectMojo reflectJarMojo = new ReflectMojo(this, JarMojo.class);
        reflectJarMojo.setField("classesDirectory", classesDirectory);

        archive.addManifestEntries(FXUtils.getManifestEntries(mainClass));
        Path metaInfDir = classesDirectory.toPath().resolve("META-INF");
        File manifestFile = metaInfDir.resolve("MANIFEST.MF").toFile();
        try {
            Files.createDirectories(metaInfDir);
            MavenArchiver mavenArchiver = new MavenArchiver();
            Manifest manifest = mavenArchiver.getManifest(project, archive);
            try (PrintWriter pw = new PrintWriter(manifestFile)) {
                manifest.write(pw);
            }
        } catch (ManifestException | DependencyResolutionRequiredException | IOException ex) {
            throw new MojoExecutionException("Couldn't create the Manifest file!", ex);
        }
        archive.setManifestFile(manifestFile);
        super.execute();

        //archive.addManifestEntries(null);
        //        Project antProject = new Project();
        //        FXJar fxJarTask = new FXJar();
        //
        //        Path jarPath = outputDirectory.toPath().resolve(finalName + ".jar");
        //        fxJarTask.setDestfile(jarPath.toString());
        //        Application application = fxJarTask.createApplication();
        //        application.setMainClass(mainClass);
        //
        //        FileSet fileSet = fxJarTask.createFileSet();
        //        fileSet.setDir(classesDirectory);
        //        fileSet.setProject(antProject);
        //        fxJarTask.execute();
        //
        //        project.getArtifact().setFile(jarPath.toFile());

    }
}
